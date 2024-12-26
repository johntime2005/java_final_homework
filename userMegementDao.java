import java.sql.SQLException;
import java.util.List;

public interface userMegementDao {
    void create() throws SQLException;
    void addUser(User user) throws SQLException;
    void deleteUser(int id) throws SQLException;
    void updateUser(User user) throws SQLException;
    List<User> queryUser(int id) throws SQLException;
}
