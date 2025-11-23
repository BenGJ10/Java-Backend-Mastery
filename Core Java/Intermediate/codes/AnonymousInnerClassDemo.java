// Implementing all different types of anonymous and nested inner classes

class OuterClass {
    private int outerField = 10;
    public static int staticOuterField = 20;

    // Non-static nested inner class
    class InnerClass {
        public void display() {
            System.out.println("Outer Field: " + outerField);
        }
    }   

    // Static nested inner class
    static class StaticNestedClass {
        public void display() {
            System.out.println("Static Outer Field: " + staticOuterField);
        }
    }

    // Local inner class
    void localInnerClassDemo() {
        class LocalInnerClass {
            public void display() {
                System.out.println("Inside Local Inner Class");
            }
        }
        LocalInnerClass lic = new LocalInnerClass();
        lic.display();
    }

    void show() {
        System.out.println("Inside Outer Class");
    }
}

public class AnonymousInnerClassDemo {
    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        outer.show();
        
        // Using non-static nested inner class
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.display();

        // Using static nested inner class
        OuterClass.StaticNestedClass staticNested = new OuterClass.StaticNestedClass();
        staticNested.display();

        // Using local inner class
        outer.localInnerClassDemo();

        // Using anonymous inner class
        OuterClass anonOuter = new OuterClass() {
            void show() {
                System.out.println("Inside Anonymous Inner Class");
            }
        };
        anonOuter.show();
    }
}
