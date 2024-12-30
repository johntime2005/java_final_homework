package model;

public class User {
    private int id;
    private String username; // 用户名（登录账号）
    private String password; // 密码
    private String userType; // 用户类型（admin/user）
    private int balance; // 账户余额
    // private int age; // 用户年龄
    // private String schoolid;
    // private String gender;
    // private String phonenumber;
    // private String birthdate;

    // 主构造函数，包含所有字段
    public User() {
    }

    public User(int id, int balance, String username, String password, String userType) {
        this.id = id;
        this.balance = balance;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    // 用于创建新用户的构造函数，只包含基本字段
    public User(String username, String password, String userType, int balance) {
        this(0, balance, username, password, userType);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
