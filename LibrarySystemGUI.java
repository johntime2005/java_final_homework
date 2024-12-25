import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibrarySystemGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private LibrarySystem librarySystem;

    public LibrarySystemGUI(LibrarySystem librarySystem) {
        this.librarySystem = librarySystem;
        setTitle("图书馆管理系统");
        setSize(500, 400);
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
        JButton queryAccountButton = new JButton("查询账户");

        // 按钮动作
        openAccountButton.addActionListener(e -> cardLayout.show(cardPanel, "openAccountPanel"));
        removeAccountButton.addActionListener(e -> cardLayout.show(cardPanel, "removeAccountPanel"));
        modifyAccountButton.addActionListener(e -> cardLayout.show(cardPanel, "modifyAccountPanel"));
        borrowBookButton.addActionListener(e -> cardLayout.show(cardPanel, "borrowBookPanel"));
        returnBookButton.addActionListener(e -> cardLayout.show(cardPanel, "returnBookPanel"));
        queryAccountButton.addActionListener(e -> cardLayout.show(cardPanel, "queryAccountPanel"));

        // 添加按钮到主面板
        mainPanel.add(openAccountButton);
        mainPanel.add(removeAccountButton);
        mainPanel.add(modifyAccountButton);
        mainPanel.add(borrowBookButton);
        mainPanel.add(returnBookButton);
        mainPanel.add(queryAccountButton);

        // 各个功能面板
        JPanel openAccountPanel = new OpenAccountPanel(librarySystem);
        JPanel removeAccountPanel = new RemoveAccountPanel(librarySystem);
        JPanel modifyAccountPanel = new ModifyAccountPanel(librarySystem);
        JPanel borrowBookPanel = new BorrowBookPanel(librarySystem);
        JPanel returnBookPanel = new ReturnBookPanel(librarySystem);
        JPanel queryAccountPanel = new QueryAccountPanel(librarySystem);

        // 添加面板到cardPanel
        cardPanel.add(mainPanel, "mainPanel");
        cardPanel.add(openAccountPanel, "openAccountPanel");
        cardPanel.add(removeAccountPanel, "removeAccountPanel");
        cardPanel.add(modifyAccountPanel, "modifyAccountPanel");
        cardPanel.add(borrowBookPanel, "borrowBookPanel");
        cardPanel.add(returnBookPanel, "returnBookPanel");
        cardPanel.add(queryAccountPanel, "queryAccountPanel");

        add(cardPanel);
    }

    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        LibrarySystemGUI gui = new LibrarySystemGUI(librarySystem);
        gui.setVisible(true);
    }
}
