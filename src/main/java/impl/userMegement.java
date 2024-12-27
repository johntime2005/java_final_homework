package impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.userMegementDao;
import model.User;

public class userMegement implements userMegementDao {

    private Connection connection;

    // 构造函数，初始化数据库连接
    public userMegement(Connection connection) {
        this.connection = connection;
    }

    // 创建用户(使用简化版的user)

    public void create(User user) throws SQLException {
        String query = "INSERT INTO library_user (username, password, user_type, balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getUserType());
            stmt.setInt(4, user.getBalance());
            stmt.executeUpdate();
        }
    }

    // 删除用户

    public void delete(int userId) throws SQLException {
        String query = "DELETE FROM library_user WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // 更新用户信息
    public void update(User user) throws SQLException {
        String query = "UPDATE library_user SET username = ?, password = ?, user_type = ?, balance = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getUserType());
            stmt.setInt(4, user.getBalance());
            stmt.setInt(5, user.getId());
            stmt.executeUpdate();
        }
    }

    // 查询所有用户

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM library_user";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getInt("age"),
                        rs.getInt("balance"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("user_type")
                ));
            }
        }
        return users;
    }

    // 借书

    public void borrowBook(int userId, String isbn) throws SQLException {
        // 插入借书记录
        String insertQuery = "INSERT INTO user_book (user_id, book_id, borrow_date) VALUES (?, (SELECT id FROM books WHERE isbn = ?), GETDATE())";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setInt(1, userId);
            insertStmt.setString(2, isbn);
            insertStmt.executeUpdate();
        }

        // 更新图书数量
        String updateBookQuery = "UPDATE books SET quantity = quantity - 1 WHERE isbn = ?";
        try (PreparedStatement updateBookStmt = connection.prepareStatement(updateBookQuery)) {
            updateBookStmt.setString(1, isbn);
            updateBookStmt.executeUpdate();
        }

        // 更新用户余额
        String updateBalanceQuery = "UPDATE library_user SET balance = balance - 1 WHERE id = ?";
        try (PreparedStatement updateBalanceStmt = connection.prepareStatement(updateBalanceQuery)) {
            updateBalanceStmt.setInt(1, userId);
            updateBalanceStmt.executeUpdate();
        }
    }


    // 还书

    public void returnBook(int userId, String isbn) throws SQLException {
        // 删除用户借书记录
        String deleteQuery = "DELETE FROM user_book WHERE user_id = ? AND book_id = (SELECT id FROM books WHERE isbn = ?)";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, userId);
            deleteStmt.setString(2, isbn);
            deleteStmt.executeUpdate();
        }

        // 更新图书数量
        String updateQuery = "UPDATE books SET quantity = quantity + 1 WHERE isbn = ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
            updateStmt.setString(1, isbn);
            updateStmt.executeUpdate();
        }
    }

//用户登录验证
    public User login(String username, String password) throws SQLException {
        String query = "SELECT * FROM library_user WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getInt("age"),
                            rs.getInt("balance"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("user_type")
                    );
                }
            }
        }
        return null;
//检查用户名是否存在
        public boolean isUsernameExists (String username) throws SQLException {
            String query = "SELECT COUNT(*) FROM library_user WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
            return false;
        }
    }
}