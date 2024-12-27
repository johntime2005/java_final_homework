import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlserver://116.205.125.206:1433;databaseName=library;trustServerCertificate=true;characterSet=UTF-8"; // 修改为你的数据库URL
    private static final String USER = "sa"; // 数据库用户名
    private static final String PASSWORD = "952891332wW!"; // 数据库密码

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        try {
            // 如果没有加载 JDBC 驱动程序，你可以显式加载它
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unable to connect to the database", e);
        }
    }
}
