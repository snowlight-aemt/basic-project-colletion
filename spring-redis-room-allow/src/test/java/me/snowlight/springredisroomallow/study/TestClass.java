package me.snowlight.springredisroomallow.study;

public class TestClass {
    public void testMethod() {}
    public static void testStaticMethod() {}
    public void testMethod2() {
        testStaticMethod();
        InnerStaticClass.innerStaticMethod();
    }
    private class InnerClass {
        public void innerMethod() {
            TestClass.this.testMethod();
        }
    }

    public static class InnerStaticClass {
        public String a;
        public static String b;
        public void innerMethod() {
            testStaticMethod();
            innerStaticMethod();
            System.out.println(a);
            System.out.println(b);
        }

        public static void innerStaticMethod() {
            testStaticMethod();
            System.out.println(b);
        }
    }

//    MyClass a = new MyClass();
//    MyClass.InnerClass b = a.new InnerClass(); //b은 a에 대한 숨은 외부 참조"를 갖는다.
//
//    MyClass.InnerStaticClass c = new MyClass.InnerStaticClass(); // c는 해당X

}

class TestClient {
    TestClass a = new TestClass();
//    TestClass.InnerClass b = a.new InnerClass();


    TestClass.InnerStaticClass c = new TestClass.InnerStaticClass();
}