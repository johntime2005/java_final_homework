import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bookMegement implements bookMegementDao {
    private final Connection connection;

    public bookMegement(Connection connection) {
        this.connection = connection;
    }

    // 添加书籍
    @Override
    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO books (title, author, publisher, publishDate, isbn, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setDate(4, Date.valueOf(book.getPublishDate())); // Convert LocalDate to SQL Date
            stmt.setString(5, book.getIsbn());
            stmt.setInt(6, book.getQuantity());

            stmt.executeUpdate();
        }
    }

    // 根据书名查询书籍
    @Override
    public List<Book> getBooksByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE title LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getDate("publishDate").toLocalDate(), // Convert SQL Date to LocalDate
                        rs.getString("isbn"),
                        rs.getInt("quantity")
                );
                books.add(book);
            }
        }
        return books;
    }

    // 根据ID查询书籍
    @Override
    public Book getBookById(int id) throws SQLException {
        Book book = null;
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getDate("publishDate").toLocalDate(), // Convert SQL Date to LocalDate
                        rs.getString("isbn"),
                        rs.getInt("quantity")
                );
            }
        }
        return book;
    }

    // 更新书籍信息
    @Override
    public void updateBook(Book book) throws SQLException {
        String query = "UPDATE books SET title = ?, author = ?, publisher = ?, publishDate = ?, isbn = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublisher());
            stmt.setDate(4, Date.valueOf(book.getPublishDate()));
            stmt.setString(5, book.getIsbn());
            stmt.setInt(6, book.getQuantity());
            stmt.setInt(7, book.getId());

            stmt.executeUpdate();
        }
    }

    // 删除书籍
    @Override
    public void deleteBook(int id) throws SQLException {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // 获取所有书籍
    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getDate("publishDate").toLocalDate(),
                        rs.getString("isbn"),
                        rs.getInt("quantity")
                );
                books.add(book);
            }
        }
        return books;
    }
}
