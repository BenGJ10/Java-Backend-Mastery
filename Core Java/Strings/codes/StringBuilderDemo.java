public class StringBuilderDemo {
    
    public static void main(String args[]) {
        
        /* We use StringBuilder when we need to make a lot of modifications to strings
           because StringBuilder is mutable, meaning it can be changed without creating new objects.
        */

        // StringBuilder is a mutable sequence of characters
        StringBuilder sb = new StringBuilder("Hello");
        System.out.println("Original StringBuilder: " + sb.toString());

        // Appending text to StringBuilder
        sb.append(", World!");
        System.out.println("After append: " + sb.toString());

        // Inserting text at a specific position
        sb.insert(5, " Beautiful");
        System.out.println("After insert: " + sb.toString());

        // Replacing text within StringBuilder
        sb.replace(6, 15, "Amazing");
        System.out.println("After replace: " + sb.toString());

        // Deleting text from StringBuilder
        sb.delete(5, 12);
        System.out.println("After delete: " + sb.toString());

        // Reversing the content of StringBuilder
        sb.reverse();
        System.out.println("After reverse: " + sb.toString());

        // Important StringBuilder methods
        System.out.println("Length of StringBuilder: " + sb.length());
        System.out.println("Character at index 2: " + sb.charAt(2));
        sb.setCharAt(2, 'X');
        System.out.println("After setCharAt(2, 'X'): " + sb.toString());
        
        // Converting StringBuilder to String
        String finalString = sb.toString();
        System.out.println("Final String: " + finalString);
    }
}
