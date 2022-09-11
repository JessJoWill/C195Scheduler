import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


/**
 * An application for scheduling and storing customer information.
 * @author Jessica Williams
 */
public class Main extends Application {
    public AnchorPane anchor;

    /**
     * Opens and closes the database connection, and launches the program.
     */
    public static void main(String[] args) throws SQLException, IOException {
        JDBC.openConnection();
        launch(args);

        JDBC.closeConnection();
    }

    /**
     * Initializes the GUI, and takes the user to a longin screen.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login-view.fxml")));
        Scene scene = new Scene(root, 500, 250);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
