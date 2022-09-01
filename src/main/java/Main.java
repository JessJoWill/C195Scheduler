import DAO.JDBC;
import Utilities.CountriesQuery;
import Utilities.CustomersQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.TheCountry;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch(args);
/*
        int rowsAffected = CustomersQuery.delete(4);
        int rowsAffected = CustomersQuery.insert("Alejandro Mills", "344 Maple Drive, Jackson", "56223", "555-555-5555", "Jess", 29);
        */

        CustomersQuery.select();

        CountriesQuery.select();

        JDBC.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login-view.fxml"));
        root.setStyle("-fx-font-family: 'Arial';");
        Scene scene = new Scene(root, 400, 250);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
