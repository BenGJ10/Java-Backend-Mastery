// Sample Java code demonstrating Encapsulation

class Human  {

    private String name;
    private int age;
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }
}

public class Encapsulation {
    public static void main(String[] args) {
        Human human1 = new Human();
        human1.setName("Ben");
        human1.setAge(20);

        Human human2 = new Human();
        human2.setName("Anna");
        human2.setAge(20);

        System.out.println("Human 1: Name = " + human1.getName() + ", Age = " + human1.getAge());
        System.out.println("Human 2: Name = " + human2.getName() + ", Age = " + human2.getAge());
    }
}