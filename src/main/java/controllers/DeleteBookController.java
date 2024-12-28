package controllers;

import dao.bookMegementDao;
import impl.bookMegement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DatabaseConnection;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class DeleteBookController {
    private bookMegementDao bookDao;
    public DeleteBookController() throws SQLException {
        this.bookDao = new bookMegement(DatabaseConnection.getConnection());
    }
    @FXML
    private TextField idField;

    @FXML
    public void deleteBook(javafx.event.ActionEvent event)  {
        try{
            System.out.println(idField.getText());
        if (idField.getText().isEmpty()) {
            idField.setPromptText("Please enter a valid ID");
            return;
        }
        int id = Integer.parseInt(idField.getText());
        System.out.println(id);
        bookDao.deleteBook(id);
        idField.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("删除成功");
            new Alert(Alert.AlertType.INFORMATION, "书籍删除成功！").showAndWait();
    }
        catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "书籍删除失败!请输入正确的id!").showAndWait();
        }
        catch (NumberFormatException e)
            {
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
