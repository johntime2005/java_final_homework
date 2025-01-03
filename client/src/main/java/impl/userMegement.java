package impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import dao.userMegementDao;
import model.User;
import model.UserBook;
import utils.ConnectToServer;

public class userMegement implements userMegementDao {
    private ConnectToServer server;

    public userMegement() {
        this.server = new ConnectToServer();
    }

    public void create(User user) throws SQLException {
        String sql = String.format(
            "INSERT INTO library_user (username, password, user_type, balance) VALUES ('%s', '%s', '%s', %d)",
            user.getUsername(), user.getPassword(), user.getUserType(), 10
        );
        server.sendvoidRequest(sql);
    }

    public void delete(int userId) throws SQLException {
        String sql = String.format("DELETE FROM library_user WHERE id = %d", userId);
        server.sendvoidRequest(sql);
    }

    public void update(int age, String gender, String phonenumber, String schoolid, String birthdate, int id) throws SQLException {
        String sql = String.format(
            "UPDATE library_user SET age = %d, gender = '%s', phonenumber = '%s', schoolid = '%s', birthdate = '%s' WHERE id = %d",
            age, gender, phonenumber, schoolid, birthdate, id
        );
        server.sendvoidRequest(sql);
    }


    public List<UserBook> getAllUserBooks() throws SQLException {
        String sql = "SELECT user_id, book_id, borrow_date, return_date FROM user_book";
        TypeReference<List<UserBook>> typeRef = new TypeReference<List<UserBook>>() {};
        List<UserBook> userBooks = server.getObjectResponse(sql, typeRef);
        if (userBooks == null) {
            userBooks = new ArrayList<>(); // 初始化为空列表
        }
        return userBooks;
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM library_user";
        TypeReference<List<User>> typeRef = new TypeReference<List<User>>() {};
        return server.getObjectResponse(sql, typeRef.getType());
    }

    public void borrowBook(int userId, int bookId) throws SQLException {
        // 检查用户余额
        int balance = getUserBalance(userId);
        if (balance <= 0) {
            throw new SQLException("余额不足，无法借书。");
        }

        // 检查是否被借出
        String checkSql = String.format("SELECT isborrowed FROM books WHERE id = %d", bookId);
        List<Map<String, String>> response = server.getObjectResponse(checkSql, new TypeReference<List<Map<String, String>>>(){});
        if (response == null || response.isEmpty()) {
            throw new SQLException("未找到该书籍。");
        }
        String isBorrowedStr = response.get(0).get("isborrowed");
        Boolean isBorrowed = "1".equals(isBorrowedStr);

        if (!isBorrowed) {
            // 插入借书记录
            String borrowSql = String.format(
                "INSERT INTO user_book (user_id, book_id, borrow_date) VALUES (%d, %d, GETDATE())",
                userId, bookId
            );
            server.sendvoidRequest(borrowSql);

            // 更新图书状态
            String updateBookSql = String.format(
                "UPDATE books SET isborrowed = true WHERE id = %d",
                bookId
            );
            server.sendvoidRequest(updateBookSql);

            // 更新用户余额
            String updateBalanceSql = String.format(
                "UPDATE library_user SET balance = balance - 1 WHERE id = %d",
                userId
            );
            server.sendvoidRequest(updateBalanceSql);
        } else {
            throw new SQLException("该书籍已被借出，无法借阅。");
        }
    }

    public void returnBook(int userId, int bookId) throws SQLException {
        String checkSql = String.format("SELECT isborrowed FROM books WHERE id = %d", bookId);
        List<Map<String, String>> response = server.getObjectResponse(checkSql, new TypeReference<List<Map<String, String>>>(){});
        if (response == null || response.isEmpty()) {
            throw new SQLException("未找到该书籍。");
        }
        String isBorrowedStr = response.get(0).get("isborrowed");
        Boolean isBorrowed = "1".equals(isBorrowedStr);

        if (isBorrowed) {
            // 更新还书记录
            String returnSql = String.format(
                "UPDATE user_book SET return_date = GETDATE() WHERE user_id = %d AND book_id = %d",
                userId, bookId
            );
            server.sendvoidRequest(returnSql);

            // 更新图书状态
            String updateBookSql = String.format(
                "UPDATE books SET isborrowed = false WHERE id = %d",
                bookId
            );
            server.sendvoidRequest(updateBookSql);

            // 更新用户余额
            String updateBalanceSql = String.format(
                "UPDATE library_user SET balance = balance + 1 WHERE id = %d",
                userId
            );
            server.sendvoidRequest(updateBalanceSql);
        } else {
            throw new SQLException("还书失败，该书籍未被借出。");
        }
    }

    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM library_user WHERE username='" + username + "' AND password='" + password + "'";
        ConnectToServer connectToServer = new ConnectToServer();
        List<User> result = connectToServer.getObjectResponse(sql, new com.fasterxml.jackson.core.type.TypeReference<List<User>>() {});
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    // 修改 getUserBalance 方法以正确反序列化服务器响应
    public int getUserBalance(int userId) throws SQLException {
        String sql = String.format("SELECT balance FROM library_user WHERE id = %d", userId);
        List<Map<String, String>> response = server.getObjectResponse(sql, new TypeReference<List<Map<String, String>>>(){});
        int balance = 0;
        if (response != null && !response.isEmpty()) {
            String balanceStr = response.get(0).get("balance");
            if (balanceStr != null) {
                try {
                    balance = Integer.parseInt(balanceStr);
                } catch (NumberFormatException e) {
                    // 记录或处理解析错误
                }
            }
        }
        return balance;
    }

    
    public boolean isUsernameExists(String username) throws SQLException {
        String sql = String.format(
            "SELECT COUNT(*) as count FROM library_user WHERE username = '%s'",
            username
        );
        Integer count = server.getObjectResponse(sql, Integer.class);
        return count != null && count > 0;
    }
    public void deleteUser(int userId) throws SQLException {
        String sql = String.format("DELETE FROM library_user WHERE id = %d", userId);
        server.sendvoidRequest(sql);
    }
}
