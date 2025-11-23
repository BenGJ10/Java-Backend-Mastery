class MainClass{
    public void show(){
        System.out.println("This is the MainClass.");
    }

    static class InnerStaticClass{
        public void show(){
            System.out.println("This is the InnerStaticClass.");
        }
    }

    class InnerClass{
        public void show(){
            System.out.println("This is the InnerClass.");
        }
    }
}

public class InnerClassDemo {
    public static void main(String[] args) {
        MainClass mainObj = new MainClass();
        mainObj.show();

        // Creating an instance of the inner static class
        // This will create a bytecode like MainClass$InnerStaticClass
        MainClass.InnerStaticClass innerObj = new MainClass.InnerStaticClass();
        innerObj.show();

        // Creating an instance of the inner class
        // This will create a bytecode like MainClass$InnerClass
        MainClass.InnerClass innerClassObj = mainObj.new InnerClass();
        innerClassObj.show();
    }
}
