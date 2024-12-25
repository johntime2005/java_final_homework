import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bookMegement implements bookMegementDao {
    private final String IP = "116.205.125.206";
    private final String PORT = "1433";
    private final String DB_NAME = "library";
    private final String user = "sa";
    private final String password = "952891332wW!";

    private Connection conn = null;
    private Statement stmt = null;

    private void initializeDatabase() {
        // 设置JVM字符编码
        System.setProperty("file.encoding", "UTF-8");
        try {
            // 首先连接到master数据库
            String masterUrl = "jdbc:sqlserver://" + IP + ":" + PORT + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "database=master;"
                    + "sendStringParametersAsUnicode=true;"
                    + "characterEncoding=UTF-8;";

            conn = DriverManager.getConnection(masterUrl, user, password);
            stmt = conn.createStatement();

            // 检查并创建数据库
            String createDbSQL = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = '" + DB_NAME + "') " +
                    "CREATE DATABASE " + DB_NAME + " COLLATE Chinese_PRC_CI_AS";
            stmt.executeUpdate(createDbSQL);

            // 关闭master连接
            stmt.close();
            conn.close();

            // 连接到新创建的数据库
            String dbUrl = "jdbc:sqlserver://" + IP + ":" + PORT + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "database=" + DB_NAME + ";"
                    + "sendStringParametersAsUnicode=true;"
                    + "characterEncoding=UTF-8;";
            conn = DriverManager.getConnection(dbUrl, user, password);
            stmt = conn.createStatement();

            // 创建book表
            String createTableSQL =
                    "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'book') " +
                            "CREATE TABLE book (" +
                            "    id INT IDENTITY(1,1) PRIMARY KEY," +
                            "    bookName NVARCHAR(100) COLLATE Chinese_PRC_CI_AS," +
                            "    author NVARCHAR(100) COLLATE Chinese_PRC_CI_AS," +
                            "    publisher NVARCHAR(100) COLLATE Chinese_PRC_CI_AS," +
                            "    publishDate NVARCHAR(20)," +
                            "    ISBN NVARCHAR(20)," +
                            "    quantity INT DEFAULT 1" +
                            ")";
            stmt.executeUpdate(createTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();  // 建议在开发时打印错误信息以便调试
        }
    }

    public bookMegement() {
        initializeDatabase();
    }

    public void close() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
