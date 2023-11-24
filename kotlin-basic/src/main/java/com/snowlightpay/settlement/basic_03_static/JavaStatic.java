package com.snowlightpay.settlement.basic_03_static;

import java.util.ArrayList;
import java.util.List;

public class JavaStatic {
    public static void main(String[] args) {
        String hello1 = HelloClass.Companion.hello();
        String hi1 = HiObject.INSTANCE.hi();
        System.out.println(hello1);
        System.out.println(hi1);

        String hello2 = JvmStaticClass.hello();
        String hi2 = JvmStaticHiObject.hi();
        System.out.println(hello2);
        System.out.println(hi2);

        System.out.println(JvmFieldClass.Companion.getId());
        System.out.println(JvmFieldObject.INSTANCE.getName());
        System.out.println(JvmFieldClass.CODE);
        System.out.println(JvmFieldObject.FAMILY);

//        System.out.println("ABCD".first());
//        System.out.println("ABCD".addFirst('Z'));
        System.out.println(KotlinStringKt.first("ABCD"));
        System.out.println(KotlinStringKt.addFirst("ABCD" ,'Z'));
    }
}
