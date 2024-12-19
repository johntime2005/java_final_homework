/*
一个写用户功能的类
1：开户，申请开设个人账号；
2：销户，对于已毕业学生强制销户，或者因故离校的人员，经申请可以销户处理。
3：修改信息，但注意只能修改id，姓名，密码，性别，生日，手机号，专业，班级等。
4：借书
5：还书
*/
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
            System.out.println("销户成功");
        }
    }
    //查询余额
    public void queryBalance(String accountid){
        if(!accounts.containsKey(accountid)){
            System.out.println("查询失败，账户未找到");
        }else{
            System.out.println("账户余额为"+accounts.get(accountid).getBalance());
        }
    }
    //修改信息
    public void modifyInfo(String accountid,String name,String password,char gender,String birthday,String phoneNumber,String major,int class){
        if(!accounts.containsKey(accountid)){
            System.out.println("修改失败，账户未找到");
        }else{
            accounts.get(accountid).setName(name);
            accounts.get(accountid).setPassword(password);
            accounts.get(accountid).setGender(gender);
            accounts.get(accountid).setBirthday(birthday);
        }
    }
    //取书

}
