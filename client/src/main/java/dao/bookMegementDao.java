package dao;

import java.sql.SQLException;
import java.util.List;
import model.Book;

public interface bookMegementDao {
    void addBook(Book book) throws SQLException;
    List<Book> getBooksByTitle(String title) throws SQLException;
    Book getBookById(int id) throws SQLException;
    void deleteBook(int id) throws SQLException;
    List<Book> getAllBooks() throws SQLException;
    List<Book> searchBooks(String title, String author, String publisher) throws SQLException;
    void updateBook(Book book) throws SQLException;
    void batchInsertBooks(List<Book> books) throws SQLException;
}
