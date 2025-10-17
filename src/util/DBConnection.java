package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/taskmanager?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // <--- để trống vì bạn không có mật khẩu

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Loi ket noi CSDL:");
            e.printStackTrace();
            return null;
        }
    }
}
