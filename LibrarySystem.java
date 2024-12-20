/*
一个写用户功能的类
1：开户，申请开设个人账号；
2：销户，对于已毕业学生强制销户，或者因故离校的人员，经申请可以销户处理。
3：修改简单信息，但注意只能修改id，性别，生日，手机号，专业，班级等。
4：借书:用户只能根据编号借书,先在后台查询isBorrowed，如为未借出，则借出，同时余额减少，同时isBorrowed设为true，同时借阅人设为当前用户。
5：还书
*/
import java.util.Map;
import java.util.HashMap;
public class LibrarySystem {
    private Map<String, LibraryAccount> accounts = new HashMap<>();
    private Map<String, BookAccount> bookAccounts = new HashMap<>();

    private String counterUsername;
    private String counterPassword;

    public LibrarySystem()
    //图书管理人员的用户名和密码可以在系统初始化时单独设定
    {
        this.counterUsername = "Username";
        this.counterPassword = "123456789";
    }

    //开户操作
    public void openAccount(LibraryAccount account) {
        if (accounts.containsKey(account.getAccountId())) {
            System.out.println("开户失败，账户已存在。");
        } else {
            accounts.put(account.getAccountId(), account);
            System.out.println("开户成功");
        }
    }

    //销户操作
    public void removeAccount(String accountid) {
        if (!accounts.containsKey(accountid)) {
            System.out.println("销户失败，账户未找到");
        } else {
            accounts.remove(accountid);
            System.out.println("销户成功");
        }
    }

    //修改信息
    public void modifyInfo(String studentId, String accountid, char gender, String birthday, String phonenumber, String major, int class) {
        if (!accounts.containsKey(studentId)) {
            System.out.println("修改失败，账户未找到");
        } else {
            System.out.println("提示：只能修改id，性别，生日，手机号，专业，班级");
            accounts.put(studentId).setAccountId(accountid);
            accounts.put(studentId).setGender(gender);
            accounts.put(studentId).setBirthday(birthday);
            accounts.put(studentId).setPhoneNumber(phonenumber);
            accounts.put(studentId).setMajor(major);
            accounts.put(studentId).setClass( class);
            System.out.println("修改成功");
        }
    }

    //取书
    public void borrowBook(String bookId, String studentId) {
        {
            if (!bookAccounts.containsKey(bookId) || !bookAccounts.get(bookId).getisBorrowed()) {
                System.out.println("取书失败，图书未找到");
            } else {
                if (accounts.containsKey(studentId).getBalance() > 0) {
                    bookAccounts.put(bookId).setIsBorrowed(true);
                    accounts.put(studentId).setBalance(accounts.put(studentId).getBalance() - 1);
                    bookAccounts.put(bookId).setBorrower(accounts.put(studentId).getstudentId());
                }
            }
        }
    }
}
    //还书

