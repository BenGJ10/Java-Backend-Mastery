import java.sql.*;

public class CRUDCompleteDemo {
    public static void main(String[] args){
        String url = "jdbc:postgresql://localhost:5432/demo"; 
        String user = "postgres";
        String password = "password";    

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully.");

            Statement stmt = conn.createStatement();

            String insertSql = """
                INSERT INTO management (student_id, student_name, department, marks, aws_certified)
                VALUES (8, 'Kylian Mbappe', 'CSE', 88, true)
                """;

            int rowsInserted = stmt.executeUpdate(insertSql);
            System.out.println("Insertion in Progress.. | Rows Inserted: " + rowsInserted);

            String selectSql = """
                SELECT student_id, student_name, department, marks
                FROM management WHERE student_id = 8
                """;

            ResultSet rs = stmt.executeQuery(selectSql);
            while(rs.next()) {
                System.out.println(
                    "Inserted Student ID: " + rs.getInt("student_id") +
                    ", Name: " + rs.getString("student_name") +
                    ", Dept: " + rs.getString("department") +
                    ", Marks: " + rs.getInt("marks")
                );
            }
            rs.close();


            String updateSql = """
                UPDATE management SET marks = 95 WHERE student_id = 8
                """;

            int rowsUpdated = stmt.executeUpdate(updateSql);
            System.out.println("Updation in Progress.. | Rows Updated: " + rowsUpdated);

            ResultSet rsAfterUpdate = stmt.executeQuery(selectSql);
            while(rsAfterUpdate.next()) {
                System.out.println(
                    "Updated Student ID: " + rsAfterUpdate.getInt("student_id") +
                    ", Name: " + rsAfterUpdate.getString("student_name") +
                    ", Dept: " + rsAfterUpdate.getString("department") +
                    ", Marks: " + rsAfterUpdate.getInt("marks")
                );
            }
            rsAfterUpdate.close();


            String deleteSql = """
                DELETE FROM management WHERE student_id = 8
                """;

            int rowsDeleted = stmt.executeUpdate(deleteSql);
            System.out.println("Deletion in Progress.. | Rows Deleted: " + rowsDeleted);

            stmt.close();
            conn.close();
        }
        catch (Exception e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
        }
    }
}