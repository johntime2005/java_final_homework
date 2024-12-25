public class LibraryAccount {
    private String accountId;   // 账户ID
    private String name;        // 姓名
    private String password;    // 密码
    private String studentId;   // 学号
    private String phone;       // 电话
    private char gender;        // 性别
    private String birthday;    // 生日
    private int balance;        // 余额
    private String major;       // 专业
    private int classroom;      // 班级

    // 构造方法
    public LibraryAccount(String accountId, String name, String password, String studentId, String phone, char gender, String birthday, int balance, String major, int classroom) {
        this.accountId = accountId;
        this.name = name;
        this.password = password;
        this.studentId = studentId;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
        this.balance = balance;
        this.major = major;
        this.classroom = classroom;
    }

    // Getter 方法
    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPhone() {
        return phone;
    }

    public char getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getBalance() {
        return balance;
    }

    public String getMajor() {
        return major;
    }

    public int getClassroom() {
        return classroom;
    }

    // toString 方法，便于打印账户信息
    @Override
    public String toString() {
        return "账户ID: " + accountId + "\n" +
                "姓名: " + name + "\n" +
                "学号: " + studentId + "\n" +
                "电话: " + phone + "\n" +
                "性别: " + (gender == 'M' ? "男" : "女") + "\n" +
                "生日: " + birthday + "\n" +
                "余额: " + balance + "\n" +
                "专业: " + major + "\n" +
                "班级: " + classroom;
    }
}
