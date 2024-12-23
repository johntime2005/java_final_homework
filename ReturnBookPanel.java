import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReturnBookPanel extends JPanel {
    private JTextField studentIdField, bookIdField;
    private JButton returnBookButton;

    public ReturnBookPanel() {
        setLayout(new GridLayout(3, 2));

        // 学号输入框
        add(new JLabel("请输入学号:"));
        studentIdField = new JTextField();
        add(studentIdField);

        // 书籍编号输入框
        add(new JLabel("请输入书籍编号:"));
        bookIdField = new JTextField();
        add(bookIdField);

        // 还书按钮
        returnBookButton = new JButton("还书");
        returnBookButton.addActionListener(this::returnBook);
        add(new JLabel()); // Empty cell for spacing
        add(returnBookButton);
    }

    // 执行还书操作
    private void returnBook(ActionEvent e) {
        String studentId = studentIdField.getText();
        String bookId = bookIdField.getText();

        // 假设你有一个 LibrarySystem 实例来处理还书
        // librarySystem.returnBook(bookId, studentId);

        // 还书成功提示
        JOptionPane.showMessageDialog(this, "还书成功");
    }
}