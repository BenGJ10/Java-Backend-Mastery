// Practice code for JDBC insertion in Java
import java.sql.*;

public class JDBCInsertionDemo {
    public static void main(String[] args){
        // Initializing connection variables
        String url = "jdbc:postgresql://localhost:5432/demo"; 
        String user = "postgres";
        String password = "password";

        try{
            // Load the JDBC driver - Optional for newer JDBC versions
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully.");

            // Create a statement
            String insertSql = "INSERT INTO management (student_id, student_name, department, marks, aws_certified) VALUES (?, ?, ?, ?, ?)";

            // We will use PreparedStatement for safer insertion
            PreparedStatement pstmt = conn.prepareStatement(insertSql);
            
            // Sample data to insert
            Object[][] students = {
                {6, "Lionel Messi", "CSE", 90, true},
                {7, "Pedri Gonzalez", "IT", 78, false},
            };

            for(Object[] student: students){
                pstmt.setInt(1, (Integer)student[0]);
                pstmt.setString(2, (String)student[1]);
                pstmt.setString(3, (String)student[2]);
                pstmt.setInt(4, (Integer)student[3]);
                pstmt.setBoolean(5, (Boolean)student[4]);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Inserted " + rowsAffected + " row(s) for student: " + student[1]);
            }

            // Fetching and displaying those who are AWS certified
            String selectSql = "SELECT student_id, student_name, department FROM management WHERE aws_certified = true";
            pstmt = conn.prepareStatement(selectSql);
            
            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Process the result set
            System.out.println("AWS Certified Students:");
            while(rs.next()){
                int id  = rs.getInt("student_id");
                String name = rs.getString("student_name");
                String dept = rs.getString("department");
                System.out.println("Student ID: " + id + ", Name: " + name + ", Department: " + dept);
            }

            // Close the resources
            rs.close();
            pstmt.close();
            conn.close();
        }
        catch(Exception e){
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
}
