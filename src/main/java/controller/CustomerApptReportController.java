package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.AppointmentsQuery.*;

/**
 * Displays and controls the Customer Appointmens report screen.
 */
public class CustomerApptReportController implements Initializable {
    public Button mainMenuBtn;
    public Button custByRegionBtn;
    public Button contactApptBtn;
    public Button userApptBtn;
    public TableView<Appointment> apptsByMonthTV;
    public TableColumn<Appointment, String> monthCol;
    public TableColumn<Appointment, Integer> monthCountCol;
    public TableView<Appointment> apptsByTypeTV;
    public TableColumn<Appointment, Integer> typeCol;
    public TableColumn<Appointment, Integer> typeCountCol;
    public AnchorPane anchor;

    /**
     * Runs the methods that fill each of the tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillByMonthTV();
            fillByTypeTV();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Runs the query that counts the number of appointments in each month, and fills the appropriate table.
     * @throws SQLException
     */
    public void fillByMonthTV() throws SQLException {
        try {
            countByMonth();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        apptsByMonthTV.setItems(apptsByMonth);

        monthCol.setCellValueFactory(new PropertyValueFactory<>("str"));
        monthCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
    }

    /**
     * Runs the query that counts the number of appointments for each type, and fills the appropriate table.
     * @throws SQLException
     */
    public void fillByTypeTV() throws SQLException {
        try{
            countByType();
        }catch(Exception e){
            throw new RuntimeException(e);
        }

        apptsByTypeTV.setItems(apptsByType);

        typeCol.setCellValueFactory(new PropertyValueFactory<>("str"));
        typeCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
    }

    /**
     * Takes the user to the Customers By Region report screen.
     */
    public void toCustByRegion(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-by-region-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user to the Contact Schedule report screen.
     */
    public void toContactApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/contact-appt-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user to the User Schedule report screen.
     */
    public void toUserApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/user-appt-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user to the main Customers screen.
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
