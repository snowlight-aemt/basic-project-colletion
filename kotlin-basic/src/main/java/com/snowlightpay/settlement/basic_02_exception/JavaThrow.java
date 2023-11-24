package com.snowlightpay.settlement.basic_02_exception;

import java.io.IOException;

public class JavaThrow {
    public void throwIoException() throws IOException {
        throw new IOException("체크도 익셉션인 IOException 발생");
    }

    public static void main(String[] args) {
//        JavaThrow javaThrow = new JavaThrow();
//        try {
//            javaThrow.throwIoException();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // 체크드 예외가 존재하는 코들린 클래스는 자바에서 try/catch 하지 못한다. (컴파일 에러)
        KotlinThrow kotlinThrow = new KotlinThrow();
        try {
            kotlinThrow.throwIOException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
