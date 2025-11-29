class ExceptionPropagation {
    static void methodA() {
        // This will throw an ArithmeticException
        int result = 10 / 0;
        System.out.println("Result: " + result);
    }
    
    static void methodB() {
        methodA();
    }
    
    static void methodC() {
        methodB();
    }
}

public class ExceptionPropagationDemo {
    public static void main(String[] args) {
        try {
            ExceptionPropagation.methodC();
        } catch (ArithmeticException e) {
            // Let's check the stack trace to see the propagation path
            e.printStackTrace();
        }
        
        /* The output will be in the form:
                java.lang.ArithmeticException: / by zero
                at ExceptionPropagation.methodA(ExceptionPropagationDemo.java:4)
                at ExceptionPropagation.methodB(ExceptionPropagationDemo.java:9)
                at ExceptionPropagation.methodC(ExceptionPropagationDemo.java:13)
                at ExceptionPropagationDemo.main(ExceptionPropagationDemo.java:20)
        */
    }    
}
