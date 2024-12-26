# README.md

# 图书馆管理系统

## 项目简介
这是一个基于JavaFX的图书馆管理系统，旨在提供用户友好的界面来管理图书和用户信息。该系统允许用户进行开户、借书、还书等操作。

## 项目结构
```
library-management-system
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   └── library
│   │   │   │       ├── App.java
│   │   │   │       ├── controller
│   │   │   │       │   └── MainController.java
│   │   │   │       ├── model
│   │   │   │       │   ├── Book.java
│   │   │   │       │   └── User.java
│   │   │   │       └── view
│   │   │   │           └── MainView.fxml
│   │   └── resources
│   │       └── styles
│   │           └── styles.css
├── pom.xml
└── README.md
```

## 环境要求
- JDK 11 或更高版本
- Maven

## 安装与运行
1. 克隆项目到本地：
   ```
   git clone <repository-url>
   ```
2. 进入项目目录：
   ```
   cd library-management-system
   ```
3. 使用Maven构建项目：
   ```
   mvn clean install
   ```
4. 运行应用程序：
   ```
   mvn javafx:run
   ```

## 使用说明
- 启动应用后，用户可以通过界面进行开户、借书、还书等操作。
- 所有用户信息和图书信息将保存在系统中。

## 贡献
欢迎任何形式的贡献！请提交问题或拉取请求。

## 许可证
本项目采用MIT许可证。