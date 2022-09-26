package com.pjt.globalmarket.order.domain;

import com.pjt.globalmarket.payment.domain.Payment;
import com.pjt.globalmarket.user.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "UserOrder")
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverRequest;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();

    //private boolean status;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @OneToOne
    private Payment payment;

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }

}
