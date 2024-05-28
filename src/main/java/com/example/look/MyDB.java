package com.example.look;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bhk
 */
public class MyDB {

    final String url = "jdbc:mysql://localhost/matchy";
    final String login = "root";
    final String password = "";
    Connection connexion;
    static MyDB instance;

    public MyDB() {
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
}

