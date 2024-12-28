package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.bookMegementDao;
import model.Book;

public class BookService {

    private bookMegementDao bookDao;

    public BookService(bookMegementDao bookDao) {
        this.bookDao = bookDao;
    }

    // 根据书名查询
    public List<Book> getBookByTitle(String title) throws SQLException {
        return bookDao.getBooksByTitle(title);
    }

    // 获取所有书籍
    public List<Book> getAllBooks() throws SQLException {
        return bookDao.getAllBooks();
    }

    // 添加书籍
    public void addBook(Book book) throws SQLException {
        bookDao.addBook(book);
    }

    // 更新书籍
    public void updateBook(Book book) throws SQLException {
        bookDao.updateBook(book);
    }

    // 根据ID获取书籍
    public Book getBookById(int id) throws SQLException {
        return bookDao.getBookById(id);
    }

    // 更新书籍数量
//    public void updateBookQuantity(int bookId, int quantity) throws SQLException {
//        bookDao.updateBookQuantity(bookId, quantity);
//    }

    // 根据书名、作者和出版社搜索书籍
    public List<Book> searchBooks(String title, String author, String publisher) throws SQLException {
        return bookDao.searchBooks(title, author, publisher);
    }
}
