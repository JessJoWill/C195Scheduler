package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.FirstLvlDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static Utilities.DivisionsQuery.*;

public class CustByRegionController implements Initializable {

    public TableView<FirstLvlDivision> customersByRegionTV;
    public TableColumn<FirstLvlDivision, String> firstDivisionCol;
    public TableColumn<FirstLvlDivision, String> countryCol;
    public TableColumn<FirstLvlDivision, Integer> numCustomersCol;
    public Button userApptBtn;
    public Button contactApptBtn;
    public Button custApptBtn;
    public Button mainMenuBtn;

    /**
     * Runs queries to get all divisions and the customer count in each one, and fills the table with the information.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getDivisions();
            byRegion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customersByRegionTV.setItems(custByRegion);

        firstDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        numCustomersCol.setCellValueFactory(new PropertyValueFactory<>("customerCount"));
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
     * Takes the user to the Customer Appointments report screen.
     */
    public void toCustApptReport(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customer-appts-report-view.fxml")));
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 645);
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Takes the user back to the main Customers screen.
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
