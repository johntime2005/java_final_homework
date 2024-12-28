package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AddBookController {
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField authorField;
    
    @FXML
    private TextField pressField;
    
    @FXML
    private TextField priceField;
    
    @FXML
    private TextField stockField;
    
    @FXML
    private void handleSubmit(ActionEvent event) {
        String name = nameField.getText();
        String author = authorField.getText();
        String press = pressField.getText();
        // 验证输入并添加图书
        try {
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            // 实现添加图书逻辑
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("输入错误");
            alert.setContentText("请输入有效的价格和库存数量!");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("/views/adminsystem.fxml"));
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
