import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ModifyAccountPanel extends JPanel {
    private JTextField accountIdField, nameField, passwordField, studentIdField, phoneField, birthdayField, majorField, classroomField;
    private JComboBox<String> genderComboBox;
    private JTextField balanceField;
    private JButton modifyButton;

    public ModifyAccountPanel() {
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

        // 修改按钮
        modifyButton = new JButton("修改信息");
        modifyButton.addActionListener(this::modifyAccount);
        add(new JLabel()); // Empty cell for spacing
        add(modifyButton);
    }

    // 执行修改操作
    private void modifyAccount(ActionEvent e) {
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

        // 假设你有一个 LibrarySystem 实例来处理修改信息
        // librarySystem.modifyInfo(studentId, accountId, gender, birthday, phone, major, classroom);

        // 修改成功提示
        JOptionPane.showMessageDialog(this, "修改信息成功");
    }
}