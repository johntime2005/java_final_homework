import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReturnBookPanel extends JPanel {
    private JTextField accountIdField, bookIdField;
    private JButton returnButton;
    private LibrarySystem librarySystem;

    public ReturnBookPanel(LibrarySystem librarySystem) {
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

        // 还书按钮
        returnButton = new JButton("还书");
        returnButton.addActionListener(this::returnBook);
        add(new JLabel()); // Empty cell for spacing
        add(returnButton);
    }

    private void returnBook(ActionEvent e) {
        String accountId = accountIdField.getText();
        String bookId = bookIdField.getText();

        librarySystem.returnBook(accountId, bookId);

        JOptionPane.showMessageDialog(this, "还书成功");
    }
}
