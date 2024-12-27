package model;

public class User {
    private int id;
    private String username;    // 用户名（登录账号）
    private String password;    // 密码
    private String userType;    // 用户类型（admin/user）
    private int balance;       // 账户余额
    private int age;           // 用户年龄

    public User(int id, int age, int balance, String username, String password, String userType) {
        this.id = id;
        this.age = age;
        this.balance = balance;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    // 用于创建新用户的构造函数
    public User(String username, String password, String userType, int balance) {
        this(0, 0, balance, username, password, userType);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return String.format("User{id=%d, age=%d, username='%s', userType='%s', balance=%d}",
            id, age, username, userType, balance);
    }
}
