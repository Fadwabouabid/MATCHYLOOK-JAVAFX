package com.example.look;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label LoginMessageLabel;
    @FXML
    private TextField EmailTextField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private TextField NomTextField;
    @FXML
    private TextField PrenomTextField;
    @FXML
    private PasswordField PasswordField2;

    public void SignupButtonOnAction(ActionEvent e){

        if (!EmailTextField.getText().isBlank() && !PasswordField.getText().isBlank()&&!NomTextField.getText().isBlank() && !PrenomTextField.getText().isBlank()&& !PasswordField2.getText().isBlank()){
            LoginMessageLabel.setText("Bienvenue!");
        }
        else {
            LoginMessageLabel.setText("veillez ecrire tous les informations!");
        }
    }
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage=(Stage) cancelButton.getScene().getWindow();
        stage.close();;
    }
}
