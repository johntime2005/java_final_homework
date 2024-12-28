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
            stmt.setInt(4, 10);
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
                       // rs.getInt("age"),
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

    public void borrowBook(int userId, int bookId) throws SQLException {
        // 检查是否被借出
        String checkQuery = "SELECT isborrowed FROM books WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, bookId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    boolean isBorrowed = rs.getBoolean("isborrowed");
                    if (!isBorrowed) {
                        // 插入借书记录
                        String insertQuery = "INSERT INTO user_book (user_id, book_id, borrow_date) VALUES (?, ?, GETDATE())";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setInt(2, bookId);
                            insertStmt.executeUpdate();
                        }

                        // 更新图书状态
                        String updateBookQuery = "UPDATE books SET isborrowed = ? WHERE id = ?";
                        try (PreparedStatement updateBookStmt = connection.prepareStatement(updateBookQuery)) {
                            updateBookStmt.setBoolean(1, true);
                            updateBookStmt.setInt(2, bookId);
                            updateBookStmt.executeUpdate();
                        }

                        // 更新用户余额
                        String updateBalanceQuery = "UPDATE library_user SET balance = balance - 1 WHERE id = ?";
                        try (PreparedStatement updateBalanceStmt = connection.prepareStatement(updateBalanceQuery)) {
                            updateBalanceStmt.setInt(1, userId);
                            updateBalanceStmt.executeUpdate();
                        }
                    } else {
                        throw new SQLException("该书籍已被借出，无法借阅。");
                    }
                } else {
                    throw new SQLException("书籍ID不存在。");
                }
            }
        } catch (SQLException e) {
            // 处理SQL异常
            throw new SQLException("借书过程中发生错误: " + e.getMessage(), e);
        }
    }

    // 还书

    public void returnBook(int userId, int bookId) throws SQLException {
        // 检查是否被借出
        String checkQuery = "SELECT isborrowed FROM books WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, bookId);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    boolean isBorrowed = rs.getBoolean("isborrowed");
                    if (isBorrowed) {
                        // 插入还书记录
                        String insertQuery = "INSERT INTO user_book (user_id, book_id, return_date) VALUES (?, ?, GETDATE())";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setInt(2, bookId);
                            insertStmt.executeUpdate();
                        }

                        // 更新图书状态
                        String updateBookQuery = "UPDATE books SET isborrowed = ? WHERE id = ?";
                        try (PreparedStatement updateBookStmt = connection.prepareStatement(updateBookQuery)) {
                            updateBookStmt.setBoolean(1, false);
                            updateBookStmt.setInt(2, bookId);
                            updateBookStmt.executeUpdate();
                        }

                        // 更新用户余额
                        String updateBalanceQuery = "UPDATE library_user SET balance = balance + 1 WHERE id = ?";
                        try (PreparedStatement updateBalanceStmt = connection.prepareStatement(updateBalanceQuery)) {
                            updateBalanceStmt.setInt(1, userId);
                            updateBalanceStmt.executeUpdate();
                        }
                    } else {
                        throw new SQLException("还书失败，该书籍未被借出。");
                    }
                } else {
                    throw new SQLException("还书失败，书籍ID不存在。");
                }
            }
        } catch (SQLException e) {
            // 处理SQL异常
            throw new SQLException("还书过程中发生错误: " + e.getMessage(), e);
        }
    }



    //更新用户余额
//    public void updateUserBalance(int userId, int amount) throws SQLException {
//        String query = "UPDATE library_user SET balance = balance + ? WHERE id = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, amount);
//            stmt.setInt(2, userId);
//            stmt.executeUpdate();
//        }
//    }

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
                          //  rs.getInt("age"),
                            rs.getInt("balance"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("user_type")
                    );
                }
            }
        }
        return null;
    }

    //检查用户名是否存在
    public boolean isUsernameExists(String username) throws SQLException {
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