import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.sql.*;

class connect extends Thread {
    private Socket socket;
    
    public connect(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try {
            // 处理与客户端的通信逻辑
            System.out.println("新客户端连接: " + socket.getInetAddress());
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                
                // SQLServer连接字符串
                String connectionUrl = 
                    "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=library;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true";
                
                conn = DriverManager.getConnection(connectionUrl, "sa", "952891332wW!");
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM book");
                System.out.println("数据库连接成功");
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
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
            
            
        } catch (Exception e) {
            System.out.println("客户端连接异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ConnectToClient {
    private static final int PORT = 8888;
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("服务器启动，监听端口: " + PORT);
            
            while (true) {
                try {
                    // 等待并接受新的客户端连接
                    Socket clientSocket = serverSocket.accept();
                    // 为每个客户端创建新的处理线程
                    new connect(clientSocket).start();
                } catch (IOException e) {
                    System.out.println("接受客户端连接失败: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("服务器启动失败: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}