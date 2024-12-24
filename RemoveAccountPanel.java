import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RemoveAccountPanel extends JPanel {
    private JTextField studentIdField;
    private JButton removeAccountButton;

    public RemoveAccountPanel() {
        setLayout(new GridLayout(3, 2));

        // 学号输入框
        add(new JLabel("请输入学号:"));
        studentIdField = new JTextField();
        add(studentIdField);

        // 销户按钮
        removeAccountButton = new JButton("销户");
        removeAccountButton.addActionListener(this::removeAccount);
        add(new JLabel()); // Empty cell for spacing
        add(removeAccountButton);
    }

    // 执行销户操作
    private void removeAccount(ActionEvent e) {
        String studentId = studentIdField.getText();

        // 假设你有一个 LibrarySystem 实例来处理销户
        // librarySystem.removeAccount(studentId);

        // 销户成功提示
        JOptionPane.showMessageDialog(this, "销户成功");
    }
}