import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userMegement implements userMegementDao {

    private Connection connection;

    // 构造函数，初始化数据库连接
    public userMegement(Connection connection) {
        this.connection = connection;
    }

    // 创建用户
    @Override
    public void create(User user) throws SQLException {
        String query = "INSERT INTO library_user (name, age, balance, username, password, user_type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.setInt(3, user.getBalance());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getUserType());
            stmt.executeUpdate();
        }
    }

    // 删除用户
    @Override
    public void delete(int userId) throws SQLException {
        String query = "DELETE FROM library_user WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // 更新用户信息
    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE library_user SET name = ?, age = ?, balance = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.setInt(3, user.getBalance());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        }
    }

    // 查询所有用户
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, age, balance FROM library_user";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int balance = rs.getInt("balance");
                users.add(new User(id, name, age, balance));
            }
        }
        return users;
    }

    // 借书
    @Override
    public void borrowBook(int userId, int bookId) throws SQLException {
        String query = "INSERT INTO user_book (user_id, book_id, borrow_date) VALUES (?, ?, GETDATE())";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        }

        // 更新图书数量
        String updateQuery = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    // 还书
    @Override
    public void returnBook(int userId, int bookId) throws SQLException {
        // 删除用户借书记录
        String query = "DELETE FROM user_book WHERE user_id = ? AND book_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        }

        // 更新图书数量
        String updateQuery = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setInt(1, bookId);
            stmt.executeUpdate();
        }
    }

    public void updateUserBalance(int userId, int newBalance) throws SQLException {
        String query = "UPDATE library_user SET balance = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newBalance);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    @Override
    public User login(String username, String password) throws SQLException {
        String query = "SELECT * FROM library_user WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
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
    }

    @Override
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
