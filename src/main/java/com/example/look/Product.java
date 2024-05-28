package com.example.look;

public class Product {
    private int id;
    private String nom;
    private int prix;
    private String path;
    private int quantite;

    // Constructor
    public Product(int id, String nom, int prix, String path, int quantite) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.path = path;
        this.quantite = quantite;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

