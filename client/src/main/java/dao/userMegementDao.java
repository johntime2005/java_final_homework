package dao;

import java.sql.SQLException;
import java.util.List;
import model.UserBook;
import model.User;

public interface userMegementDao {

    // 创建用户
    void create(User user) throws SQLException;

    // 删除用户
    void delete(int userId) throws SQLException;

    // 更新用户信息
    void update(int age, String gender, String phonenumber, String schoolid, String birthdate, int id) throws SQLException;

    // 查询所有用户
    List<User> getAllUsers() throws SQLException;

    // 借书
    void borrowBook(int userId, int bookId) throws SQLException;

    // 还书
    void returnBook(int userId, int bookId) throws SQLException;
//    void updateUserBalance(int userId, int newBalance) throws SQLException;

    // 登录
    User login(String username, String password) throws SQLException;

    // 检查用户名是否存在
    boolean isUsernameExists(String username) throws SQLException;
    //获取所有用户借阅记录
    List<UserBook> getAllUserBooks() throws SQLException;
    //获取用户余额
    int getUserBalance(int userId) throws SQLException;
}
