package com.example.look;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CartController {

    @FXML
    private ListView<String> cartListView;
    @FXML
    private Label totalPriceLabel;

    private ObservableList<String> cartItems = FXCollections.observableArrayList();
    private List<Integer> selectedProductIds;
    private List<String> selectedProductNames;
    private List<Integer> selectedQuantities;
    private int totalPrice;

    public void setCartItems(List<String> items, List<Integer> productIds, List<Integer> quantities, int price) {
        this.selectedProductIds = productIds;
        this.selectedProductNames = items;
        this.selectedQuantities = quantities;
        this.totalPrice = price;

        for (int i = 0; i < items.size(); i++) {
            cartItems.add(items.get(i) + " - Quantity: " + quantities.get(i));
        }

        cartListView.setItems(cartItems);
        totalPriceLabel.setText(totalPrice + " dt");
    }

    @FXML
    private void confirmPurchase() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection == null) {
            System.err.println("Database connection failed.");
            showAlert("Erreur", "Échec de la connexion à la base de données.");
            return;
        }

        String updateQuery = "UPDATE produits SET quantite = quantite - ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            connection.setAutoCommit(false); // Start transaction
            for (int i = 0; i < selectedProductIds.size(); i++) {
                int productId = selectedProductIds.get(i);
                int quantityToReduce = selectedQuantities.get(i);

                System.out.println("Updating product ID " + productId + " to reduce quantity by " + quantityToReduce);
                preparedStatement.setInt(1, quantityToReduce);
                preparedStatement.setInt(2, productId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit(); // Commit transaction

            System.out.println("Quantities updated successfully in the database.");

            // Close the connection after successful update
            connection.close();

            // Show success message
            showAlert("Success", "Commande passée avec succès !");

            // Clear the cart and reset total price
            clearCart();

            // Close the cart window
            Stage stage = (Stage) cartListView.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Échec de la mise à jour de la base de données.");
            try {
                connection.rollback(); // Rollback transaction in case of error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void clearCart() {
        selectedProductIds.clear();
        selectedProductNames.clear();
        selectedQuantities.clear();
        cartItems.clear();
        totalPrice = 0;
        totalPriceLabel.setText("0 dt");
    }

    @FXML
    private void cancelPurchase() {
        // Clear the cart
        clearCart();
        // Close the cart window
        Stage stage = (Stage) cartListView.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

