package com.argon.order.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(builderMethodName = "foodMenuBuilder", toBuilder = true)
@Entity
@Data
@Table(name="NT_FOOD_MENU")
public class FoodMenu {

    @Id
    @Column(name = "FOOD_MENU_NO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodMenuNo;

    @Column(name = "RESTAURANT_ID", columnDefinition = "VARCHAR(100)")
    private String restaurantId;

    @Column(name = "FOOD_MENU_NM", columnDefinition = "VARCHAR(50)", nullable = false)
    private String foodMenuNm;

    @Column(name = "FOOD_MENU_PRICE", columnDefinition = "INT", nullable = false)
    private String foodMenuPrice;

    @Column(name = "MENU_CATEGORY", columnDefinition = "VARCHAR(20)", nullable = false)
    private String menuCategory;

    @Column(name = "POPULAR_AT", columnDefinition = "VARCHAR(1)", nullable = false)
    private String popularAt;

    @Column(name = "FOOD_MENU_TITLE", columnDefinition = "VARCHAR(20)", nullable = false)
    private String foodMenuTitle;

    @Column(name = "FOOD_IMG_NAME", columnDefinition = "VARCHAR(100)", nullable = false)
    private String foodImgName;

    @Column(name = "FOOD_IMG_STORE_NAME", columnDefinition = "VARCHAR(100)")
    private String foodImgStoreName;

    @Column(name = "FOOD_IMG_PATH", columnDefinition = "VARCHAR(400)")
    private String foodImgPath;

    @Column(name = "REGIST_DATE", columnDefinition = "CHAR(14)")
    private String registDate;

    @Column(name = "REGIST_ID", columnDefinition = "VARCHAR(20)")
    private String registId;

    @Column(name = "UPDATE_DATE", columnDefinition = "CHAR(14)")
    private String updateDate;

    @Column(name = "UPDATE_ID", columnDefinition = "VARCHAR(20)")
    private String updateId;

    public static FoodMenu.FoodMenuBuilder builder(){
        return foodMenuBuilder();
    }
}
