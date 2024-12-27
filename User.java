public class User {
    private int id;
    private String username;    // 用户名（登录账号）
    private String password;    // 密码
    private String userType;    // 用户类型（admin/user）
    private int balance;       // 账户余额

    public User(int id, String username, String password, String userType, int balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.balance = balance;
    }

    // 用于创建新用户的构造函数
    public User(String username, String password, String userType, int balance) {
        this(0, username, password, userType, balance);
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

    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', userType='%s', balance=%d}",
            id, username, userType, balance);
    }
}
