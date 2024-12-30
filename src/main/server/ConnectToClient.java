import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.sql.*;

class connect implements Serializable {
    private static final long serialVersionUID = 1L;
    private Socket socket;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    
    public connect(Socket socket) {
        this.socket = socket;
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = 
                "jdbc:sqlserver://localhost:1433;"
                + "databaseName=library;"
                + "encrypt=true;"
                + "trustServerCertificate=true";
            
            conn = DriverManager.getConnection(connectionUrl, "sa", "952891332wW!");
            stmt = conn.createStatement();
            System.out.println("数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 添加getter方法
    public ResultSet getResultSet() throws SQLException {
        return stmt.executeQuery("SELECT * FROM book");
    }
    
    public void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try {
            System.out.println("新客户端连接: " + socket.getInetAddress());
            
            // 创建connect对象
            connect conn = new connect(socket);
            
            // 创建对象输出流
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            
            // 将connect对象发送给客户端
            out.writeObject(conn);
            out.flush();
            
            System.out.println("已向客户端发送connect对象");
            
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
                    new ClientHandler(clientSocket).start();
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