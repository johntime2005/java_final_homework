import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LibraryUI extends Application {
    private bookMegement bookManager;
    private TableView<Book> bookTable;
    private TextField bookNameField, authorField, publisherField, publishDateField, isbnField;

    @Override
    public void start(Stage primaryStage) {
        bookManager = new bookMegement();

        // 创建主布局
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // 创建输入表单
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);

        bookNameField = new TextField();
        authorField = new TextField();
        publisherField = new TextField();
        publishDateField = new TextField();
        isbnField = new TextField();

        formPane.addRow(0, new Label("书名:"), bookNameField);
        formPane.addRow(1, new Label("作者:"), authorField);
        formPane.addRow(2, new Label("出版社:"), publisherField);
        formPane.addRow(3, new Label("出版日期:"), publishDateField);
        formPane.addRow(4, new Label("ISBN:"), isbnField);

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
        TableColumn<Book, String> nameColumn = new TableColumn<>("书名");
        TableColumn<Book, String> authorColumn = new TableColumn<>("作者");
        TableColumn<Book, String> publisherColumn = new TableColumn<>("出版社");
        TableColumn<Book, String> dateColumn = new TableColumn<>("出版日期");
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        TableColumn<Book, Integer> quantityColumn = new TableColumn<>("数量");

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        publisherColumn.setCellValueFactory(cellData -> cellData.getValue().publisherProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().publishDateProperty());
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        bookTable.getColumns().addAll(idColumn, nameColumn, authorColumn, publisherColumn, 
                                    dateColumn, isbnColumn, quantityColumn);

        // 设置按钮事件
        addButton.setOnAction(e -> addBook());
        deleteButton.setOnAction(e -> deleteBook());
        updateButton.setOnAction(e -> updateBook());
        queryButton.setOnAction(e -> queryBook());

        // 组装界面
        mainLayout.getChildren().addAll(formPane, buttonBox, bookTable);

        // 创建场景
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("图书管理系统");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 加载初始数据
        refreshTableData();
    }

    private void addBook() {
        try {
            bookManager.addBook(
                bookNameField.getText(),
                authorField.getText(),
                publisherField.getText(),
                publishDateField.getText(),
                isbnField.getText()
            );
            clearFields();
            refreshTableData();
            showAlert("成功", "图书添加成功！", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("错误", "添加图书失败：" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void deleteBook() {
        try {
            bookManager.deleteBook(bookNameField.getText());
            clearFields();
            refreshTableData();
            showAlert("成功", "图书删除成功！", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("错误", "删除图书失败：" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateBook() {
        try {
            bookManager.updateBook(
                bookNameField.getText(),
                authorField.getText(),
                publisherField.getText(),
                publishDateField.getText(),
                isbnField.getText()
            );
            clearFields();
            refreshTableData();
            showAlert("成功", "图书更新成功！", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("错误", "更新图书失败：" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void queryBook() {
        try {
            String bookName = bookNameField.getText();
            if (bookName.isEmpty()) {
                refreshTableData();
            } else {
                List<Book> books = bookManager.queryBook(bookName);
                bookTable.setItems(FXCollections.observableArrayList(books));
            }
        } catch (Exception e) {
            showAlert("错误", "查询图书失败：" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void refreshTableData() {
        try {
            List<Book> books = bookManager.queryAllBooks();
            bookTable.setItems(FXCollections.observableArrayList(books));
        } catch (Exception e) {
            showAlert("错误", "刷新数据失败：" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        bookNameField.clear();
        authorField.clear();
        publisherField.clear();
        publishDateField.clear();
        isbnField.clear();
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
