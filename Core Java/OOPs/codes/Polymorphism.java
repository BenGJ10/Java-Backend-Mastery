// Sample Java code demonstrating polymorphism


interface Animal {
    void sound();
}

interface Pet {
    void play();
}

class Dog implements Animal, Pet {
    public void sound() {
        System.out.println("Woof");
    }
    public void play() {
        System.out.println("The dog is playing fetch.");
    }
}

class Cat implements Animal, Pet {
    public void sound() {
        System.out.println("Meow");
    }
    public void play() {
        System.out.println("The cat is playing with a ball of yarn.");
    }
}

public class Polymorphism {
    public static void main(String[] args) {
        Animal myDog = new Dog();
        Animal myCat = new Cat();

        myDog.sound(); // Outputs: Woof
        myCat.sound(); // Outputs: Meow

        Pet petDog = new Dog();
        Pet petCat = new Cat();

        petDog.play(); // Outputs: The dog is playing fetch.
        petCat.play(); // Outputs: The cat is playing with a ball of yarn.
    }
}