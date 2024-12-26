import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class AdminPanel extends Application {
    private BookService bookService;

    // GUI组件
    private TextField bookIdField, bookTitleField, bookAuthorField, bookQuantityField;
    private Button addButton, queryButton, updateButton;
    private TextArea bookInfoArea;

    public AdminPanel(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void start(Stage primaryStage) {
        // 初始化GUI组件
        bookIdField = new TextField();
        bookIdField.setPromptText("Enter Book ID");

        bookTitleField = new TextField();
        bookTitleField.setPromptText("Enter Book Title");

        bookAuthorField = new TextField();
        bookAuthorField.setPromptText("Enter Author");

        bookQuantityField = new TextField();
        bookQuantityField.setPromptText("Enter Quantity");

        addButton = new Button("Add Book");
        queryButton = new Button("Query Book");
        updateButton = new Button("Update Book");

        bookInfoArea = new TextArea();
        bookInfoArea.setEditable(false);

        // 添加图书按钮事件
        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(bookIdField.getText());
                String title = bookTitleField.getText();
                String author = bookAuthorField.getText();
                int quantity = Integer.parseInt(bookQuantityField.getText());

                Book newBook = new Book(id, title, author, quantity);
                bookService.createBook(newBook);
                bookInfoArea.setText("Book added successfully!");
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                bookInfoArea.setText("Error: " + ex.getMessage());
            }
        });

        // 查询图书按钮事件
        queryButton.setOnAction(e -> {
            try {
                String title = bookTitleField.getText();
                List<Book> books = bookService.getBookByTitle(title);
                if (books.isEmpty()) {
                    bookInfoArea.setText("Book not found!");
                } else {
                    StringBuilder bookDetails = new StringBuilder();
                    for (Book book : books) {
                        bookDetails.append(book.toString()).append("\n");
                    }
                    bookInfoArea.setText(bookDetails.toString());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                bookInfoArea.setText("Error: " + ex.getMessage());
            }
        });

        // 更新图书按钮事件
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(bookIdField.getText());
                String title = bookTitleField.getText();
                String author = bookAuthorField.getText();
                int quantity = Integer.parseInt(bookQuantityField.getText());

                Book updatedBook = new Book(id, title, author, quantity);
                bookService.updateBook(updatedBook);
                bookInfoArea.setText("Book updated successfully!");
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                bookInfoArea.setText("Error: " + ex.getMessage());
            }
        });

        // 布局
        VBox layout = new VBox(10, bookIdField, bookTitleField, bookAuthorField, bookQuantityField,
                addButton, queryButton, updateButton, bookInfoArea);
        Scene scene = new Scene(layout, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
