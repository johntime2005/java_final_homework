import java.util.HashMap;

public class LibrarySystem {
    private HashMap<String, LibraryAccount> accountMap; // 用于存储账户信息

    public LibrarySystem() {
        accountMap = new HashMap<>();
    }

    // 开户操作
    public void openAccount(LibraryAccount account) {
        accountMap.put(account.getAccountId(), account); // 将账户ID作为键，账户信息作为值
    }

    // 查询账户信息
    public LibraryAccount queryAccountById(String accountId) {
        return accountMap.get(accountId);  // 返回对应账户ID的账户信息
    }

    // 修改账户信息
    public void modifyAccount(String accountId, String name, String studentId, String phone, String birthday, String major, int classroom) {
        LibraryAccount account = accountMap.get(accountId);
        if (account != null) {
            account = new LibraryAccount(accountId, name, account.getPassword(), studentId, phone, account.getGender(), birthday, account.getBalance(), major, classroom);
            accountMap.put(accountId, account);  // 更新账户信息
        }
    }

    // 销户操作
    public void removeAccount(String studentId) {
        accountMap.entrySet().removeIf(entry -> entry.getValue().getStudentId().equals(studentId));
    }

    // 借书操作
    public void borrowBook(String accountId, String bookId) {
        // 假设借书逻辑，借书操作具体实现
    }

    // 还书操作
    public void returnBook(String accountId, String bookId) {
        // 假设还书逻辑，还书操作具体实现
    }
}
