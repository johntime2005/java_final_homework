import java.sql.*;

public class userMegement {
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

        } catch (SQLException e) {
            // 静默处理异常，不输出错误信息
        }
    }

    public userMegement() {
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

    // TODO: 1.增加用户 2.删除用户
    public void create() {
        try {
            // 在SQL Server中，可以使用IF语句检测表是否存在
            String createTableSQL = "IF OBJECT_ID('dbo.[user]', 'U') IS NULL " + // 检测[dbo].[user]表是否存在
                    "BEGIN " +
                    "    CREATE TABLE [dbo].[user] (" +
                    "        [id] INT PRIMARY KEY," +
                    "        [name] VARCHAR(255) NOT NULL," +
                    "        [age] INT NOT NULL" +
                    "        [balance] INT NOT NULL" +
                    "    )" +
                    "END";// 用户具有id，姓名，年龄，余额四个属性

            stmt.executeUpdate(createTableSQL);
            System.out.println("表创建成功或已存在！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {// 按照id删除用户
        try {
            String deleteTableSQL = "DELETE FROM user WHERE id = " + id;
            stmt.executeUpdate(deleteTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //test
    // public static void main(String[] args) {
    // userMegement con = new userMegement();
    // System.out.println("连接成功");
    // con.create();
    // con.close();
    // System.out.println("关闭成功");
    // }

}