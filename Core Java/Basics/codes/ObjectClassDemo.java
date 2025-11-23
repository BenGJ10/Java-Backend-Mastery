// Implementation of methods from Object class in Java

class Mobile {
    String brand;
    String model;
    
    Mobile(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }
    
    @Override
    public String toString() {
        return "Mobile [brand=" + brand + ", model=" + model + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mobile mobile = (Mobile) obj;
        return brand.equals(mobile.brand) && model.equals(mobile.model);
    }
}

public class ObjectClassDemo {
    public static void main(String[] args) {
        Mobile mobile1 = new Mobile("Apple", "iPhone 13");
        Mobile mobile2 = new Mobile("Apple", "iPhone 13");
        Mobile mobile3 = new Mobile("Samsung", "Galaxy S21");
        
        // Using toString() method
        System.out.println(mobile1); // This will call mobile1.toString()
        
        // Using equals() method
        System.out.println("mobile1 equals mobile2: " + mobile1.equals(mobile2)); // true
        System.out.println("mobile1 equals mobile3: " + mobile1.equals(mobile3)); // false
    }
}
