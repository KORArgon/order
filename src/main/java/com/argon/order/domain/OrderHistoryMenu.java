package com.argon.order.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(builderMethodName = "orderHistoryMenuBuilder", toBuilder = true)
@Entity
@Data
@Table(name="NT_ORDER_HISTORY_MENU")
public class OrderHistoryMenu {

    @Id
    @Column(name = "ORDER_HISTORY_MENU_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderHistoryMenuNo;

    @Column(name = "ORDER_ID", columnDefinition = "VARCHAR(200)", nullable = false)
    private String orderId;

    @Column(name = "FOOD_MENU_NO", columnDefinition = "INT", nullable = false)
    private int foodMenuNo;

    @Column(name = "ORDER_CNT", columnDefinition = "INT", nullable = false)
    private int orderCnt;

    public static OrderHistoryMenuBuilder builder(){
        return orderHistoryMenuBuilder();
    }

}
