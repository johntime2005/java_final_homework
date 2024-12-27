package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQlconnectiontest {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://116.205.125.206:1433;databaseName=library;encrypt=true;trustServerCertificate=true;";
        String username = "sa";
        String password = "952891332wW!";

        Connection conn = null;
        Statement stmt = null;

        try {
            // 确保驱动程序已加载（对于JDBC 4.0及以上版本，这一步通常是自动的，但为了确保兼容性，仍然可以保留）
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // 建立连接
            conn = DriverManager.getConnection(url, username, password);

            // 创建Statement对象
            stmt = conn.createStatement();

            // SQL语句：创建一个名为"Users"的表，包含ID和Name列
            String createTableSQL = "CREATE TABLE qU(" +
                    "ID INT PRIMARY KEY IDENTITY(1,1) NOT NULL, " +
                    "Name NVARCHAR(50) NOT NULL)";

            // 执行SQL语句
            stmt.executeUpdate(createTableSQL);

            System.out.println("成功连接到数据库并创建了表！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC驱动程序未找到！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败或SQL执行出错！");
        } finally {
            // 关闭Statement和Connection
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}