import java.util.Map;
import java.util.HashMap;

public class LibrarySystem {
    private final Map<String, LibraryAccount> accounts = new HashMap<>();  // 存储账户信息
    private final Map<String, BookAccount> bookAccounts = new HashMap<>();  // 存储图书信息

    private final String counterUsername = "Username";  // 管理员用户名
    private final String counterPassword = "123456789";  // 管理员密码

    public LibrarySystem() {
        // 图书管理人员的用户名和密码可以在系统初始化时单独设定
    }

    // 开户操作
    public void openAccount(LibraryAccount account) {
        if (accounts.containsKey(account.getAccountId())) {
            System.out.println("开户失败，账户已存在。");
        } else {
            accounts.put(account.getAccountId(), account);
            System.out.println("开户成功");
        }
    }

    // 销户操作
    public void removeAccount(String accountId) {
        if (!accounts.containsKey(accountId)) {
            System.out.println("销户失败，账户未找到");
        } else {
            accounts.remove(accountId);
            System.out.println("销户成功");
        }
    }

    // 修改信息操作
    public void modifyInfo(String studentId, String accountId, char gender, String birthday, String phoneNumber, String major, int classroom) {
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

    // 借书操作
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

    // 还书操作
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

    // 查询账户信息（通过账户ID）
    public LibraryAccount queryAccountById(String accountId) {
        return accounts.get(accountId);  // 返回账户ID对应的账户信息
    }

    // 查询账户信息（通过学号）
    public LibraryAccount queryAccountByStudentId(String studentId) {
        for (LibraryAccount account : accounts.values()) {
            if (account.getStudentId().equals(studentId)) {
                return account;  // 找到匹配的账户
            }
        }
        return null;  // 如果没有找到匹配的账户，返回 null
    }

    // 查询图书信息（通过图书ID）
    public BookAccount queryBookById(String bookId) {
        return bookAccounts.get(bookId);  // 返回图书ID对应的图书信息
    }

    // 查询图书信息（通过书名）
    public BookAccount queryBookByName(String bookName) {
        for (BookAccount book : bookAccounts.values()) {
            if (book.getBookName().equals(bookName)) {
                return book;  // 找到匹配的图书
            }
        }
        return null;  // 如果没有找到匹配的图书，返回 null
    }

    // 添加图书信息（用于初始化或增加图书）
    public void addBook(BookAccount book) {
        if (bookAccounts.containsKey(book.getBookId())) {
            System.out.println("图书已存在，无法添加。");
        } else {
            bookAccounts.put(book.getBookId(), book);
            System.out.println("图书添加成功");
        }
    }
}