package com.argon.order.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "memberBuilder")
@Entity
@Data
@Table(name="NT_MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_ID", nullable = false)
    private String memberId;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(200)", nullable = false)
    private String password;

    @Column(name = "NAME", columnDefinition = "VARCHAR(20)", nullable = false)
    private String name;

    @Column(name = "GRADE", columnDefinition = "VARCHAR(5)", nullable = false)
    private String grade;

    @Column(name = "REGIST_DATE", columnDefinition = "CHAR(14)", updatable=false)
    private String registDate;

    @Column(name = "REGIST_ID", columnDefinition = "VARCHAR(20)", updatable=false)
    private String registId;

    @Column(name = "UPDATE_DATE", columnDefinition = "CHAR(14)", insertable=false)
    private String updateDate;

    @Column(name = "UPDATE_ID", columnDefinition = "VARCHAR(20)", insertable=false)
    private String updateId;

    public Member() {

    }

    public static MemberBuilder builder(){
        return memberBuilder();
    }
}
