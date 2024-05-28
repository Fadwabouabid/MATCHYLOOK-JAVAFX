package com.example.look;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {

    public List<Product> getAllProduits() {
        List<Product> produits = new ArrayList<>();
        String query = "SELECT * FROM produits";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product produit = new Product(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getInt("prix"),
                            rs.getString("image"),
                            rs.getInt("id_sous_categorie")
                    );
                    produits.add(produit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return produits;
    }
}

