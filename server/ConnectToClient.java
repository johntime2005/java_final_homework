import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

private class connect extends Thread {
    private Socket socket;
    
    public connect(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try {
            // 处理与客户端的通信逻辑
            System.out.println("新客户端连接: " + socket.getInetAddress());
            // TODO: 在这里添加具体的客户端通信处理代码
            
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