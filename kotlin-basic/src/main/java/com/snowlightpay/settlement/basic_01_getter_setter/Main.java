package com.snowlightpay.settlement.basic_01_getter_setter;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Student student = new Student();

        student.name = "안녕하세요.";
        student.setBirthDate(LocalDate.now());

        System.out.println(student.name);
        System.out.println(student.getBirthDate());

//        student.setAge();
        System.out.println(student.getAge());
//        student.setGrade();
        student.changeGrade("B");
        System.out.println(student.getGrade());

    }
}