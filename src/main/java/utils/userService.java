package utils;

import java.sql.SQLException;
import java.util.List;

import dao.userMegementDao;
import model.User;

public class userService {

    private userMegementDao userDao;
    private BookService bookService;

    public userService(userMegementDao userDao, BookService bookService) {
        this.userDao = userDao;
        this.bookService = bookService;
    }

    // 开户
    public void create(User user) throws SQLException {
        userDao.create(user);
    }

    // 销户
    public void delete(int userId) throws SQLException {
        userDao.delete(userId);
    }

    // 修改用户信息
    public void update(int age, String gender, String phonenumber, String schoolid, String birthdate, int id) throws SQLException {
        userDao.update(age,  gender, phonenumber,  schoolid,  birthdate, id);
    }

    // 查询所有用户
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    // 借书
    public void borrowBook(int userId, int bookId) throws SQLException {
        userDao.borrowBook(userId, bookId);
//        bookService.updateBookQuantity(bookId, 0);//没有找到错误

    }

    // 还书
    public void returnBook(int userId, int bookId) throws SQLException {
//        bookService.updateBookQuantity(bookId, 1);
        userDao.returnBook(userId, bookId);
    }

}
