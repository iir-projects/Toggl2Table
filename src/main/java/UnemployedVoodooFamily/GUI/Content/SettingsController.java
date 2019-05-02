package UnemployedVoodooFamily.GUI.Content;

import UnemployedVoodooFamily.Data.Enums.FilePath;
import UnemployedVoodooFamily.Logic.SettingsLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SettingsController {

    @FXML
    private Button viewHoursBtn;
    @FXML
    private Button confirmHoursBtn;
    @FXML
    private DatePicker hoursFromField;
    @FXML
    private DatePicker hoursToField;
    @FXML
    private Button viewDataBtn;
    @FXML
    private Button dataPathBtn;
    @FXML
    private TextField dataPathField;
    @FXML
    private Button logoutBtn;
    @FXML
    private Pane contentRoot;
    @FXML
    private TextField hoursField;
    @FXML
    private TextField noteField;
    @FXML
    private TableView hoursView;
    @FXML
    private Label inputFeedbackLabel;

    @FXML
    private Button deleteDataBtn;

    private SettingsLogic logic;

    /**
     * Load and return the root node of Settings.fxml
     * @return the root node of Settings.fxml
     * @throws IOException
     */
    public Node loadFXML() throws IOException {
        URL r = getClass().getClassLoader().getResource("view\\" + "Settings.fxml");
        return FXMLLoader.load(r);
    }

    public void initialize() {
        this.logic = new SettingsLogic(FilePath.getCurrentUserWorkhours());
        this.hoursView.setVisible(false);
        toggleViewHoursList();
        setKeyAndClickListeners();
    }

    @SuppressWarnings("Duplicates")
    /**
     * Set key and click listeners
     */ private void setKeyAndClickListeners() {
        confirmHoursBtn.setOnAction(event -> trySetWorkHours());
        viewHoursBtn.setOnAction(event -> toggleViewHoursList());
        deleteDataBtn.setOnAction(event -> {
            try {
                logic.deleteStoredData(String.valueOf(FilePath.LOGS_HOME));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        hoursField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            hoursField.getStyleClass().remove("error");
            if(! newValue) {
                if(! hoursField.getText().matches("[0-1]?[0-9](\\.[0-9][0-9]?)?|2[0-3](\\.[0-9][0-9]?)?|24(\\.[0][0]?)?")) {
                    hoursField.setText("");
                    hoursField.getStyleClass().add("error");
                }
            }
        });

        hoursFromField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                hoursFromField.getStyleClass().remove("error");
            }
            else if(hoursFromField.getValue() == null) {
                hoursFromField.getStyleClass().add("error");
            }
            else if(oldValue) {
                hoursFromField.getStyleClass().remove("error");
            }
        });

        hoursToField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                hoursToField.getStyleClass().remove("error");
            }
            else if(hoursToField.getValue() == null) {
                hoursToField.getStyleClass().add("error");
            }
            else if(oldValue) {
                hoursToField.getStyleClass().remove("error");
            }
        });

        viewDataBtn.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(FilePath.APP_HOME.getPath()));
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            catch(IllegalArgumentException e) {
                //could not find path
            }
        });

        //deleteDataBtn.getStyleClass().add("delete");
    }

    /**
     * Toggle visibility of hours table
     */
    private void toggleViewHoursList() {
        if(hoursView.isVisible()) {
            hoursView.setVisible(false);
            viewHoursBtn.setText("View hours");
        }
        else {
            hoursView.setVisible(true);
            viewHoursBtn.setText("Hide hours");
            populateHoursList();
        }
    }

    /**
     * WIP
     */
    private void populateHoursList() {
        logic.populateHoursTable(hoursView);
    }

    /**
     * Sets work hours if fields are not empty
     */
    private void trySetWorkHours() {
        boolean success = false;
        if(hoursFromField.getValue() == null) {
            hoursFromField.getStyleClass().add("error");
            showWorkHourInputErrorMessage("From date not specified");
        }
        else if(hoursToField.getValue() == null) {
            hoursToField.getStyleClass().add("error");
            showWorkHourInputErrorMessage("To date not specified");
        }
        else if(hoursField.getText().equals("")) {
            hoursField.getStyleClass().add("error");
            showWorkHourInputErrorMessage("Amount of work hours not specified");
        }
        else if(hoursFromField.getValue().isAfter(hoursToField.getValue())) {
            hoursFromField.getStyleClass().add("error");
            hoursToField.getStyleClass().add("error");
            showWorkHourInputErrorMessage("\"From\" date cannot come before \"to\" date");
        }
        else {
            success = true;
        }
        if(success) {
            logic.setWorkHours(hoursFromField.getValue(), hoursToField.getValue(), hoursField.getText(), noteField.getText());

            if(hoursView.isVisible()) {
                logic.populateHoursTable(hoursView);
            }

            clearWorkHourInputFields();
            showWorkHoursInputSuccess();
        }

    }

    private void showWorkHourInputErrorMessage(String errorMessage) {
        inputFeedbackLabel.getStyleClass().remove("success");
        inputFeedbackLabel.getStyleClass().add("error");
        inputFeedbackLabel.setText(errorMessage);
    }

    private void showWorkHoursInputSuccess()    {
        inputFeedbackLabel.getStyleClass().remove("error");
        inputFeedbackLabel.getStyleClass().add("success");
        inputFeedbackLabel.setText("Work hours added");
    }

    private void clearWorkHourInputFields() {
        hoursFromField.setValue(null);
        hoursFromField.getStyleClass().remove("error");
        hoursToField.setValue(null);
        hoursToField.getStyleClass().remove("error");
        hoursField.setText("");
        hoursField.getStyleClass().remove("error");
        noteField.setText("");
        noteField.getStyleClass().remove("error");
        inputFeedbackLabel.setText("");
        inputFeedbackLabel.getStyleClass().remove("error");
    }
}

