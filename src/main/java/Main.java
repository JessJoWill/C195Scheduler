import DAO.JDBC;
import Utilities.CountriesQuery;
import Utilities.CustomersQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TheCountry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {

        JDBC.openConnection();
        launch(args);







        JDBC.closeConnection();
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login-view.fxml"));
        root.setStyle("-fx-font-family: 'Arial';");
        Scene scene = new Scene(root, 500, 250);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
