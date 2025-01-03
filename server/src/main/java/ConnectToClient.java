import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.sql.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

class RequestHandler extends Thread {
    private String sql;
    private DataBaseConnection dbConnection;
    private PrintWriter out;

    public RequestHandler(String sql, DataBaseConnection dbConnection, PrintWriter out) {
        this.sql = sql;
        this.dbConnection = dbConnection;
        this.out = out;
    }

    public void run() {
        try {
            System.out.println("客户端请求: " + sql);
            if (dbConnection == null) {
                throw new SQLException("数据库连接未初始化");
            }
            Statement stmt = dbConnection.getStatement();
            if (stmt == null) {
                throw new SQLException("数据库语句未初始化");
            }
            ObjectMapper mapper = new ObjectMapper();
            if (sql.trim().toLowerCase().startsWith("select")) {
                ResultSet rs = stmt.executeQuery(sql);
                ArrayNode arrayNode = mapper.createArrayNode();
                while (rs.next()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int cols = meta.getColumnCount();
                    ObjectNode objNode = mapper.createObjectNode();
                    for (int i = 1; i <= cols; i++) {
                        objNode.put(meta.getColumnName(i), rs.getString(i));
                    }
                    arrayNode.add(objNode);
                }
                out.println(mapper.writeValueAsString(arrayNode));
            } else {
                int rows = stmt.executeUpdate(sql);
                ObjectNode resultNode = mapper.createObjectNode();
                resultNode.put("影响行数", rows);
                out.println(mapper.writeValueAsString(resultNode));
            }
            out.flush();
            System.out.println("请求处理完成");
        } catch (Exception e) {
            System.out.println("SQL执行异常: " + e.getMessage());
            e.printStackTrace();
            out.println("ERROR: " + e.getMessage());
            out.flush();
        }
    }
}

class ConnectMaintainer extends Thread {
    private Socket socket;
    private DataBaseConnection dbConnection;

    public ConnectMaintainer(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("新客户端连接: " + socket.getInetAddress());
            dbConnection = new DataBaseConnection(); // 确保数据库连接初始化
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String sql = in.readLine();
            RequestHandler handler = new RequestHandler(sql, dbConnection, out);
            handler.start();
            handler.join(); // 等待 RequestHandler 完成
        } catch (Exception e) {
            System.out.println("客户端连接异常: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
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
                    new ConnectMaintainer(clientSocket).start();
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