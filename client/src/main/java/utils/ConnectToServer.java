package utils;

import java.io.*;
import java.net.Socket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectToServer {
    private static final Logger logger = LoggerFactory.getLogger(ConnectToServer.class);
    private static final String SERVER_ADDRESS = "1.95.82.58";
    private static final int SERVER_PORT = 8888;

    public ConnectToServer() {
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
    }

    public void sendvoidRequest(String sql) {
        Socket socket = null;
        PrintWriter out = null;

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            logger.info("成功连接到服务器: {}:{}", SERVER_ADDRESS, SERVER_PORT);

            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            out.println(sql);
            logger.info("成功发送SQL请求: {}", sql);

        } catch (IOException e) {
            logger.error("发送请求失败: {}", e.getMessage());
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
                logger.error("关闭连接时出错: {}", e.getMessage());
            }
        }
    }

    private String getStringResponse(String sql) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder response = new StringBuilder();

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            out.println(sql);
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            //System.out.println("收到服务器响应: " + response.toString());

        } catch (IOException e) {
            logger.error("获取响应失败: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                //System.out.println("关闭连接时出错: " + e.getMessage());
            }
        }
        return response.toString();
    }

    public <T> T getObjectResponse(String sql, Class<T> type) {
        String response = getStringResponse(sql);
        if (response != null) {
            if (response.startsWith("ERROR:")) {
                logger.error("服务器返回错误: {}", response);
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                //System.out.println("反序列化前的响应: " + response);
                List<Map<String, String>> list = mapper.readValue(response, new TypeReference<List<Map<String, String>>>(){});
                return list.isEmpty() ? null : mapper.convertValue(list.get(0), type);
            } catch (Exception e) {
                logger.error("对象转换失败: {}", e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> T getObjectResponse(String sql, TypeReference<T> typeReference) {
        String response = getStringResponse(sql);
        if (response != null) {
            if (response.startsWith("ERROR:")) {
                logger.error("服务器返回错误: {}", response);
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                return mapper.readValue(response, typeReference);
            } catch (Exception e) {
                logger.error("对象转换失败: {}", e.getMessage());
            }
        }
        return null;
    }

    public <T> T getObjectResponse(String sql, java.lang.reflect.Type type) {
        String response = getStringResponse(sql);
        if (response != null) {
            if (response.startsWith("ERROR:")) {
                logger.error("服务器返回错误: {}", response);
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                JavaType javaType = mapper.getTypeFactory().constructType(type);
                return mapper.readValue(response, javaType);
            } catch (Exception e) {
                logger.error("对象转换失败: {}", e.getMessage());
            }
        }
        return null;
    }
}
