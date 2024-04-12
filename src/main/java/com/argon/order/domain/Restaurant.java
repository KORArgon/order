package com.argon.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="NT_RESTAURANT")
public class Restaurant {

    @Id
    @Column(name = "RESTAURANT_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantNo;

    @Column(name = "MEMBER_ID", columnDefinition = "VARCHAR(20)", nullable = false)
    private String memberId;

    @Column(name = "RESTAURANT_ID", columnDefinition = "VARCHAR(100)", nullable = false)
    private String restaurantId;

    @Column(name = "RESTAURANT_NM", columnDefinition = "VARCHAR(200)", nullable = false)
    private String restaurantNm;

    @Column(name = "REGIST_DATE", columnDefinition = "CHAR(14)", nullable = false, updatable=false)
    private String registDate;

    @Column(name = "REGIST_ID", columnDefinition = "VARCHAR(20)", nullable = false, updatable=false)
    private String registId;

    @Column(name = "UPDATE_DATE", columnDefinition = "CHAR(14)", insertable=false)
    private String updateDate;

    @Column(name = "UPDATE_ID", columnDefinition = "VARCHAR(20)", insertable=false)
    private String updateId;

}
