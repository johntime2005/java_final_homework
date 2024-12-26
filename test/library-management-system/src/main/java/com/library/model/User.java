package test.library

public class User {
    private int id; // 重命名accountId为id
    private String name;
    private int age; // 添加age属性
    private int balance;

    public User(int id, String name, int age, int balance) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.balance = balance;
    }

    // Getters
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

    // Setters
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

    @Override
    public String toString() {
        return id + " " + name + " " + age + " " + balance;
    }
}
