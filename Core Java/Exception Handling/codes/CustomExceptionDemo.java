class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}

public class CustomExceptionDemo {
    public static void validateAge(int age) throws CustomException {
        if (age < 18) {
            throw new CustomException("Age must be at least 18 to vote.");
        } else {
            System.out.println("Valid age for voting.");
        }
    }

    public static void main(String[] args) {
        int age = 16;

        try {
            validateAge(age);
        } 
        catch (CustomException e) {
            System.out.println("Exception caught: " + e.getMessage());
        } 
        finally {
            System.out.println("Validation process completed.");
        }
    }    
}
