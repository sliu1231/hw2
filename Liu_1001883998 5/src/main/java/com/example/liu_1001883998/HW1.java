package com.example.liu_1001883998;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.lang.reflect.Field;
import java.util.*;

public class HW1 extends Application {

    private final TextField nameField = new TextField("Name");
    private final TextField streetField = new TextField("Street");
    private final TextField cityField = new TextField("City");
    private final TextField stateField = new TextField("State");
    private final TextField zipField = new TextField("Zip");

    private final CheckBox appCheckBox = new CheckBox("APP");
    private final CheckBox musicCheckBox = new CheckBox("MUSIC");
    private final ComboBox<String> musicTypeComboBox = new ComboBox<>();
    private final ToggleGroup appTypeGroup = new ToggleGroup();
    private final RadioButton gameRadioButton = new RadioButton("Game");
    private final RadioButton productivityRadioButton = new RadioButton("Productivity");
    private final RadioButton socialRadioButton = new RadioButton("Social");
   private final TextField titleField = new TextField("Title");
   private final DatePicker datePurchasedPicker = new DatePicker();
   private final TextField accountNumberField = new TextField();
   private final Button submitButton = new Button("SUBMIT");
   private final Button finishButton = new Button("FINISH");
   private final GridPane gridPane = new GridPane();
   private final HBox buttonBox = new HBox(10);
   private final Scene scene = new Scene(gridPane, 800, 390);
   private final List<RadioButton> rbs = new ArrayList<>();
   private boolean musicChecked, appChecked;
   private final List<TextField> ALL_TEXT_FIELDS = new ArrayList<>();
   private final List<String> FIELD_NAMES = new ArrayList<>();






    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Details Form");



        musicTypeComboBox.getItems().addAll("Rock", "Pop", "Jazz", "Classical");
        musicTypeComboBox.setPromptText("Select Music Type");

        gameRadioButton.setToggleGroup(appTypeGroup);
        productivityRadioButton.setToggleGroup(appTypeGroup);
        socialRadioButton.setToggleGroup(appTypeGroup);

        rbs.add(gameRadioButton);
        rbs.add(productivityRadioButton);
        rbs.add(socialRadioButton);
        FIELD_NAMES.add("Name");
        FIELD_NAMES.add("Street");
        FIELD_NAMES.add("City");
        FIELD_NAMES.add("State");
        FIELD_NAMES.add("Zip");
        FIELD_NAMES.add("Title");



        titleField.setPromptText("Title");
        accountNumberField.setPromptText("Account Number");


        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Street:"), 0, 1);
        gridPane.add(streetField, 1, 1);
        gridPane.add(new Label("City:"), 0, 2);
        gridPane.add(cityField, 1, 2);
        gridPane.add(new Label("State:"), 0, 3);
        gridPane.add(stateField, 1, 3);
        gridPane.add(new Label("Zip:"), 0, 4);
        gridPane.add(zipField, 1, 4);

        gridPane.add(new Label("Product Selection:"), 0, 5);
        gridPane.add(appCheckBox, 1, 5);
        gridPane.add(musicCheckBox, 2, 5);

        gridPane.add(new Label("Music Type:"), 0, 6);
        gridPane.add(musicTypeComboBox, 1, 6);

        gridPane.add(new Label("App Type:"), 0, 7);
        gridPane.add(gameRadioButton, 1, 7);
        gridPane.add(productivityRadioButton, 2, 7);
        gridPane.add(socialRadioButton, 3, 7);

        gridPane.add(new Label("Title:"), 0, 8);
        gridPane.add(titleField, 1, 8);
        gridPane.add(new Label("Date Purchased:"), 0, 9);
        gridPane.add(datePurchasedPicker, 1, 9);
        gridPane.add(new Label("Account Number:"), 0, 10);
        gridPane.add(accountNumberField, 1, 10);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(submitButton, finishButton);
        gridPane.add(buttonBox, 1, 11, 2, 1);

        collectTextFields();


        wireEvents();


        primaryStage.setScene(scene);
        primaryStage.show();




    }

    private void collectTextFields()
    {
        ALL_TEXT_FIELDS.add(nameField);
        ALL_TEXT_FIELDS.add(streetField);
        ALL_TEXT_FIELDS.add(cityField);
        ALL_TEXT_FIELDS.add(stateField);
        ALL_TEXT_FIELDS.add(zipField);
        ALL_TEXT_FIELDS.add(titleField);
        ALL_TEXT_FIELDS.add(accountNumberField);

    }

    /**
     * Evaluate the fields state
     * @return true if stateful false if else.
     */
    private boolean validateTextFields()
    {
        boolean allValidFields = true; // go backwards x ! set invert.


        // iterate through each field one x one
        for(TextField T : ALL_TEXT_FIELDS)
        {
            for(String S : FIELD_NAMES)
            {
                String value = T.getText();
                if(value.isEmpty() || S.equals(value) || value.equals("Please provide a value..."))
                {
                    T.setText("Please provide a value..");
                    allValidFields = false;
                }
            }
        }


        return allValidFields;

    }

    /**
     * Conditional check the radio buttons.
     * music radio button need only validate that the drop-down has an option whereas the inverse is true for app buttons
     * @return true if we found valid checked elements false if else.
     */
    private boolean validateRadioButtons()
    {
        boolean anyChecked = true;

        if(musicChecked)
            return validateDropDown(); // true if value is inside the drop-down

        if(appChecked)
        {
            for(RadioButton r : rbs)
                if(r.isSelected())
                    return anyChecked; // if we find one thats checked then we pass.
        }

        if(!musicChecked || !appChecked)
            showAlert(Alert.AlertType.ERROR, "Please select..", "Please select either the app " +
                    "or music box");


        return false; // if we make it here we have a problem houston.
    }

    private boolean validateDropDown()
    {
        return (musicTypeComboBox.getValue() != null); // if we have nothing selected then we are not valid.
    }

    // Modify the `wireEvents()` method to completely disable/enable components
    private boolean wireEvents() {
        // disable music components when APP checkbox is selected
        appCheckBox.setOnAction(event -> {
            boolean isSelected = appCheckBox.isSelected();
            musicTypeComboBox.setDisable(isSelected);
            musicCheckBox.setSelected(!isSelected);
            musicChecked = false;
            appChecked = isSelected;

            // Enable or disable App radio buttons
            for (RadioButton r : rbs) {
                r.setDisable(!isSelected);
            }
        });

        // disable app components when MUSIC checkbox is selected
        musicCheckBox.setOnAction(event -> {
            boolean isSelected = musicCheckBox.isSelected();
            appCheckBox.setSelected(!isSelected);
            appChecked = false;
            musicChecked = isSelected;

            // Enable or disable App radio buttons
            for (RadioButton r : rbs) {
                r.setDisable(isSelected);
            }
            musicTypeComboBox.setDisable(!isSelected);
        });

        // Submit button with validation
        submitButton.setOnAction(e -> {
            boolean valid = validateTextFields() && validateRadioButtons();

            if (!valid) {
                showAlert(Alert.AlertType.ERROR, "Not Valid", "Please fill in all required fields.");
            } else {
                writeToFile();
                clearForm();
                nameField.requestFocus();
            }
        });

        // Finish button to exit the application
        finishButton.setOnAction(e -> {
            Stage stage = (Stage) finishButton.getScene().getWindow();
            stage.close();
        });

        return true;
    }


    private void showAlert(Alert.AlertType alertType, String title, String message)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();


    }

    private void writeToFile()
    {

    }



    public static void main(String[] args)
    {
        launch(args);
    }
}
