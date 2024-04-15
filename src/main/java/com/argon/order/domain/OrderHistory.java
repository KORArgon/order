package com.argon.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="NT_ORDER_HISTORY")
public class OrderHistory {

    @Id
    @Column(name = "ORDER_HISTORY_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderHistoryNo;

    @Column(name = "ORDER_ID", columnDefinition = "VARCHAR(200)", nullable = false)
    private String orderId;

    @Column(name = "RESTAURANT_ID", columnDefinition = "VARCHAR(100)", nullable = false)
    private String restaurantId;

    @Column(name = "TABLE_NO", columnDefinition = "INT", nullable = false)
    private String tableNo;

    @Column(name = "REGIST_DATE", columnDefinition = "CHAR(14)", nullable = false, updatable=false)
    private String registDate;

    @Column(name = "REGIST_ID", columnDefinition = "VARCHAR(20)", nullable = false, updatable=false)
    private String registId;

    @Column(name = "UPDATE_DATE", columnDefinition = "CHAR(14)", insertable=false)
    private String updateDate;

    @Column(name = "UPDATE_ID", columnDefinition = "VARCHAR(20)", insertable=false)
    private String updateId;

}
