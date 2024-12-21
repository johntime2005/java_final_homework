/*
一个写用户功能的类
1：开户，申请开设个人账号；
2：销户，对于已毕业学生强制销户，或者因故离校的人员，经申请可以销户处理。
3：修改简单信息，但注意只能修改id，性别，生日，手机号，专业，班级等。
4：借书:用户只能根据编号借书,先在后台查询isBorrowed，如为未借出，则借出，同时余额减少，同时isBorrowed设为true，同时借阅人设为当前用户。
5：还书：用户只能根据编号还书，还书后isBorrowed设为false，借阅人设为null，同时余额增加。
*/
import java.util.Map;
import java.util.HashMap;
public class LibrarySystem {
    private final Map<String, LibraryAccount> accounts = new HashMap<>();
    private final Map<String, BookAccount> bookAccounts = new HashMap<>();

    private final String counterUsername= "Username";
    private final String counterPassword="123456789";

    public LibrarySystem()
    //图书管理人员的用户名和密码可以在系统初始化时单独设定
    {

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
    public void removeAccount(String accountId) {
        if (!accounts.containsKey(accountId)) {
            System.out.println("销户失败，账户未找到");
        } else {
            accounts.remove(accountId);
            System.out.println("销户成功");
        }
    }

    //修改信息
    public void modifyInfo(String studentId, String accountId, char gender, String birthday,
                           String phoneNumber, String major, int classroom) {
        if (!accounts.containsKey(studentId)) {
            System.out.println("修改失败，账户未找到");
        } else {
            System.out.println("提示：只能修改id，性别，生日，手机号，专业，班级");
            LibraryAccount account = accounts.get(studentId);
            account.setAccountId(accountId);
            account.setGender(gender);
            account.setBirthday(birthday);
            account.setPhoneNumber(phoneNumber);
            account.setMajor(major);
            account.setClassroom(classroom);
            System.out.println("修改成功");
        }
    }

    //借书操作
    public void borrowBook(String bookId, String studentId) {
        if (!accounts.containsKey(studentId)) {
            System.out.println("借书失败，用户不存在");
            return;
        }
        
        if (!bookAccounts.containsKey(bookId)) {
            System.out.println("借书失败，图书不存在");
            return;
        }
        
        BookAccount book = bookAccounts.get(bookId);
        LibraryAccount account = accounts.get(studentId);
        
        if (book.getIsBorrowed()) {
            System.out.println("借书失败，图书已被借出");
            return;
        }
        
        if (account.getBalance() <= 0) {
            System.out.println("借书失败，账户余额不足");
            return;
        }
        
        // 更新图书状态
        book.setIsBorrowed(true);
        book.setBorrower(studentId);
        // 更新用户余额
        account.setBalance(account.getBalance() - 1);
        System.out.println("借书成功");
    }

    //还书操作
    public void returnBook(String bookId, String studentId) {
        if (!accounts.containsKey(studentId)) {
            System.out.println("还书失败，用户不存在");
            return;
        }
        
        if (!bookAccounts.containsKey(bookId)) {
            System.out.println("还书失败，图书不存在");
            return;
        }
        
        BookAccount book = bookAccounts.get(bookId);
        LibraryAccount account = accounts.get(studentId);
        
        if (!book.getIsBorrowed()) {
            System.out.println("还书失败，图书未被借出");
            return;
        }
        
        if (!studentId.equals(book.getBorrower())) {
            System.out.println("还书失败，这本书不是你借的");
            return;
        }
        
        // 更新图书状态
        book.setIsBorrowed(false);
        book.setBorrower(null);
        // 更新用户余额
        account.setBalance(account.getBalance() + 1);
        System.out.println("还书成功");
    }
}

