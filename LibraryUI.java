import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.sql.*;

public class LibraryUI extends Application {
    private bookMegement bookManager;
    private TableView<Book> bookTable;
    private TextField idField, titleField, authorField, quantityField;

    @Override
    public void start(Stage primaryStage) throws SQLException {
        // 创建数据库连接
        Connection conn = DriverManager.getConnection(
            "jdbc:sqlserver://116.205.125.206:1433;database=library;encrypt=true;trustServerCertificate=true",
            "sa", "952891332wW!"
        );
        bookManager = new bookMegement(conn);

        // 创建主布局
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // 创建输入表单
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);

        idField = new TextField();
        titleField = new TextField();
        authorField = new TextField();
        quantityField = new TextField();

        formPane.addRow(0, new Label("ID:"), idField);
        formPane.addRow(1, new Label("书名:"), titleField);
        formPane.addRow(2, new Label("作者:"), authorField);
        formPane.addRow(3, new Label("数量:"), quantityField);

        // 创建按钮
        HBox buttonBox = new HBox(10);
        Button addButton = new Button("添加图书");
        Button deleteButton = new Button("删除图书");
        Button updateButton = new Button("更新图书");
        Button queryButton = new Button("查询图书");
        buttonBox.getChildren().addAll(addButton, deleteButton, updateButton, queryButton);

        // 创建表格
        bookTable = new TableView<>();
        TableColumn<Book, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Book, String> titleColumn = new TableColumn<>("书名");
        TableColumn<Book, String> authorColumn = new TableColumn<>("作者");
        TableColumn<Book, Integer> quantityColumn = new TableColumn<>("数量");

        idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthor()));
        quantityColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        bookTable.getColumns().addAll(idColumn, titleColumn, authorColumn, quantityColumn);

        // 设置按钮事件
        addButton.setOnAction(e -> addBook());
        deleteButton.setOnAction(e -> deleteBook());
        updateButton.setOnAction(e -> updateBook());
        // queryButton.setOnAction(e -> queryBook());

        // 组装界面
        mainLayout.getChildren().addAll(formPane, buttonBox, bookTable);

        // 创建场景
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setTitle("图书管理系统");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 创建表
        try {
            bookManager.create();
        } catch (SQLException ex) {
            showAlert("错误", "创建表失败：" + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void addBook() {
        try {
            Book book = new Book(
                Integer.parseInt(idField.getText()),
                titleField.getText(),
                authorField.getText(),
                Integer.parseInt(quantityField.getText())
            );
            bookManager.addBook(book);
            clearFields();
            showAlert("成功", "图书添加成功！", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("错误", "添加图书失败：" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void deleteBook() {
        try {
            bookManager.deleteBook(Integer.parseInt(idField.getText()));
            clearFields();
            showAlert("成功", "图书删除成功！", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("错误", "删除图书失败：" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateBook() {
        try {
            Book book = new Book(
                Integer.parseInt(idField.getText()),
                titleField.getText(),
                authorField.getText(),
                Integer.parseInt(quantityField.getText())
            );
            bookManager.updateBook(book);
            clearFields();
            showAlert("成功", "图书更新成功！", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("错误", "更新图书失败：" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // private void queryBook() {
    //     try {
    //         String title = titleField.getText();
    //         if (title.isEmpty()) {
    //             refreshTableData();
    //         } else {
    //             bookTable.setItems(FXCollections.observableArrayList(bookManager.queryBook(title)));
    //         }
    //     } catch (SQLException e) {
    //         showAlert("错误", "查询图书失败：" + e.getMessage(), Alert.AlertType.ERROR);
    //     }
    // }

    // private void refreshTableData() {
    //     try {
    //         bookTable.setItems(FXCollections.observableArrayList(bookManager.queryAllBooks()));
    //     } catch (SQLException e) {
    //         showAlert("错误", "刷新数据失败：" + e.getMessage(), Alert.AlertType.ERROR);
    //     }
    // }

    private void clearFields() {
        idField.clear();
        titleField.clear();
        authorField.clear();
        quantityField.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
