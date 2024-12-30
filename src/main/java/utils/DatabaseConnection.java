package utils;

import model.Book;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class DatabaseConnection {
    // SQL Server 连接配置
    private static final String SERVER_URL = "jdbc:sqlserver://116.205.125.206:1433;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    private static final String DB_URL = SERVER_URL + "databaseName=library;";
    private static final String USER = "sa"; // 替换为你的SQL Server用户名
    private static final String PASSWORD = "952891332wW!"; // 替换为你的SQL Server密码
    private static Connection connection;

    static {
        try {
            // 加载 SQL Server JDBC 驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("无法加载 SQL Server JDBC 驱动!");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // 先尝试连接到特定数据库
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            } catch (SQLException e) {
                // 如果失败，尝试连接到服务器并创建数据库
                connection = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
                DatabaseInitializer.initializeDatabase(connection);
                // 重新连接到新创建的数据库
                connection.close();
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加数据库操作方法
    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("press"));
                book.setIsborrowed(rs.getBoolean("is_borrowed"));
                books.add(book);
            }
        }
        return books;
    }
    
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("user_type"));
                user.setBalance(rs.getInt("balance"));
                users.add(user);
            }
        }
        return users;
    }
}
