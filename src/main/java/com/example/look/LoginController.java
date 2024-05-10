package com.example.look;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
   @FXML
    private Button cancelButton;
   @FXML
   private Label LoginMessageLabel;
   @FXML
   private TextField EmailTextField;
   @FXML
   private PasswordField PasswordField;
   public void LoginButtonOnAction(ActionEvent e){

       if (!EmailTextField.getText().isBlank() && !PasswordField.getText().isBlank()){
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