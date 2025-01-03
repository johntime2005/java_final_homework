package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import dao.bookMegementDao;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

import impl.bookMegement;
import java.sql.SQLException;

public class QueryController implements Initializable  {
    private bookMegementDao bookDao;

    public QueryController() {
        this.bookDao = new bookMegement(); // 移除数据库连接参数
    }

    @FXML
    private TextField searchField;
    
    @FXML
    private TableView<Book> bookTable;
    
    @FXML
    private TableColumn<Book, Integer> idColumn;
    
    @FXML
    private TableColumn<Book, String> nameColumn;
    
    @FXML
    private TableColumn<Book, String> authorColumn;
    
    @FXML
    private TableColumn<Book, String> pressColumn;
    
    @FXML
    private TableColumn<Book, Boolean> borrowedColumn;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化表格列
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        pressColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        borrowedColumn.setCellValueFactory(new PropertyValueFactory<>("isborrowed"));
        
        try {
            // 初始化时加载所有图书
            List<Book> books = bookDao.getAllBooks();
            bookTable.setItems(FXCollections.observableArrayList(books));
        } catch (Exception e) {
            showAlert("错误", "加载图书数据失败: " + e.getMessage());
            //System.out.println("加载图书数据失败: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleSearch(ActionEvent event) {
        try {
            String keyword = searchField.getText().trim();
            List<Book> results;
            
            if (keyword.isEmpty()) {
                // 如果搜索框为空，显示所有图书
                results = bookDao.getAllBooks();
            } else {
                // 使用关键字搜索图书（这里假设用关键字匹配书名、作者和出版社）
                results = bookDao.searchBooks(keyword, keyword, keyword);
            }
            
            bookTable.setItems(FXCollections.observableArrayList(results));
        } catch (Exception e) {
            showAlert("错误", "搜索图书失败: " + e.getMessage());
        }
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("/views/adminsystem.fxml"));
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
