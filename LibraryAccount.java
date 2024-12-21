public class LibraryAccount {
    private String accountId;
    private String name;
    private String password; // 密码
    private String studentId; // 学号
    private String phoneNumber; // 电话号码
    private char gender;
    private String birthday;
    private int balance;
    private String major;
    private int classroom;

    public LibraryAccount(String accountId, String name, String password, String studentId,
            String phoneNumber, char gender, String birthday, int balance, String major, int classroom) {
        this.accountId = accountId;
        this.name = name;
        this.password = password;
        this.studentId = studentId;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
        this.balance = balance;
        this.major = major;
        this.classroom = classroom;
    }

    // Getters
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

    public String getPhoneNumber() {
        return phoneNumber;
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

    // Setters
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return accountId + " " + name + " " + password + " " + studentId + " " + phoneNumber + " " + gender + " "
                + birthday + " " + major + " " + classroom;
    }
}
