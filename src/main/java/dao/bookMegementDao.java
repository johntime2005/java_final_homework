package main.java.dao;
import java.sql.SQLException;
import java.util.List;

import main.java.model.Book;

public interface bookMegementDao {
    // 添加书籍
    void addBook(Book book) throws SQLException;

    // 根据书名查询书籍
    List<Book> getBooksByTitle(String title) throws SQLException;

    // 根据id查询书籍
    Book getBookById(int id) throws SQLException;

    // 更新书籍信息
    void updateBookQuantity(int bookId, int quantity) throws SQLException;

    // 删除书籍
    void deleteBook(int id) throws SQLException;

    // 获取所有书籍
    List<Book> getAllBooks() throws SQLException;

    // 多条件查询书籍
    List<Book> searchBooks(String title, String author, String publisher) throws SQLException;

    void updateBook(Book book) throws SQLException;
}
