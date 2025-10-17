package util;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("✅ Ket noi thanh cong!");
        } catch (Exception e) {
            System.out.println("❌ Loi ket noi CSDL:");
            e.printStackTrace();
        }
    }
}
