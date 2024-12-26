import java.sql.SQLException;
import java.util.List;

public class userService {
    private final userMegementDao userdao;

    public userService(userMegementDao userdao) {
        this.userdao = userdao;
    }
    public void create() {
        try {
            userdao.create();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(String id) {
        try {
            userdao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(String id, String name, int age, int balance) {
        try {
            userdao.update(id, name, age, balance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> query(String id) {
        try {
            return userdao.query(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
