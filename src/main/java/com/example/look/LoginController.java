package com.example.look;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
         validateLogin(e);
       }
       else {
           LoginMessageLabel.setText("veillez ecrire tous les informations!");
       }
   }
   public void cancelButtonOnAction(ActionEvent e){
       Stage stage=(Stage) cancelButton.getScene().getWindow();
       stage.close();;
    }
    public void switchtohomme(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("homme.fxml"));
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.show();
        // Close the current stage if needed
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    public void validateLogin(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin ="SELECT count(*) FROM utilisateurs Where adressemail = '"+EmailTextField.getText()+"' AND mtp = '"+PasswordField.getText()+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                if(queryResult.getInt(1)==1) {
                    LoginMessageLabel.setText("Bienvenue!");
                   switchtohomme(event);
                } else {
                    LoginMessageLabel.setText("email ou mot de passe incorrect!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loginButtonOnAction(ActionEvent e) {
        if (!EmailTextField.getText().isBlank() && !PasswordField.getText().isBlank()) {
            validateLogin(e); // Pass the ActionEvent parameter
        } else {
            LoginMessageLabel.setText("Please Enter Email and Password!");
        }
    }
    public void switchtoInscription(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.show();
        // Close the current stage if needed
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}