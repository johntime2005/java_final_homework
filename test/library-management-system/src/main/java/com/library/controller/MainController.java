package com.library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button borrowButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button queryButton;

    @FXML
    public void initialize() {
        // 初始化逻辑
    }

    @FXML
    private void handleBorrowButtonAction() {
        // 处理借书按钮的动作
    }

    @FXML
    private void handleReturnButtonAction() {
        // 处理还书按钮的动作
    }

    @FXML
    private void handleQueryButtonAction() {
        // 处理查询按钮的动作
    }
}