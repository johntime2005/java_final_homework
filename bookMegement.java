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
    public Book getBookById(int bookId) throws SQLException {
        String query = "SELECT * FROM books WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, bookId);  // 使用 int 类型的 bookId
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getDate("publishDate").toLocalDate(),
                    rs.getString("isbn"),
                    rs.getInt("quantity")
            );
        } else {
            return null;  // 如果没有找到对应的书籍，返回 null
        }
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


    @Override
    public void updateBookQuantity(int bookId, int quantity) throws SQLException {
        // 1. 获取当前书籍的数量
        String query = "SELECT quantity FROM books WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bookId);  // 设置书籍ID

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // 获取当前书籍的数量
                    int currentQuantity = rs.getInt("quantity");

                    // 2. 计算新的数量（增减）
                    int newQuantity = currentQuantity + quantity;

                    // 3. 更新书籍数量
                    String updateQuery = "UPDATE books SET quantity = ? WHERE id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, newQuantity);  // 设置新的数量
                        updateStmt.setInt(2, bookId);       // 设置书籍ID
                        updateStmt.executeUpdate();         // 执行更新
                        System.out.println("Book quantity updated successfully for book ID: " + bookId);  // 记录成功日志
                    } catch (SQLException updateEx) {
                        // 捕获更新操作的异常，输出详细错误信息
                        System.err.println("Error updating book quantity for book ID: " + bookId);
                        System.err.println("Update query: " + updateQuery);
                        System.err.println("New quantity: " + newQuantity);
                        throw new SQLException("Error executing update query for book ID: " + bookId, updateEx);
                    }
                } else {
                    // 如果没有找到书籍，抛出异常
                    throw new SQLException("Book with ID " + bookId + " not found.");
                }
            } catch (SQLException queryEx) {
                // 捕获查询书籍数量的异常
                System.err.println("Error executing query to fetch quantity for book ID: " + bookId);
                System.err.println("Query: " + query);
                throw new SQLException("Error fetching current quantity for book ID: " + bookId, queryEx);
            }
        } catch (SQLException ex) {
            // 捕获整个方法的异常
            System.err.println("SQL error while updating book quantity.");
            throw new SQLException("SQL error while updating book quantity for book ID: " + bookId, ex);
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
