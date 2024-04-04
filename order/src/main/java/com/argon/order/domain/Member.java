package com.argon.order.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="NT_MEMBER")
public class Member extends Common {

    @Id
    @Column(name = "MEMBER_ID", nullable = false)
    private String memberId;

    @Column(name = "RESTAURANT_ID", columnDefinition = "VARCHAR(100)")
    private String restaurantId;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(200)", nullable = false)
    private String password;

    @Column(name = "NAME", columnDefinition = "VARCHAR(20)", nullable = false)
    private String name;

    @Column(name = "GRADE", columnDefinition = "VARCHAR(5)", nullable = false)
    private String grade;

    @Column(name = "REGIST_DATE", columnDefinition = "CHAR(14)", nullable = false)
    private String registDate;

    @Column(name = "REGIST_ID", columnDefinition = "VARCHAR(20)", nullable = false)
    private String registId;

    @Column(name = "UPDATE_DATE", columnDefinition = "CHAR(14)")
    private String updateDate;

    @Column(name = "UPDATE_ID", columnDefinition = "VARCHAR(20)")
    private String updateId;

}
