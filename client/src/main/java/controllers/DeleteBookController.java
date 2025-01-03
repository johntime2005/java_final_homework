package controllers;

import utils.BookService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteBookController {
    private BookService bookService;
    
    public DeleteBookController() {
        this.bookService = new BookService();
    }
    
    @FXML
    private TextField idField;

    @FXML
    public void deleteBook(javafx.event.ActionEvent event) {
        try {
            if (idField.getText().isEmpty()) {
                idField.setPromptText("Please enter a valid ID");
                return;
            }
            int id = Integer.parseInt(idField.getText());
            bookService.deleteBook(id);
            idField.clear();
            new Alert(Alert.AlertType.INFORMATION, "书籍删除成功！").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "书籍删除失败!请输入正确的id!").showAndWait();
        }
    }

    @FXML
    private void handleBack(javafx.event.ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("/views/adminsystem.fxml"));
            Stage stage = (Stage) idField.getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
