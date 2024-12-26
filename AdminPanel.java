import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class AdminPanel extends Application {
    private BookService bookService;

    @Override
    public void start(Stage primaryStage) {
        // Layout setup for admin panel
        VBox layout = new VBox(10);

        TextField bookTitleField = new TextField();
        bookTitleField.setPromptText("Enter book title");
        Button queryButton = new Button("Query Book");

        queryButton.setOnAction(e -> {
            try {
                List<Book> books = bookService.getBookByTitle(bookTitleField.getText());
                if (books.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "Book not found!").showAndWait();
                } else {
                    // Display book information
                    books.forEach(book -> {
                        System.out.println(book);
                    });
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(bookTitleField, queryButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
