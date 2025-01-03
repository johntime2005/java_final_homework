package impl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import dao.bookMegementDao;
import model.Book;
import utils.ConnectToServer;

public class bookMegement implements bookMegementDao {
    private ConnectToServer server;

    public bookMegement() {
        this.server = new ConnectToServer();
    }

    // 添加书籍
    public void addBook(Book book) throws SQLException {
        String sql = String.format(
            "INSERT INTO books (title, author, publisher, isborrowed) VALUES ('%s', '%s', '%s', false)",
            book.getTitle(), book.getAuthor(), book.getPublisher()
        );
        server.sendvoidRequest(sql);
    }

    // 根据书名查询书籍
    public List<Book> getBooksByTitle(String title) throws SQLException {
        String sql = String.format(
            "SELECT * FROM books WHERE title LIKE '%%%s%%'",
            title
        );
        TypeReference<List<Book>> typeRef = new TypeReference<List<Book>>() {};
        return server.getObjectResponse(sql, typeRef.getType());
    }

    // 根据ID查询书籍
    public Book getBookById(int bookId) throws SQLException {
        String sql = String.format(
            "SELECT * FROM books WHERE id = %d",
            bookId
        );
        return server.getObjectResponse(sql, Book.class);
    }

    // 更新书籍信息
    public void updateBook(Book book) throws SQLException {
        String sql = String.format(
            "UPDATE books SET title = '%s', author = '%s', publisher = '%s', isborrowed = %b WHERE id = %d",
            book.getTitle(), book.getAuthor(), book.getPublisher(), 
            book.getIsborrowed(), book.getId()
        );
        server.sendvoidRequest(sql);
    }

    // 删除书籍
    public void deleteBook(int id) throws SQLException {
        String sql = String.format(
            "DELETE FROM books WHERE id = %d",
            id
        );
        server.sendvoidRequest(sql);
    }

    // 获取所有书籍
    public List<Book> getAllBooks() throws SQLException {
        String sql = "SELECT * FROM books";
        TypeReference<List<Book>> typeRef = new TypeReference<List<Book>>() {};
        return server.getObjectResponse(sql, typeRef.getType());
    }

    // 搜索书籍
    public List<Book> searchBooks(String title, String author, String publisher) throws SQLException {
        List<String> conditions = new ArrayList<>();
        if (title != null && !title.trim().isEmpty()) {
            conditions.add(String.format("title LIKE '%%%s%%'", title));
        }
        if (author != null && !author.trim().isEmpty()) {
            conditions.add(String.format("author LIKE '%%%s%%'", author));
        }
        if (publisher != null && !publisher.trim().isEmpty()) {
            conditions.add(String.format("publisher LIKE '%%%s%%'", publisher));
        }

        String sql = "SELECT * FROM books WHERE " + 
            (conditions.isEmpty() ? "1=1" : String.join(" OR ", conditions));
        
        TypeReference<List<Book>> typeRef = new TypeReference<List<Book>>() {};
        return server.getObjectResponse(sql, typeRef.getType());
    }

    // 批量插入书籍
    public void batchInsertBooks(List<Book> books) throws SQLException {
        for (Book book : books) {
            addBook(book); // 使用现有的addBook方法
        }
    }
}