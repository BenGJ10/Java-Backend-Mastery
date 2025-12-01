import java.util.ArrayList;
import java.util.List;

class Box<T> {
    private T item;

    public Box() {}
    public Box(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}

class Numbers {
    private List<? extends Number> numbers;

    public Numbers(List<? extends Number> numbers) {
        this.numbers = numbers;
    }

    public List<? extends Number> getNumbers() {
        return numbers;
    }
}

public class WildcardsDemo {
    public static void main(String[] args) {

        // Upper Bounded (Read-only)
        Box<? extends Number> upper = new Box<>(20);
        System.out.println("Upper bounded (get only): " + upper.getItem());

        // Lower Bounded (Write allowed)
        Box<? super Integer> lower = new Box<Number>();
        lower.setItem(50);
        System.out.println("Lower bounded (insertion allowed): " + lower.getItem());

        // Unbounded wildcard (cannot write)
        Box<?> unknown = new Box<>("Hello World");
        System.out.println("Unbounded wildcard: " + unknown.getItem());
        // unknown.setItem("Hi"); // Not allowed

        // Using wildcards with Numbers class
        List<Double> list = new ArrayList<>();
        list.add(10.5);
        list.add(20.75);
        Numbers nums = new Numbers(list);
        System.out.println("Numbers list: " + nums.getNumbers());
    }
}
