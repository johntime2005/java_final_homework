package utils;

import model.Book;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    
    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setIsborrowed(rs.getBoolean("is_borrowed"));
                books.add(book);
            }
        }
        return books;
    }
    
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM library_users";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));  // 确保这里的字段名与数据库一致
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("user_type"));
                user.setBalance(rs.getInt("balance"));
                users.add(user);
            }
        }
        return users;
    }
}
