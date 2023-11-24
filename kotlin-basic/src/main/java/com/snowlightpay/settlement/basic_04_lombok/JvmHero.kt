package com.snowlightpay.settlement.basic_04_lombok

import HeroKt

fun main() {
    val hero = Hero()
//    hero.name = "아이언맨" // 에러
    hero.address = "스타크타워"
    println(hero.address)

    val heroKt = HeroKt(name = "아이언맨", age = 50, address = "스타크타워")
    println(heroKt);
}