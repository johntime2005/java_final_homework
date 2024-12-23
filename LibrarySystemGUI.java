import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibrarySystemGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public LibrarySystemGUI() {
        setTitle("图书馆管理系统");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 创建面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));

        // 按钮
        JButton openAccountButton = new JButton("开户");
        JButton removeAccountButton = new JButton("销户");
        JButton modifyAccountButton = new JButton("修改信息");
        JButton borrowBookButton = new JButton("借书");
        JButton returnBookButton = new JButton("还书");

        // 按钮动作
        openAccountButton.addActionListener(e -> cardLayout.show(cardPanel, "openAccountPanel"));
        removeAccountButton.addActionListener(e -> cardLayout.show(cardPanel, "removeAccountPanel"));
        modifyAccountButton.addActionListener(e -> cardLayout.show(cardPanel, "modifyAccountPanel"));
        borrowBookButton.addActionListener(e -> cardLayout.show(cardPanel, "borrowBookPanel"));
        returnBookButton.addActionListener(e -> cardLayout.show(cardPanel, "returnBookPanel"));

        // 添加按钮到主面板
        mainPanel.add(openAccountButton);
        mainPanel.add(removeAccountButton);
        mainPanel.add(modifyAccountButton);
        mainPanel.add(borrowBookButton);
        mainPanel.add(returnBookButton);

        // 各个功能面板
        JPanel openAccountPanel = new OpenAccountPanel();
        JPanel removeAccountPanel = new RemoveAccountPanel();
        JPanel modifyAccountPanel = new ModifyAccountPanel();
        JPanel borrowBookPanel = new BorrowBookPanel();
        JPanel returnBookPanel = new ReturnBookPanel();

        // 添加到CardLayout面板
        cardPanel.add(mainPanel, "mainPanel");
        cardPanel.add(openAccountPanel, "openAccountPanel");
        cardPanel.add(removeAccountPanel, "removeAccountPanel");
        cardPanel.add(modifyAccountPanel, "modifyAccountPanel");
        cardPanel.add(borrowBookPanel, "borrowBookPanel");
        cardPanel.add(returnBookPanel, "returnBookPanel");

        add(cardPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibrarySystemGUI frame = new LibrarySystemGUI();
            frame.setVisible(true);
        });
    }
}