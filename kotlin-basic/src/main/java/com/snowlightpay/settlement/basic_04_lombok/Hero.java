package com.snowlightpay.settlement.basic_04_lombok;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Hero {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int age;

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void main(String[] args) {
        Hero hero = new Hero();
//        hero.setName("아이어맨");
//        hero.setAge(53);
//        hero.setAddress("스타크타워");
    }
}
