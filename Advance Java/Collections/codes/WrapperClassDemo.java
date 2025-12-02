public class WrapperClassDemo {
    public static void main(String[] args) {
        // Creating Integer wrapper objects
        Integer intObj1 = Integer.valueOf(10);
        Integer intObj2 = Integer.valueOf(20);

        // Performing arithmetic operations using unboxing
        int sum = intObj1 + intObj2;
        System.out.println("Sum: " + sum + "Type of sum: " + ((Object)sum).getClass().getSimpleName());

        // Creating Double wrapper objects
        Double doubleObj1 = Double.valueOf(15.5);
        Double doubleObj2 = Double.valueOf(4.5);
        // Performing arithmetic operations using unboxing
        double difference = doubleObj1 - doubleObj2;
        System.out.println("Difference: " + difference + " Type of difference: " + ((Object)difference).getClass().getSimpleName());    

        // Integer Chaining example
        // Values between -128 and 127 are cached by Integer class
        Integer intObj3 = Integer.valueOf(100);
        Integer intObj4 = Integer.valueOf(100);
        System.out.println("intObj3 and intObj4 refer to the same object: " + (intObj3 == intObj4));

        Integer intObj5 = Integer.valueOf(200);
        Integer intObj6 = Integer.valueOf(200);
        System.out.println("intObj5 and intObj6 refer to the same object: " + (intObj5 == intObj6));

        // Auto-boxing example
        Integer autoBoxedInt = Integer.valueOf(50); // Auto-boxing from int to Integer
        System.out.println("Auto-boxed Integer: " + autoBoxedInt + " Type: " + ((Object)autoBoxedInt).getClass().getSimpleName());

        // Auto-unboxing example
        int autoUnboxedInt = autoBoxedInt; // Auto-unboxing from Integer to int
        System.out.println("Auto-unboxed int: " + autoUnboxedInt + " Type: " + ((Object)autoUnboxedInt).getClass().getSimpleName());
    }
}