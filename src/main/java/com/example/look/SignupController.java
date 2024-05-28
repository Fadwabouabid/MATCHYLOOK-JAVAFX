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
import java.sql.SQLException;
import java.sql.Statement;

public class SignupController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label inscriptionMessageLabel;
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
           if (PasswordField2.getText().equals(PasswordField.getText())) {
               faire_inscription(e);
           }
           else {
               inscriptionMessageLabel.setText("mot de passes differents");
           }
        }
        else {
            inscriptionMessageLabel.setText("veillez ecrire tous les informations!");
        }
    }
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage=(Stage) cancelButton.getScene().getWindow();
        stage.close();;
    }
    public void faire_inscription(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();



        String inscription = "INSERT INTO utilisateurs(adressemail,mtp,mtpc,nom,prenom,sexe) VALUES ('" + EmailTextField.getText() + "','" + PasswordField.getText() + "','" + PasswordField.getText() + "','"+NomTextField.getText() +"','"+PrenomTextField.getText() + "','"+"homme"+"')";
        try {
            Statement statement = connectDB.createStatement();
            int rowsAffected = statement.executeUpdate(inscription);
            if (rowsAffected > 0) {
                inscriptionMessageLabel.setText("Bienvenue!");
            } else {
                inscriptionMessageLabel.setText("veillez saisir tous les informations!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void switchtologin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.show();
        // Close the current stage if needed
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }}
