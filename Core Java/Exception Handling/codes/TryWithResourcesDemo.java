import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TryWithResourcesDemo {
    public static void main(String[] args) throws IOException{
        // Using try-with-resources to ensure the BufferedReader is closed automatically
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter your name: ");
            String name = reader.readLine();
            System.out.println("Hello, " + name + "!");
            demoFinallyBlock();
        }
    }

    // Another way to demonstrate try-with-resources is using finally block
    public static void demoFinallyBlock() throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your age: ");
            String age = reader.readLine();
            System.out.println("You are " + age + " years old.");
        } 
        finally {
            if (reader != null) {
                reader.close();        
            }  
        }
    }            
}
