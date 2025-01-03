package utils;

import java.io.*;
import java.net.Socket;
import java.lang.reflect.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

public class ConnectToServer {
    private static final String SERVER_ADDRESS = "1.95.82.58";
    private static final int SERVER_PORT = 8888;

    public void sendvoidRequest(String sql) {
        Socket socket = null;
        PrintWriter out = null;

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("成功连接到服务器: " + SERVER_ADDRESS + ":" + SERVER_PORT);

            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(sql);
            System.out.println("成功发送SQL请求: " + sql);

        } catch (IOException e) {
            System.out.println("发送请求失败:");
            System.out.println("原因: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("关闭连接时出错: " + e.getMessage());
            }
        }
    }

    public String getStringResponse(String sql) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder response = new StringBuilder();

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(sql);
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            System.out.println("收到服务器响应: " + response.toString());

        } catch (IOException e) {
            System.out.println("获取响应失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("关闭连接时出错: " + e.getMessage());
            }
        }
        return response.toString();
    }

    public <T> T getObjectResponse(String sql, Class<T> type) {
        try {
            String response = getStringResponse(sql);
            if (response != null) {
                if (response.startsWith("ERROR:")) {
                    System.out.println("服务器返回错误: " + response);
                    return null;
                }
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                // 检查响应是否为数组
                if (response.startsWith("[")) {
                    JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, type);
                    return mapper.readValue(response, javaType);
                } else {
                    return mapper.readValue(response, type);
                }
            }
        } catch (Exception e) {
            System.out.println("对象转换失败: " + e.getMessage());
        }
        return null;
    }

    public <T> T getObjectResponse(String sql, Type type) {
        try {
            String response = getStringResponse(sql);
            if (response != null) {
                if (response.startsWith("ERROR:")) {
                    System.out.println("服务器返回错误: " + response);
                    return null;
                }
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                JavaType javaType = TypeFactory.defaultInstance().constructType(type);
                return mapper.readValue(response, javaType);
            }
        } catch (Exception e) {
            System.out.println("对象转换失败: " + e.getMessage());
        }
        return null;
    }

    public <T> T getObjectResponse(String sql, TypeReference<T> typeReference) {
        try {
            String response = getStringResponse(sql);
            if (response != null) {
                if (response.startsWith("ERROR:")) {
                    System.out.println("服务器返回错误: " + response);
                    return null;
                }
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return mapper.readValue(response, typeReference);
            }
        } catch (Exception e) {
            System.out.println("对象转换失败: " + e.getMessage());
        }
        return null;
    }
}
