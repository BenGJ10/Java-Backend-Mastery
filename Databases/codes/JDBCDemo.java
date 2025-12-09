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
            String sql = "SELECT student_id, student_name, department, marks FROM management WHERE marks > 70";

            ResultSet rs = stmt.executeQuery(sql);

            // 5. Process the result set
            while(rs.next()){
                int id  = rs.getInt("student_id");
                String name = rs.getString("student_name");
                String dept = rs.getString("department");
                int marks = rs.getInt("marks"); 
                System.out.println("Student ID: " + id + ", Name: " + name + ", Department: " + dept + ", Marks: " + marks);

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
