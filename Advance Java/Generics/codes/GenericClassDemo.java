class GenericClass<T> {
    private T data;

    public GenericClass(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

public class GenericClassDemo {
    public static void main(String[] args) {
        // Creating an instance of GenericClass with Integer type
        GenericClass<Integer> intInstance = new GenericClass<>(10);
        System.out.println("Integer Value: " + intInstance.getData());

        // Creating an instance of GenericClass with String type
        GenericClass<String> stringInstance = new GenericClass<>("Hello Generics");
        System.out.println("String Value: " + stringInstance.getData());

        // Creating an instance of GenericClass with Double type
        GenericClass<Double> doubleInstance = new GenericClass<>(15.5);
        System.out.println("Double Value: " + doubleInstance.getData());
    }    
}
