import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BorrowBookPanel extends JPanel {
    private JTextField studentIdField, bookIdField;
    private JButton borrowButton;

    public BorrowBookPanel() {
        setLayout(new GridLayout(3, 2));

        add(new JLabel("学号:"));
        studentIdField = new JTextField();
        add(studentIdField);

        add(new JLabel("书籍编号:"));
        bookIdField = new JTextField();
        add(bookIdField);

        borrowButton = new JButton("借书");
        borrowButton.addActionListener(this::borrowBook);
        add(new JLabel()); // Empty cell for spacing
        add(borrowButton);
    }

    private void borrowBook(ActionEvent e) {
        String studentId = studentIdField.getText();
        String bookId = bookIdField.getText();

        // 调用图书馆系统借书的方法
        // librarySystem.borrowBook(bookId, studentId);
        JOptionPane.showMessageDialog(this, "借书成功");
    }
}