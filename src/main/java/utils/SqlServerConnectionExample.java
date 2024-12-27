package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class SqlServerConnectionExample {

    // 数据库URL、用户名和密码
    private static final String DB_URL = "jdbc:sqlserver://116.205.125.206:1433;"
            + "databaseName=library;"
            + "encrypt=true;"
            + "trustServerCertificate=true;"; // 添加信任服务器证书
    private static final String USER = "sa";
    private static final String PASS = "952891332wW!";

    private static void add(String name, int age) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(
                 "INSERT INTO [wangzhangzhuo].[dbo].[user] (name, age) VALUES (?, ?)")) {  // 修改这里，给表名加上方括号
            
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("添加成功！");
            }
            
        } catch (SQLException se) {
            System.out.println("数据库错误: " + se.getMessage());
        } catch (Exception e) {
            System.out.println("程序错误: " + e.getMessage());
        }
    }

    private static void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            
            String sql = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'user') " +
                        "CREATE TABLE [user] (" +
                        "id INT IDENTITY(1,1) PRIMARY KEY," +
                        "name NVARCHAR(50)," +
                        "age INT" +
                        ")";
            
            stmt.executeUpdate(sql);
            System.out.println("表创建成功或已存在！");
            
        } catch (SQLException se) {
            System.out.println("创建表时发生错误: " + se.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册JDBC驱动程序（不再需要显式调用Class.forName()，因为从JDBC 4.0开始，驱动程序会自动注册）
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // 打开连接
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            // 只查询用户表，不包含系统表
            String sql = "SELECT TABLE_NAME FROM library.INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_NAME NOT LIKE 'sys%'";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("用户表列表：");
            while (rs.next()) {
                System.out.println("表名: " + rs.getString("TABLE_NAME"));
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理JDBC错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理Class.forName错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            } // 什么都不做
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("查询完成");
        createTable();  // 先创建表
        add("张三", 20);  // 再添加数据
    }
}