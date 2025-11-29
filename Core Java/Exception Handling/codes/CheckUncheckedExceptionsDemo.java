// A complete Java program to demonstrate Checked and Unchecked Exceptions

public class CheckUncheckedExceptionsDemo {
    public static void divibeByZero(){
        int a = 10;
        int b = 0;
        int c = a / b; // This will throw ArithmeticException
        System.out.println("Result: " + c);
    }

    public static void readFile() throws Exception{
        throw new Exception("Checked Exception Example");
    }
    public static void main(String args[]){
        System.out.println("Demonstrating Checked Exception:");
        try {
            readFile();
            System.out.println("File read successfully.");
        } catch (Exception e) {
            System.out.println("Caught an Exception: " + e.getMessage());
        }
        
        System.out.println("Demonstrating Unchecked Exception:");
        try {
            divibeByZero();
            System.out.println("Division performed successfully.");
        } catch (Exception e) {
            System.out.println("Caught an ArithmeticException: " + e.getClass().getSimpleName());
        }
    }    
}
