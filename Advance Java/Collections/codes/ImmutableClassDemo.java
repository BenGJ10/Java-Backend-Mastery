/* Immutable Classes in Java
   1. Declare the class as final so it can't be extended.
   2. Make all fields private and final.
   3. Don't provide setter methods for fields.
   4. Initialize all fields via a constructor.
   5. If a field is a mutable object, ensure that it is not modified after construction and provide only a copy of it in getter methods.
 */

import java.util.List;

final class Profile{
    private final String name;
    private final List<String> techstack;

    Profile(String name, List<String> techstack){
        this.name = name;
        this.techstack = techstack;
    }

    public String getName() {
        return name;
    }

    public List<String> getTechstack() {
        return techstack;
    }
}

public class ImmutableClassDemo {
    public static void main(String[] args) {
        Profile profile = new Profile("Ben Gregory", List.of("Java", "Python", "C++"));

        System.out.println("Name: " + profile.getName());
        System.out.println("Tech Stack: " + profile.getTechstack());
    
        // The members of the Profile class cannot be modified after the object is created, ensuring immutability.
        try{
            profile.getTechstack().add("SQL"); // This will throw an UnsupportedOperationException
        } 
        catch (UnsupportedOperationException e){
            System.out.println("Cannot modify tech stack: " + e.getMessage());
        }

    }
}
