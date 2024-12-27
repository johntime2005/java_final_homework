public class User {
    private int id;
    private String name;
    private int age;
    private int balance;
    private String username;
    private String password;
    private String userType; // "admin" 或 "user"

    public User(int id, String name, int age, int balance, String username, String password, String userType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.balance = balance;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(String name, int age, int balance, String username, String password, String userType) {
        this.name = name;
        this.age = age;
        this.balance = balance;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    @Override
    public String toString() {
        return id + " " + name + " " + age + " " + balance;
    }
}
