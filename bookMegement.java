import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bookMegement implements bookMegementDao {
    private Connection conn;

    public bookMegement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS book (id INT PRIMARY KEY, title VARCHAR(255), author VARCHAR(255), quantity INT)";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    @Override
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO book (id, title, author, quantity) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, book.getId());
        stmt.setString(2, book.getTitle());
        stmt.setString(3, book.getAuthor());
        stmt.setInt(4, book.getQuantity());
        stmt.executeUpdate();
    }

    @Override
    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM book WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, author = ?, quantity = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setInt(3, book.getQuantity());
        stmt.setInt(4, book.getId());
        stmt.executeUpdate();
    }

    @Override
    public List<Book> queryBook(String title) throws SQLException {
        String sql = "SELECT * FROM book WHERE title = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        ResultSet rs = stmt.executeQuery();
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("quantity")));
        }
        return books;
    }
}
