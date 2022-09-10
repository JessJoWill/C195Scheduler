package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.AppointmentsQuery.*;

/**
 * Displays and controls the reports screen.
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillByMonthTV();
           // fillByTypeTV();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

/*    public void fillByTypeTV() throws SQLException {
        countByType();
        apptsByTypeTV.setItems(apptsByType);

        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCountCol.setCellValueFactory(new PropertyValueFactory<>("customerCount"));
    }*/

    public void fillByMonthTV() throws SQLException {
        try {
            countByMonth();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        apptsByMonthTV.setItems(apptsByMonth);

        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
    }

    public void toCustByRegion(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-by-region-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toContactApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/contact-appt-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void toUserApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/user-appt-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
