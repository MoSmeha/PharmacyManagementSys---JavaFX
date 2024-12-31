package pharmacy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/pharmacy", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
           
            e.printStackTrace();
            return null;
        }
    }
}
    
   