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
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter your age: ");
            String age = reader.readLine();
            System.out.println("You are " + age + " years old.");
        } 
        // As we are using try-with-resources, the resource will be closed automatically. No need for the following finally block.
        // finally {
        //     if (reader != null) {
        //         reader.close();        
        //     }  
        // }
    }            
}
