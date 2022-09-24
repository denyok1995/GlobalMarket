package com.pjt.globalmarket.cart.service;

import com.pjt.globalmarket.cart.dao.CartRepository;
import com.pjt.globalmarket.cart.domain.Cart;
import com.pjt.globalmarket.product.domain.Product;
import com.pjt.globalmarket.product.service.ProductService;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final CartRepository cartRepository;

    @Transactional
    public Cart saveProductInUserCart(User user, Product product, Long productNum) {
        // NOTE: 보통은 UserAndProduct를 사용합니다. 사람이 상품을 답는다는 조건이 강하고, 이건 다른데서도 사용 많이 하거든요.
        Optional<Cart> savedCart = cartRepository.findCartByProductAndUser(product, user);
        isOverStock(product, productNum);

        if (savedCart.isPresent()) {
            isOverStock(product, productNum + savedCart.get().getProductNum());
            savedCart.get().setProductNum(savedCart.get().getProductNum() + productNum);
            return savedCart.get();
        } else {
            Cart cart = Cart.builder().product(product)
                    .productNum(productNum)
                    .user(user)
                    .build();
            cartRepository.save(cart);
            return cart;
        }
    }

    private static void isOverStock(Product product, Long productNum) {
        if(product.getStock() < productNum) {
            throw new IllegalArgumentException("해당 상품의 재고가 부족합니다.");
        }
    }

    public List<Cart> getAllProductsInCartByUser(User user) {
        return cartRepository.findCartsByUser(user).stream().filter(cart -> !isSoldOut(cart.getProduct(), cart.getProductNum())).collect(Collectors.toList());
    }

    public boolean isSoldOut(Product product, Long num) {
        Optional<Product> productById = productService.getProductById(product.getId());
        if(productById.isEmpty()) {
            return true;
        }
        return productById.get().getStock() < num;
    }

    // NOTE: 파라메터에서 productMap이 의미하는게 뭘까요? long/long이라는 것이 별로 좋지 않아요. 또는 Pair를 사용하는 것도 방법이기는 한데, 좀더 명확히 해봅시다.
    @Transactional
    public void buyProductsInCart(User user, Map<Long, Long> productMap) {
        List<Cart> myCart = cartRepository.findCartsByUser(user);
        for(Cart cart : myCart) {
            // NOTE: Long은 특별한 경우 아니면 사용하지 마세요. long을 사용하면 null check도 필요 없잖아요?
            // 그리고 아직 구현이 다 안된거죠? 구매라는 실제 과정이 빠져 있어서.
            Long orderMount = productMap.get(cart.getProduct().getId());
            if(orderMount != null && orderMount > 0) {
                if(cart.getProductNum() > orderMount) {
                    cart.setProductNum(cart.getProductNum() - orderMount);
                } else {
                    cartRepository.delete(cart);
                }
            }
        }
    }
}
