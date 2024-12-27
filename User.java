public class User {
    private int id;
    private String name;
    private int age;
    private int balance;

    public User(int id, String name, int age, int balance) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.balance = balance;
    }

    public User(String name, int age, int balance) {
        this.name = name;
        this.age = age;
        this.balance = balance;
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

    @Override
    public String toString() {
        return id + " " + name + " " + age + " " + balance;
    }
}
