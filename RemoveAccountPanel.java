import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RemoveAccountPanel extends JPanel {
    private JTextField studentIdField;
    private JButton removeAccountButton;
    private LibrarySystem librarySystem;

    public RemoveAccountPanel(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
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

    private void removeAccount(ActionEvent e) {
        String studentId = studentIdField.getText();
        // 假设我们按学生ID来删除账户
        librarySystem.removeAccount(studentId);
        JOptionPane.showMessageDialog(this, "销户成功");
    }
}
