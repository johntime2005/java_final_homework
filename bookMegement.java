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
        try {
            // 首先连接到master数据库
            String masterUrl = "jdbc:sqlserver://" + IP + ":" + PORT + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "database=master;";

            conn = DriverManager.getConnection(masterUrl, user, password);
            stmt = conn.createStatement();

            // 检查并创建数据库
            String createDbSQL = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = '" + DB_NAME + "') " +
                    "CREATE DATABASE " + DB_NAME;
            stmt.executeUpdate(createDbSQL);

            // 关闭master连接
            stmt.close();
            conn.close();

            // 连接到新创建的数据库
            String dbUrl = "jdbc:sqlserver://" + IP + ":" + PORT + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "database=" + DB_NAME + ";";
            conn = DriverManager.getConnection(dbUrl, user, password);
            stmt = conn.createStatement();

            // 创建book表
            String createTableSQL =
                    "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'book') " +
                            "CREATE TABLE book (" +
                            "    id INT IDENTITY(1,1) PRIMARY KEY," +
                            "    bookName NVARCHAR(100)," +
                            "    author NVARCHAR(100)," +
                            "    publisher NVARCHAR(100)," +
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
    public void addBook(String bookName, String author, String publisher, String publishDate, String ISBN) throws SQLException {
        try {
            // 先查询是否存在相同的图书
            String checkSQL = "SELECT quantity FROM book WHERE bookName = ? AND author = ? AND publisher = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
            checkStmt.setString(1, bookName);
            checkStmt.setString(2, author);
            checkStmt.setString(3, publisher);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // 如果存在，更新数量
                String updateSQL = "UPDATE book SET quantity = quantity + 1 WHERE bookName = ? AND author = ? AND publisher = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
                updateStmt.setString(1, bookName);
                updateStmt.setString(2, author);
                updateStmt.setString(3, publisher);
                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                // 如果不存在，添加新记录
                String insertSQL = "INSERT INTO book (bookName, author, publisher, publishDate, ISBN, quantity) VALUES (?, ?, ?, ?, ?, 1)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSQL);
                insertStmt.setString(1, bookName);
                insertStmt.setString(2, author);
                insertStmt.setString(3, publisher);
                insertStmt.setString(4, publishDate);
                insertStmt.setString(5, ISBN);
                insertStmt.executeUpdate();
                insertStmt.close();
            }
            checkStmt.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteBook(String bookName) throws SQLException {
        try {
            String sql = "DELETE FROM book WHERE bookName = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookName);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void updateBook(String bookName, String author, String publisher, String publishDate, String ISBN) throws SQLException {
        try {
            String sql = "UPDATE book SET author = ?, publisher = ?, publishDate = ?, ISBN = ? WHERE bookName = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, author);
            pstmt.setString(2, publisher);
            pstmt.setString(3, publishDate);
            pstmt.setString(4, ISBN);
            pstmt.setString(5, bookName);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Book> queryBook(String bookName) throws SQLException {
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM book WHERE bookName = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("bookName"), rs.getString("author"), rs.getString("publisher"), rs.getString("publishDate"), rs.getString("ISBN"), rs.getInt("quantity"));
                books.add(book);
            }
            pstmt.close();
        } catch (SQLException e) {
            throw e;
        }
        return books;
    }

    @Override
    public List<Book> queryAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM book";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("bookName"), rs.getString("author"), rs.getString("publisher"), rs.getString("publishDate"), rs.getString("ISBN"), rs.getInt("quantity"));
                books.add(book);
            }
            pstmt.close();
        } catch (SQLException e) {
            throw e;
        }
        return books;
    }

    @Override
    public InventoryStats getInventoryStats() throws SQLException {
        InventoryStats stats = new InventoryStats();
        try {
            String sql = "SELECT COUNT(DISTINCT bookName) as uniqueBooks, " +
                    "SUM(quantity) as totalBooks " +
                    "FROM book";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                stats.setUniqueBooks(rs.getInt("uniqueBooks"));
                stats.setTotalBooks(rs.getInt("totalBooks"));
            }
            pstmt.close();
        } catch (SQLException e) {
            throw e;
        }
        return stats;
    }

    public static void main(String[] args) {
        bookMegement con = new bookMegement();
        System.out.println("连接成功");

        // 测试添加重复图书
        try {
            con.addBook("Java编程", "作者A", "出版社X", "2023-01-01", "ISBN001");
            con.addBook("Java编程", "作者A", "出版社X", "2023-01-01", "ISBN001");
            con.addBook("C++", "作者B", "出版社Y", "2023-01-01", "ISBN002");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 显示所有图书信息
        try {
            List<Book> books = con.queryAllBooks();
            for (Book book : books) {
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 显示库存统计
        try {
            InventoryStats stats = con.getInventoryStats();
            System.out.println(stats);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        con.close();
        System.out.println("关闭成功");
    }
}
