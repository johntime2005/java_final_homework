import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainPanel extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setPadding(new javafx.geometry.Insets(20));

        Button adminLoginButton = new Button("Admin Login");
        Button userLoginButton = new Button("User Login");
        Button userRegisterButton = new Button("User Register");

        adminLoginButton.setOnAction(e -> {
            // 进入管理员登录界面
            new LoginPanel(true).start(new Stage());
            primaryStage.close();
        });

        userLoginButton.setOnAction(e -> {
            // 进入用户登录界面
            new LoginPanel(false).start(new Stage());
            primaryStage.close();
        });

        userRegisterButton.setOnAction(e -> {
            // 进入用户注册界面
            new RegisterPanel().start(new Stage());
            primaryStage.close();
        });

        layout.getChildren().addAll(adminLoginButton, userLoginButton, userRegisterButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Panel");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
