package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class UserAppointmentsReportController {
    public RadioButton apptsByWeekRB;
    public RadioButton apptsByMonthRB;
    public ToggleGroup apptFilterToggle;
    public TableView myApptsTableView;
    public TableColumn apptIdCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptLocationCol;
    public TableColumn apptContactCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public TableColumn apptCustomerIdCol;
    public TableColumn apptUserIdCol;
    public Button mainMenuBtn;

    public void filterByWeek(ActionEvent actionEvent) {
    }

    public void filterByMonth(ActionEvent actionEvent) {
    }

    public void onCancel(ActionEvent actionEvent) {
    }
}
