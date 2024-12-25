import java.sql.SQLException;
import java.util.List;

public interface userMegementDao {
    void create() throws SQLException;
    void delete(String id) throws SQLException;
    void update(String id, String name, int age, int balance) throws SQLException;
    List<User> query(String id) throws SQLException;
}
