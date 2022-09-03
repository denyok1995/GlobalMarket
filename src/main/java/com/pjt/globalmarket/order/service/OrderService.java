package com.pjt.globalmarket.order.service;

import com.pjt.globalmarket.address.dao.AddressRepository;
import com.pjt.globalmarket.address.domain.Address;
import com.pjt.globalmarket.cart.service.CartService;
import com.pjt.globalmarket.coupon.domain.Coupon;
import com.pjt.globalmarket.coupon.service.CouponService;
import com.pjt.globalmarket.order.dao.OrderProductRepository;
import com.pjt.globalmarket.order.dao.OrderRepository;
import com.pjt.globalmarket.order.domain.Order;
import com.pjt.globalmarket.order.domain.OrderProduct;
import com.pjt.globalmarket.order.dto.CheckInfo;
import com.pjt.globalmarket.order.dto.OrderProductInfo;
import com.pjt.globalmarket.order.dto.OrderRequestInfo;
import com.pjt.globalmarket.payment.domain.Payment;
import com.pjt.globalmarket.payment.dto.PaymentInfo;
import com.pjt.globalmarket.payment.service.PaymentService;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.dto.SimpleProductInfo;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final CouponService couponService;
    private final CartService cartService;

    public Optional<CheckInfo> getOrderInfo(User user, List<SimpleProductInfo> productsInfo) {

        Optional<Address> address = addressRepository.findAddressByUserAndMain(user, true);

        Map<Long, Long> productMap = new HashMap<>();
        for(SimpleProductInfo info : productsInfo) {
            productMap.put(info.getProductId(), info.getProductNum());
        }
        List<Product> products = productService.
                findProductsByIds(productsInfo.stream().map(info -> info.getProductId()).collect(Collectors.toList()))
                .stream().filter(product -> product.getStock() >= productMap.get(product.getId())).collect(Collectors.toList());

        List<OrderProductInfo> productInfos = new ArrayList<>();
        Double totalPrice = 0.0;
        Double totalDeliveryFee = 0.0;
        for(Product product : products) {
            Double discountedPrice = productService.getDiscountedPriceByUserGrade(user.getGrade(), product.getPrice());
            Long count = productMap.get(product.getId());
            totalPrice += discountedPrice * count;
            totalDeliveryFee += product.getDeliveryFee();
            productInfos.add(OrderProductInfo.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(discountedPrice)
                    .count(count)
                    .deliveryFee(product.getDeliveryFee())
                    .rocketDelivery(product.getRocketDelivery())
                    .build());
        }

        return Optional.ofNullable(CheckInfo.builder().consumerName(user.getName())
                .consumerPhone(user.getPhone())
                .consumerAddress((address.isEmpty()) ? null : address.get().getContent())
                .orderProducts(productInfos)
                .totalDeliveryFee(totalDeliveryFee)
                .totalPrice(totalPrice)
                .build());
    }

    @Transactional
    public void payOrder(User user, OrderRequestInfo orderRequestInfo, Coupon coupon) {
        // 재고 있는지 다시 확인
        Map<Long, Long> productMap = new HashMap<>();
        for(OrderProductInfo info : orderRequestInfo.getOrderProducts()) {
            productMap.put(info.getId(), info.getCount());
        }
        List<Product> products = productService.
                findProductsByIds(orderRequestInfo.getOrderProducts().stream().map(info -> info.getId()).collect(Collectors.toList()));
        List<OrderProduct> orderProducts = new ArrayList<>();

        Double originalPrice = 0.0;
        Double totalPrice = 0.0;
        Double totalDeliveryFee = 0.0;
        for(Product product : products) {
            if(productMap.get(product.getId()) > product.getStock()) {
                throw new IllegalArgumentException("재고가 없습니다.");
            } else {
                // 금액 책정
                originalPrice += product.getPrice();
                Double discountedPrice = productService.getDiscountedPriceByUserGrade(user.getGrade(), product.getPrice());
                Long count = productMap.get(product.getId());
                totalPrice += discountedPrice * count;
                totalDeliveryFee += product.getDeliveryFee();

                // order 주문정보 저장을 위한 리스트 추가
                orderProducts.add(OrderProduct.builder()
                        .product(product)
                        .productNum(productMap.get(product.getId()))
                        .build());

                // 상품 재고 감소
                product.setStock(product.getStock() - productMap.get(product.getId()));
            }
        }

        // 쿠폰 있으면 사용
        if(coupon != null && originalPrice >= coupon.getMinPrice()) {
            totalPrice -= coupon.getDiscountPrice();
            couponService.useCoupon(user, coupon);
        }
        Double discountPrice = originalPrice - totalPrice;

        // payment service 호출해서 저장
        Payment payment = paymentService.savePayment(PaymentInfo.builder()
                .type(orderRequestInfo.getPaymentType())
                .discountPrice(discountPrice)
                .totalPrice(totalPrice + totalDeliveryFee)
                .build());

        // order에도 주문정보 저장
        Order order = Order.builder()
                .user(user)
                .receiverName(orderRequestInfo.getReceiverName())
                .receiverAddress(orderRequestInfo.getReceiverAddress())
                .receiverPhone(orderRequestInfo.getReceiverPhone())
                .orderProducts(orderProducts.stream().map(orderProductRepository::save).collect(Collectors.toList()))
                .payment(payment)
                .build();
        orderRepository.save(order);

        // 장바구니에 있는 품목 제거
        cartService.buyProductsInCart(user, productMap);
    }
}
