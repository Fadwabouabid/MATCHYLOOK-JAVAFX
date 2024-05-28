package com.example.look;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccessoireController implements Initializable {
    @FXML
    private GridPane productContainer;
    @FXML
    private Label prixTextField;
    @FXML
    private Button CommanderButton;
    @FXML
    private Label SuccesTextFIeld;

    private List<Integer> selectedProductIds = new ArrayList<>();
    private List<String> selectedProductNames = new ArrayList<>();
    private List<Integer> selectedQuantities = new ArrayList<>();  // New list for quantities

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadProducts();
    }

    private void loadProducts() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        List<Product> products = dbConnection.getProductsBySubCategory_accessoire(3); // You can change the sub-category ID here

        if (products == null || products.isEmpty()) {
            System.err.println("No products found.");
            return;
        }

        int column = 0;
        int row = 0;
        for (Product product : products) {
            AnchorPane productPane = createProductPane(product.getId(), product.getNom(), product.getPrix(), product.getPath(), product.getQuantite());
            if (productPane != null) {
                productContainer.add(productPane, column, row);
                column++;
                if (column == 3) { // 3 products per row
                    column = 0;
                    row++;
                }
            }
        }
    }

    private AnchorPane createProductPane(int id, String nom, int prix, String path, int quantite) {
        if (quantite == 0) {
            // Don't display the product if quantity is 0
            return null;
        }

        AnchorPane productPane = new AnchorPane();
        productPane.setPrefSize(200, 250);
        productPane.setStyle("-fx-background-color: #D3D3D3;");

        Label nameLabel = new Label(nom);
        nameLabel.setLayoutX(68);
        nameLabel.setLayoutY(124);

        Label priceLabel = new Label("Prix: " + prix);
        priceLabel.setLayoutX(78);
        priceLabel.setLayoutY(154);

        Label quantityLabel = new Label("Quantité disponible: " + quantite); // Display available quantity
        quantityLabel.setLayoutX(20);
        quantityLabel.setLayoutY(170); // Adjust the Y position as needed

        ImageView imageView = new ImageView();
        imageView.setFitHeight(125);
        imageView.setFitWidth(500);
        imageView.setLayoutX(45);
        imageView.setPreserveRatio(true);

        try {
            System.out.println("Trying to load image from path: " + path);
            URL imageUrl = getClass().getResource("image/"+path);
            if (imageUrl != null) {
                imageView.setImage(new Image(imageUrl.toString()));
                System.out.println("Image loaded successfully from path: " + path);
            } else {
                System.err.println("Image file not found at path: " + path);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Quantity selection components
        Label quantityInputLabel = new Label("Quantity:");
        quantityInputLabel.setLayoutX(20);
        quantityInputLabel.setLayoutY(200);

        TextField quantityTextField = new TextField();
        quantityTextField.setLayoutX(80);
        quantityTextField.setLayoutY(200);

        Button acheterButton = new Button("Acheter");
        acheterButton.setLayoutX(66);
        acheterButton.setLayoutY(230);
        acheterButton.setOnAction(event -> acheterButtonOnAction(event, id, nom, prix, quantityTextField.getText()));

        productPane.getChildren().addAll(nameLabel, priceLabel, quantityLabel, imageView, quantityInputLabel, quantityTextField, acheterButton);
        return productPane;
    }



    private void acheterButtonOnAction(ActionEvent event, int id, String nom, int prix, String quantityStr) {
        try {
            int enteredQuantity = Integer.parseInt(quantityStr);
            DatabaseConnection dbConnection = new DatabaseConnection();
            int availableQuantity = dbConnection.getAvailableQuantity(id);

            if (enteredQuantity <= availableQuantity) {
                selectedProductIds.add(id);
                selectedProductNames.add(nom);
                selectedQuantities.add(enteredQuantity);
                int prixActuel = Integer.parseInt(prixTextField.getText());
                int nouveauPrix = prixActuel + (prix * enteredQuantity);
                prixTextField.setText(String.valueOf(nouveauPrix));
            } else {
                // Show message if entered quantity is more than available quantity
                showAlert("Erreur", "La quantité demandée est supérieure à la quantité disponible en stock.");
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer une quantité valide.");
        }
    }

    @FXML
    void CommanderButtonOnAction(ActionEvent event) {
        int prixActuel = Integer.parseInt(prixTextField.getText());
        if (prixActuel != 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cart.fxml"));
                Parent root = loader.load();

                CartController cartController = loader.getController();
                cartController.setCartItems(selectedProductNames, selectedProductIds, selectedQuantities, prixActuel);

                // Update quantities in the database after the user confirms the order
                DatabaseConnection dbConnection = new DatabaseConnection();
                for (int i = 0; i < selectedProductIds.size(); i++) {
                    int id = selectedProductIds.get(i);
                    int enteredQuantity = selectedQuantities.get(i);
                    int availableQuantity = dbConnection.getAvailableQuantity(id);
                    dbConnection.updateQuantity(id, availableQuantity - enteredQuantity);
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Clear the cart and reset total price field in HommeController
                clearCartInHommeController();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            SuccesTextFIeld.setText("Veuillez choisir au moins un produit !");
        }
    }

    private void clearCartInHommeController() {
        selectedProductIds.clear();
        selectedProductNames.clear();
        selectedQuantities.clear();
        prixTextField.setText("0");
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void switchtofemme(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("femme.fxml"));
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    public void switchtoacceuil(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("acceuil.fxml"));
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    public void switchtoapropos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Apropos.fxml"));
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }


}

