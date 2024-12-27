package utils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseInitializer {
    
    public static void initializeDatabase(Connection connection) throws SQLException {
        createDatabaseIfNotExists(connection);
        createTables(connection);
        createAdminUser(connection);
    }

    private static void createDatabaseIfNotExists(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // 检查数据库是否存在
            ResultSet rs = stmt.executeQuery(
                "SELECT database_id FROM sys.databases WHERE name = 'library'"
            );
            
            if (!rs.next()) {
                // 创建数据库
                stmt.execute("CREATE DATABASE library");
                System.out.println("数据库 'library' 创建成功");
            }

            // 切换到新创建的数据库
            stmt.execute("USE library");
        }
    }
    
    private static void createTables(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // 创建用户表
            stmt.execute("""
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[library_user]') AND type in (N'U'))
                BEGIN
                CREATE TABLE library_user (
                    id INT IDENTITY(1,1) PRIMARY KEY,
                    username NVARCHAR(50) UNIQUE NOT NULL,
                    password NVARCHAR(50) NOT NULL,
                    user_type NVARCHAR(10) NOT NULL,
                    balance INT DEFAULT 0
                )
                END
            """);

            // 创建图书表
            stmt.execute("""
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[books]') AND type in (N'U'))
                BEGIN
                CREATE TABLE books (
                    id INT IDENTITY(1,1) PRIMARY KEY,
                    title NVARCHAR(200) NOT NULL,
                    author NVARCHAR(100),
                    publisher NVARCHAR(100),
                    publishDate DATE,
                    isbn NVARCHAR(20) UNIQUE,
                    quantity INT DEFAULT 0
                )
                END
            """);

            // 创建借阅记录表
            stmt.execute("""
                IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[user_book]') AND type in (N'U'))
                BEGIN
                CREATE TABLE user_book (
                    user_id INT,
                    book_id INT,
                    borrow_date DATE,
                    FOREIGN KEY (user_id) REFERENCES library_user(id),
                    FOREIGN KEY (book_id) REFERENCES books(id),
                    PRIMARY KEY (user_id, book_id)
                )
                END
            """);
        }
    }
    
    private static void createAdminUser(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // 检查是否已存在管理员账户
            var rs = stmt.executeQuery(
                "SELECT COUNT(*) FROM library_user WHERE user_type = 'admin'"
            );
            if (rs.next() && rs.getInt(1) == 0) {
                // 创建默认管理员账户，使用新的表结构
                stmt.execute("""
                    INSERT INTO library_user (username, password, user_type, balance)
                    VALUES ('admin', 'admin123', 'admin', 0)
                """);
                System.out.println("创建默认管理员账户成功");
            }
        }
    }
}
