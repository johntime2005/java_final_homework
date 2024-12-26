import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userMegement implements userMegementDao {
    private Connection conn;

    public userMegement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY, name VARCHAR(255), age INT, balance INT)";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    @Override
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO user (id, name, age, balance) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, user.getId());
        stmt.setString(2, user.getName());
        stmt.setInt(3, user.getAge());
        stmt.setInt(4, user.getBalance());
        stmt.executeUpdate();
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE user SET name = ?, age = ?, balance = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getName());
        stmt.setInt(2, user.getAge());
        stmt.setInt(3, user.getBalance());
        stmt.setInt(4, user.getId());
        stmt.executeUpdate();
    }

    @Override
    public List<User> queryUser(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getInt("balance")));
        }
        return users;
    }
}
