import java.sql.*;

public class userMegement {
    private final String IP = "116.205.125.206";
    private final String PORT = "1433";
    private final String DB_NAME = "library";
    private final String USER = "sa";
    private final String PASSWORD = "952891332wW!";

    private Connection conn = null;
    private Statement stmt = null;

    // 初始化数据库连接
    private void initializeDatabase() {
        try {
            // 连接到 master 数据库
            String masterUrl = "jdbc:sqlserver://" + IP + ":" + PORT + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "database=master;";

            conn = DriverManager.getConnection(masterUrl, USER, PASSWORD);
            stmt = conn.createStatement();

            // 检查并创建数据库
            String createDbSQL = "IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = '" + DB_NAME + "') "
                    + "CREATE DATABASE " + DB_NAME;
            stmt.executeUpdate(createDbSQL);

            // 关闭 master 数据库连接
            stmt.close();
            conn.close();

            // 连接到新创建的数据库
            String dbUrl = "jdbc:sqlserver://" + IP + ":" + PORT + ";"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "database=" + DB_NAME + ";";
            conn = DriverManager.getConnection(dbUrl, USER, PASSWORD);
            stmt = conn.createStatement();

            // 检查并创建用户表
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace(); // 处理连接和查询时的异常
        }
    }

    // 构造函数，初始化数据库
    public userMegement() {
        initializeDatabase();
    }

    // 关闭数据库连接
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

    // 检查表是否存在，如果不存在则创建表
    private void createTableIfNotExists() {
        String createTableSQL = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'library_user') "
                + "BEGIN "
                + "CREATE TABLE library_user ("
                + "id INT IDENTITY(1,1) PRIMARY KEY, "
                + "name VARCHAR(255) NOT NULL, "
                + "age INT NOT NULL, "
                + "balance INT NOT NULL); "
                + "END";
        try {
            stmt.executeUpdate(createTableSQL);
            System.out.println("表创建成功或已存在！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加用户
    public void addUser(String name, int age, int balance) {
        try {
            String insertSQL = "INSERT INTO library_user (name, age, balance) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setInt(3, balance);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("用户添加成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除用户
    public void deleteUser(int id) {
        try {
            String deleteSQL = "DELETE FROM library_user WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteSQL);
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            if (rowsAffected > 0) {
                System.out.println("用户删除成功！");
            } else {
                System.out.println("用户ID不存在！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询所有用户
    public void listUsers() {
        try {
            String selectSQL = "SELECT * FROM library_user";
            ResultSet rs = stmt.executeQuery(selectSQL);

            System.out.println("用户信息：");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("姓名: " + rs.getString("name"));
                System.out.println("年龄: " + rs.getInt("age"));
                System.out.println("余额: " + rs.getInt("balance"));
                System.out.println("--------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询用户（根据ID）
    public void getUserById(int id) {
        try {
            String selectSQL = "SELECT * FROM library_user WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(selectSQL);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("姓名: " + rs.getString("name"));
                System.out.println("年龄: " + rs.getInt("age"));
                System.out.println("余额: " + rs.getInt("balance"));
            } else {
                System.out.println("没有找到指定ID的用户！");
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 主函数，测试方法
    public static void main(String[] args) {
        userMegement um = new userMegement();
        System.out.println("数据库连接成功");

        // 添加用户
        um.addUser("张三", 25, 500);
        um.addUser("李四", 28, 1000);

        // 列出所有用户
        um.listUsers();

        // 根据ID查询用户
        um.getUserById(1);

        // 删除用户
        um.deleteUser(2);

        // 列出所有用户，确认删除
        um.listUsers();

        um.close();
        System.out.println("数据库连接已关闭");
    }
}
