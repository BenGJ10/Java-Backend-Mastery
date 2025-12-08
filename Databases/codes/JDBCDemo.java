import java.sql.*;

public class JDBCDemo {
    public static void main(String[] args){
        
        // Initializing connection variables
        String url = "jdbc:postgresql://localhost:5432/demo"; // default for PostgreSQL is 5432
        String user = "postgres";
        String password = "Kuriakose@2005";

        try {
            // 1. Load the JDBC driver
            Class.forName("org.postgresql.Driver");

            // 2. Establish the connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully.");

            // 3. Create a statement
            Statement stmt = conn.createStatement();

            // 4. Execute a query
            String sql = "SELECT \"StudentID\", \"StudentName\", \"Department\", \"Marks\" " +
            "FROM management WHERE \"Marks\" > 50"; // Adjusted for PostgreSQL case sensitivity

            ResultSet rs = stmt.executeQuery(sql);

            // 5. Process the result set
            while(rs.next()){
                int id  = rs.getInt("StudentId");
                String name = rs.getString("StudentName");
                String dept = rs.getString("Department");
                int marks = rs.getInt("Marks"); 
                System.out.println("ID: " + id + ", Name: " + name + ", Department: " + dept + ", Marks: " + marks);

            }            
            // 6. Close the resources
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e){
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
}
