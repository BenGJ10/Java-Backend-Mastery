// enum extends the java.lang.Enum class internally
enum Status { // enums are basically a special type of class
    RUNNING, // these are the objects of the enum class Status
    PENDING,
    COMPLETED,
    FAILED;
}

public class EnumDemo {
    public static void main(String[] args) {
        Status currentStatus = Status.RUNNING; // assigning an enum constant to a variable

        switch (currentStatus) { // using enum in a switch statement
            case RUNNING:
                System.out.println("The process is currently running.");
                break;
            case PENDING:
                System.out.println("The process is pending.");
                break;
            case COMPLETED:
                System.out.println("The process has been completed.");
                break;
            case FAILED:
                System.out.println("The process has failed.");
                break;
        }

        // Iterating over all enum constants
        System.out.println("All possible statuses:");
        for (Status status : Status.values()) {
            System.out.println(status);
        }
    }
}
