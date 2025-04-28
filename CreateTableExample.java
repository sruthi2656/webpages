import java.sql.*;

public class CreateTableExample {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/khayati", "root", "tiger");

            // Create statement object
            Statement stmt = con.createStatement();

            // Drop table if it already exists (resetting the table)
            stmt.executeUpdate("DROP TABLE IF EXISTS student");

            // Create table with PRIMARY KEY (only once)
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS student(" +
                "rollno INT(5) PRIMARY KEY, " +
                "sname VARCHAR(20))"
            );

            // Insert data while avoiding duplicates (based on PRIMARY KEY)
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (101, 'Ravi')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (102, 'Priya')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (103, 'Amit')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (104, 'Neha')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (105, 'Rahul')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (106, 'Rishi')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (107, 'Ram')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (108, 'Hamsi')");
            stmt.executeUpdate("INSERT IGNORE INTO student VALUES (110, 'Anvi')");

            System.out.println("Student data inserted (if not already present).");

            // ✅ UPDATE a record in the database
            stmt.executeUpdate("UPDATE student SET sname = 'Ravindra' WHERE rollno = 101");
            System.out.println("Updated name for roll number 101 to 'Ravindra'.");
            // ✅ DELETE a record from the database
            stmt.executeUpdate("DELETE FROM student WHERE rollno = 105");
            System.out.println("Deleted student with roll number 105.");


            // Optional: Remove duplicate rows (if necessary)
            stmt.executeUpdate(
                "DELETE FROM student " +   
                "WHERE (rollno, sname) NOT IN ( " +
                "    SELECT * FROM ( " +
                "        SELECT MIN(rollno), sname FROM student GROUP BY sname " +
                "    ) AS temp " +
                ")"
            );

            // Display all records
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            System.out.println("\nRoll No\tName");
            while (rs.next()) {
                int roll = rs.getInt("rollno");
                String name = rs.getString("sname");
                System.out.println(roll + "\t" + name);
            }

            // Clean up and close resources
            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}