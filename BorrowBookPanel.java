import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BorrowBookPanel extends JPanel {
    private JTextField accountIdField, bookIdField;
    private JButton borrowButton;
    private LibrarySystem librarySystem;

    public BorrowBookPanel(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
        setLayout(new GridLayout(3, 2));

        // 账户ID输入框
        add(new JLabel("请输入账户ID:"));
        accountIdField = new JTextField();
        add(accountIdField);

        // 书籍编号输入框
        add(new JLabel("请输入书籍编号:"));
        bookIdField = new JTextField();
        add(bookIdField);

        // 借书按钮
        borrowButton = new JButton("借书");
        borrowButton.addActionListener(this::borrowBook);
        add(new JLabel()); // Empty cell for spacing
        add(borrowButton);
    }

    private void borrowBook(ActionEvent e) {
        String accountId = accountIdField.getText();
        String bookId = bookIdField.getText();

        librarySystem.borrowBook(accountId, bookId);

        JOptionPane.showMessageDialog(this, "借书成功");
    }
}
