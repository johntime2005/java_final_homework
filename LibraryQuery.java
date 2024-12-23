import java.util.Scanner;

public class LibraryQuery {

    private LibrarySystem librarySystem;

    public LibraryQuery(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
    }

    // 查询账户信息
    public void queryAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请选择查询方式：");
        System.out.println("1. 根据账户ID查询");
        System.out.println("2. 根据学号查询");
        System.out.print("请输入选择(1/2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // 清空输入缓冲区

        if (choice == 1) {
            System.out.print("请输入账户ID：");
            String accountId = scanner.nextLine();
            LibraryAccount account = librarySystem.queryAccountById(accountId);
            if (account != null) {
                printAccountDetails(account);
            } else {
                System.out.println("未找到匹配的账户信息。");
            }
        } else if (choice == 2) {
            System.out.print("请输入学号：");
            String studentId = scanner.nextLine();
            LibraryAccount account = librarySystem.queryAccountByStudentId(studentId);
            if (account != null) {
                printAccountDetails(account);
            } else {
                System.out.println("未找到匹配的账户信息。");
            }
        } else {
            System.out.println("无效选择，请重新选择。");
        }
    }

    // 查询图书信息
    public void queryBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请选择查询方式：");
        System.out.println("1. 根据图书ID查询");
        System.out.println("2. 根据图书名称查询");
        System.out.print("请输入选择(1/2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // 清空输入缓冲区

        if (choice == 1) {
            System.out.print("请输入图书ID：");
            String bookId = scanner.nextLine();
            BookAccount book = librarySystem.queryBookById(bookId);
            if (book != null) {
                printBookDetails(book);
            } else {
                System.out.println("未找到匹配的图书信息。");
            }
        } else if (choice == 2) {
            System.out.print("请输入图书名称：");
            String bookName = scanner.nextLine();
            BookAccount book = librarySystem.queryBookByName(bookName);
            if (book != null) {
                printBookDetails(book);
            } else {
                System.out.println("未找到匹配的图书信息。");
            }
        } else {
            System.out.println("无效选择，请重新选择。");
        }
    }

    // 打印账户详情
    private void printAccountDetails(LibraryAccount account) {
        System.out.println("账户ID: " + account.getAccountId());
        System.out.println("姓名: " + account.getName());
        System.out.println("学号: " + account.getStudentId());
        System.out.println("电话: " + account.getPhoneNumber());
        System.out.println("性别: " + (account.getGender() == 'M' ? "男" : "女"));
        System.out.println("生日: " + account.getBirthday());
        System.out.println("余额: " + account.getBalance());
        System.out.println("专业: " + account.getMajor());
        System.out.println("班级: " + account.getClassroom());
    }

    // 打印图书详情
    private void printBookDetails(BookAccount book) {
        System.out.println("图书ID: " + book.getBookId());
        System.out.println("书名: " + book.getBookName());
        System.out.println("作者: " + book.getAuthor());
       // System.out.println("出版社: " + book.getPublisher());
      //  System.out.println("库存量: " + book.getStock());
        System.out.println("是否借出: " + (book.getIsBorrowed() ? "是" : "否"));
    }

    // 主菜单，提供查询入口
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n欢迎使用图书馆查询系统！");
            System.out.println("1. 查询账户信息");
            System.out.println("2. 查询图书信息");
            System.out.println("3. 退出");
            System.out.print("请输入选择(1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // 清空输入缓冲区

            if (choice == 1) {
                queryAccount();  // 查询账户
            } else if (choice == 2) {
                queryBook();  // 查询图书
            } else if (choice == 3) {
                System.out.println("感谢使用，程序退出！");
                break;
            } else {
                System.out.println("无效选择，请重新选择。");
            }
        }
    }
}