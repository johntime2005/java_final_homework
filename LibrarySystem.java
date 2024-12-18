import java.util.Map;
import java.util.HashMap;
public class LibrarySystem {
    private Map<String,BankAccount>accounts = new HashMap<>();
    private String counterUsername;
    private String counterPassword;
    public LibrarySystem()
    //柜台操作人员的用户名和密码可以在系统初始化时单独设定
    {
        this.counterUsername="Username";
        this.counterPassword="123456789";
    }
    /*
1.	开户，年满18岁的飞马人可以申请开设个人账号，飞马银行会存入2000飞马币作为星球福利赠送给每个新用户；
2.	销户，对于年满70岁的老人强制销户，或者因故离世的人员，经申请可以销户处理。
3.	查询余额，用户可以自由查询自己账户中的余额。
4.	取钱，用户可以支取账户上的余额，但支取后要保证账户余额必须大于等于0.
5.	存钱，用户可以往账号中存钱。
6.	转账，用户可以给另一个存在账号转钱，同样，要保证账户余额大于等于0的要求。
7.	修改账户信息，但账户id，身份id，账户余额等信息不允许修改。

     */
    //开户操作
    public void openAccount(BankAccount account){
        if(accounts.containsKey(account.getAccountId())){
            System.out.println("开户失败，账户已存在。");
        }else {
            accounts.put(account.getAccountId(),account);
            System.out.println("开户成功");
        }
    }
    //销户操作
    public void removeAccount(String accountid)
    {
        if(!accounts.containsKey(accountid)){
            System.out.println("销户失败，账户未找到");
        }else {
            accounts.remove(accountid);
            System.out.println("销户成功");`
        }
    }
    //
}
