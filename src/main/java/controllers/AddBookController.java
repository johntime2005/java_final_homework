package controllers;

import impl.bookMegement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Book;
import utils.BookService;
import dao.bookMegementDao;
import utils.DatabaseConnection;

import java.sql.SQLException;
import java.util.concurrent.Flow;

public class AddBookController   {
    private bookMegementDao bookDao;
    public AddBookController() throws SQLException {
        this.bookDao = new bookMegement(DatabaseConnection.getConnection());
    }
    @FXML
    private TextField titleField;
    
    @FXML
    private TextField authorField;
    
    @FXML
    private TextField publisherField;

    @FXML
    private void handleSubmit(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        Boolean isborrowed = false;
        // 验证输入并添加图书
        try {
            Book book = new Book(0, title, author, publisher, isborrowed);
            bookDao.addBook(book);
            titleField.clear();
            authorField.clear();
            publisherField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("添加成功");
            new Alert(Alert.AlertType.INFORMATION, "书籍添加成功！").showAndWait();
            // 实现添加图书逻辑
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("输入错误");
            alert.setContentText("请输入有效书籍信息");
            alert.showAndWait();
        }
        catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "书籍添加失败!").showAndWait();
        }

    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("/views/adminsystem.fxml"));
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
