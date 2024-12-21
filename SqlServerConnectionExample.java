import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlServerConnectionExample {

    // 数据库URL、用户名和密码
    private static final String DB_URL = "jdbc:sqlserver://<116.205.125.206>:1433;databaseName=<wangzhangzhuo>";
    private static final String USER = "<sa>";
    private static final String PASS = "<952891332wW!>";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册JDBC驱动程序（不再需要显式调用Class.forName()，因为从JDBC 4.0开始，驱动程序会自动注册）
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // 打开连接
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "SELECT id, name FROM YourTable";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.println(", Name: " + name);
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // 处理JDBC错误
            se.printStackTrace();
        } catch(Exception e) {
            // 处理Class.forName错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // 什么都不做
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}