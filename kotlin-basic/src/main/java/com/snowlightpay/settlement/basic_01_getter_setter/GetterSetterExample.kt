package com.snowlightpay.settlement.basic_01_getter_setter

fun main() {
    val person = Person();

    // 자바 스타일
    person.setName("토니스타크")
    person.setAge(44)
    person.setAddress("스타크타워")

    println(person.getName())
    println(person.getAge())

    // 코클린 스타일
    val person2 = Person();
    person2.name = "토니스타크"
    person2.age = 44

    println(person2.name);
    println(person2.age);

    // 프로퍼티가 없을에도 게터 메서드라면 프로퍼티처럼 사용 가능하다.
    println(person2.uuid)

    // 세터가 있으에도 컴파일 오류가 발생한다. (게터가 없음)
    // person2.address = "";


}