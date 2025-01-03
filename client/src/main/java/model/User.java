package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("id")
    private int id;

    @JsonProperty("username")
    private String username; // 用户名（登录账号）

    @JsonProperty("password")
    private String password; // 密码

    @JsonProperty("user_type")
    private String userType; // 用户类型（admin/user）

    @JsonProperty("balance")
    private int balance; // 账户余额

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("phonenumber")
    private String phonenumber;

    @JsonProperty("schoolid")
    private String schoolid;

    @JsonProperty("birthdate")
    private String birthdate;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
