package test.library

import java.sql.SQLException;
import java.util.List;
public interface bookMegementDao {
    void addBook(String bookName, String author, String publisher, String publishDate, String ISBN) throws SQLException;
    void deleteBook(String bookName) throws SQLException;
    void updateBook(String bookName, String author, String publisher, String publishDate, String ISBN) throws SQLException;
    List<Book> queryBook(String bookName) throws SQLException;
    List<Book> queryAllBooks() throws SQLException;
    InventoryStats getInventoryStats() throws SQLException;
}
