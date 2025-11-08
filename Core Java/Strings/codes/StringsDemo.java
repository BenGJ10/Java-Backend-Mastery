public class StringsDemo{
    public static void main(String args[]){
        
        // String is a class in Java
        String name = new String("Ben");
        System.out.println("My name is: " + name);

        // Even though String is a class, we can create String objects without using the 'new' keyword
        // It will automatically create a String object in the background
        String city = "Mavelikkara";
        System.out.println("I live in: " + city);

        /* Strings are immutable in Java
           This means once a String object is created, it cannot be changed or modified.
           
           Why is this important?
           Because when we modify a String, a new String object is created in the memory. The original String remains unchanged.
        */
        String greeting = "Hello";
        System.out.println("Original greeting: " + greeting);
        greeting = greeting + ", World!";
        System.out.println("Modified greeting: " + greeting);
        
        // Important String methods
        String sample = "  Java Programming  ";
        System.out.println("Length of sample string: " + sample.length());
        System.out.println("Uppercase: " + sample.toUpperCase());
        System.out.println("Lowercase: " + sample.toLowerCase());
        System.out.println("Trimmed: '" + sample.trim() + "'");
        System.out.println("Substring (5, 16): " + sample.substring(5, 16));
        System.out.println("Replace 'Java' with 'Python': " + sample.replace("Java", "Python"));
        System.out.println("Index of 'Programming': " + sample.indexOf("Programming"));
        System.out.println("Character at index 2: " + sample.charAt(2));

    }
}