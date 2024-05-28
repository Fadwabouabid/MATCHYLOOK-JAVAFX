package com.example.look;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    final String url = "jdbc:mysql://localhost/matchy";
    final String login = "root";
    final String password = "";
    Connection connexion;
    static MyDB instance;

    public DatabaseConnection() {
        try {
            connexion
                    = DriverManager.getConnection(url, login, password);
            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.out.println("Erreur de connexion à la base de données");
        }
    }

    public static  MyDB getInstance(){
        if(instance == null)
            instance = new MyDB();
        return instance;
    }

    public Connection getConnection(){
        return connexion;
    }
    public List<Product> getProductsBySubCategory(int subCategoryId) {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();

        if (connection != null) {
            String query = "SELECT id, nom, prix, image, quantite FROM produits WHERE sous_categorie_id IN (3, 6, 12)";

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    int prix = resultSet.getInt("prix");
                    String path = resultSet.getString("image");
                    int quantite = resultSet.getInt("quantite");  // Fetch the quantity
                    products.add(new Product(id, nom, prix, path, quantite));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return products;
    }
    public List<Product> getProductsBySubCategory_femme(int subCategoryId) {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();

        if (connection != null) {
            String query = "SELECT id, nom, prix, image, quantite FROM produits WHERE sous_categorie_id IN (5, 7, 11)";

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    int prix = resultSet.getInt("prix");
                    String path = resultSet.getString("image");
                    int quantite = resultSet.getInt("quantite");  // Fetch the quantity
                    products.add(new Product(id, nom, prix, path, quantite));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return products;
    }
    public List<Product> getProductsBySubCategory_accessoire(int subCategoryId) {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();

        if (connection != null) {
            String query = "SELECT id, nom, prix, image, quantite FROM produits WHERE sous_categorie_id IN (8, 9, 10)";

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    int prix = resultSet.getInt("prix");
                    String path = resultSet.getString("image");
                    int quantite = resultSet.getInt("quantite");  // Fetch the quantity
                    products.add(new Product(id, nom, prix, path, quantite));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return products;
    }

    public List<Product> getProductsBySubCategory_acceuil(int subCategoryId) {
        List<Product> products = new ArrayList<>();
        Connection connection = getConnection();

        if (connection != null) {
            String query = "SELECT id, nom, prix, image, quantite FROM produits ";

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    int prix = resultSet.getInt("prix");
                    String path = resultSet.getString("image");
                    int quantite = resultSet.getInt("quantite");  // Fetch the quantity
                    products.add(new Product(id, nom, prix, path, quantite));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return products;
    }

    public int getAvailableQuantity(int productId) {
        int quantity = 0;
        Connection connection = getConnection();

        if (connection != null) {
            String query = "SELECT quantite FROM produits WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    quantity = resultSet.getInt("quantite");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return quantity;
    }

    public void updateQuantity(int productId, int newQuantity) {
        Connection connection = getConnection();

        if (connection != null) {
            String query = "UPDATE produits SET quantite = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, newQuantity);
                preparedStatement.setInt(2, productId);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
