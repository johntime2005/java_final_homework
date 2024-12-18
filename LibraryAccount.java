/*
银行内用户Id：10位数字组成
用户名：填姓名（10个汉字长度）
密码：不少于4位
身份ID：填学号（12位数字）
手机号：正常手机号
性别：用一位字符（M:男，F:女）
出生日期：YYYY-MM-DD
账户余额：双精度范围即可
 */
public class LibraryAccount {
    private String accountid;
    private String name;
    private String password;
    private String identifyid;
    private String phonenumber;
    private char gender;
    private String birthday;
    private double balance;
    public LibraryAccount(String accountid,String name,String password,String identifyid,
                       String phonenumber,char gender,String birthday)
    {
        this.accountid=accountid;
        this.name=name;
        this.password=password;
        this.identifyid=identifyid;
        this.phonenumber = phonenumber;
        this.gender=gender;
        this.birthday=birthday;
        this.balance=2000;
    }
    //getter
    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentityId() {
        return identityId;
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

    public double getBalance() {
        return balance;
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

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
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

    public void setBalance(double balance) {
        this.balance = balance;
    }
    @Override
    public String toString()
    {
        return accountid+" "+name+" "+password+" "+identifyid+" "+phonenumber+" "+gender+" "+birthday+" "+balance+" ";
    }
}
