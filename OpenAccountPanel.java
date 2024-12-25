import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OpenAccountPanel extends JPanel {
    private JTextField accountIdField, nameField, passwordField, studentIdField, phoneField, birthdayField, majorField, classroomField;
    private JComboBox<String> genderComboBox;
    private JTextField balanceField;
    private JButton openAccountButton;
    private LibrarySystem librarySystem;

    public OpenAccountPanel(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
        setLayout(new GridLayout(11, 2));

        // 账户ID
        add(new JLabel("账户ID:"));
        accountIdField = new JTextField();
        add(accountIdField);

        // 姓名
        add(new JLabel("姓名:"));
        nameField = new JTextField();
        add(nameField);

        // 密码
        add(new JLabel("密码:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // 学号
        add(new JLabel("学号:"));
        studentIdField = new JTextField();
        add(studentIdField);

        // 电话
        add(new JLabel("电话:"));
        phoneField = new JTextField();
        add(phoneField);

        // 性别
        add(new JLabel("性别:"));
        genderComboBox = new JComboBox<>(new String[]{"男", "女"});
        add(genderComboBox);

        // 生日
        add(new JLabel("生日:"));
        birthdayField = new JTextField();
        add(birthdayField);

        // 余额
        add(new JLabel("余额:"));
        balanceField = new JTextField();
        add(balanceField);

        // 专业
        add(new JLabel("专业:"));
        majorField = new JTextField();
        add(majorField);

        // 班级
        add(new JLabel("班级:"));
        classroomField = new JTextField();
        add(classroomField);

        // 开户按钮
        openAccountButton = new JButton("开户");
        openAccountButton.addActionListener(this::openAccount);
        add(new JLabel()); // Empty cell for spacing
        add(openAccountButton);
    }

    private void openAccount(ActionEvent e) {
        String accountId = accountIdField.getText();
        String name = nameField.getText();
        String password = passwordField.getText();
        String studentId = studentIdField.getText();
        String phone = phoneField.getText();
        char gender = genderComboBox.getSelectedItem().equals("男") ? 'M' : 'F';
        String birthday = birthdayField.getText();
        int balance = Integer.parseInt(balanceField.getText());
        String major = majorField.getText();
        int classroom = Integer.parseInt(classroomField.getText());

        // 创建 LibraryAccount 实例
        LibraryAccount newAccount = new LibraryAccount(accountId, name, password, studentId, phone, gender, birthday, balance, major, classroom);

        // 使用 LibrarySystem 来进行开户
        librarySystem.openAccount(newAccount);

        // 开户成功提示
        JOptionPane.showMessageDialog(this, "开户成功");
    }
}
