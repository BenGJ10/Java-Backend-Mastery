import java.math.BigDecimal;

public class FloatDemo {
    public static void main(String[] args) {
        float floatValue = .7f;
        System.out.println("The float value is: " + (double) floatValue);

        // We expect the output to be .7f
        // However, due to floating-point precision issues, it might not be exactly .7f
        if ((double) floatValue == .7f) {
            System.out.println("The float value is exactly 0.7");
        } 
        else {
            System.out.println("The float value is not exactly .7");        
        }

        double doubleValue = 1.0d / 0.0d;
        System.out.println("1.0d / 0.0d is: " + doubleValue); // This will print "Infinity"

        double nanValue = 0.0d / 0.0d;
        System.out.println("0.0d / 0.0d is: " + nanValue); // This will print "NaN"

        double signedZero = 0.0d;
        double negativeSignedZero = -0.0d;
        System.out.println("0.0d == -0.0d: " + (signedZero == negativeSignedZero)); // This will print "true"

        // Instead of using float and double for precise calculations, use BigDecimal
        BigDecimal bigDecimalValue1 = new BigDecimal("0.7");
        BigDecimal bigDecimalValue2 = new BigDecimal("0.1");
        System.out.println("BigDecimal 0.7 - 0.1 * 6 = " + bigDecimalValue1.subtract(bigDecimalValue2.multiply(new BigDecimal("6"))));
    }    
}
