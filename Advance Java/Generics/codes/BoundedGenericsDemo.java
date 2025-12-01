import java.util.List;

class UpDisplay <T extends Number> {
    private T value;

    public UpDisplay(T value) {
        this.value = value;
    }

    public void show() {
        System.out.println("Value: " + value);
    }
}

public class BoundedGenericsDemo {

    // Lowered Bounded Generics Example
    public static void displayLowerBounded(List<? super Integer> list) {
        for (Object obj : list) {
            System.out.println("Element: " + obj);
        }
    }
    public static void main(String[] args) {
        // Creating an instance of Display with Integer type
        UpDisplay<Integer> intDisplay = new UpDisplay<>(100);
        intDisplay.show();

        // Creating an instance of Display with Double type
        UpDisplay<Double> doubleDisplay = new UpDisplay<>(99.99);
        doubleDisplay.show();

        // The following line would cause a compile-time error
        // Display<String> stringDisplay = new Display<>("Hello"); // Error: String is not a subtype of Number

        // Lower Bounded Generics Example
        List<Number> numberList = List.of(1, 2.5, 3, 4.75);
        System.out.println("Lower Bounded Generics Output:");
        displayLowerBounded(numberList);
    }
}
