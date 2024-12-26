import java.sql.SQLException;
import java.util.List;

public class userService {
    private userMegement userDao;

    public userService(userMegement userDao) {
        this.userDao = userDao;
    }

    // 创建新用户
    public void createUser(User user) throws SQLException {
        userDao.addUser(user);
    }

    // 删除用户
    public void deleteUser(int id) throws SQLException {
        userDao.deleteUser(id);
    }

    // 更新用户信息
    public void updateUser(User user) throws SQLException {
        userDao.updateUser(user);
    }

    // 查询用户（根据id查询）
    public List<User> getUserById(int id) throws SQLException {
        return userDao.queryUser(id);
    }
}
