package java2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class tablesSql {
    public static void creerTables() {
        try (Connection conn = DBconnection.getConnection()) {
            
            
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "nom VARCHAR(50) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "mot_de_passe VARCHAR(255) NOT NULL" +
                    ")";
            conn.createStatement().executeUpdate(createUsersTable);
            System.out.println(" Table 'users' créée avec succès.");

            
            String createEtudiantsTable = "CREATE TABLE IF NOT EXISTS etudiants (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "nom VARCHAR(50) NOT NULL, " +
                    "prenom VARCHAR(50) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "telephone VARCHAR(20), " +
                    "annee VARCHAR(10) NOT NULL" +
                    ")";
            conn.createStatement().executeUpdate(createEtudiantsTable);
            System.out.println(" Table 'etudiants' créée avec succès.");
            
            
            insererEtudiants(conn);

        } catch (SQLException e) {
            System.out.println(" Erreur lors de la création des tables : " + e.getMessage());
        }
    }

    public static void insererEtudiants(Connection conn) {
        String sql = "INSERT INTO etudiants (nom, prenom, email, telephone, annee) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String[][] etudiants = {
                {"Ahmed", "Benali", "ahmed.benali@email.com", "0654321234", "L1"},
                {"Sofia", "Mehdi", "sofia.mehdi@email.com", "0776543210", "L2"},
                {"Omar", "Khalil", "omar.khalil@email.com", "0665987452", "L3"},
                {"Sarah", "Bouaziz", "sarah.bouaziz@email.com", "0612345678", "M1"},
                {"Yassine", "Zerrouki", "yassine.zerrouki@email.com", "0798564321", "M2"}
            };

            
            for (String[] etudiant : etudiants) {
                pstmt.setString(1, etudiant[0]);  
                pstmt.setString(2, etudiant[1]);  
                pstmt.setString(3, etudiant[2]);  
                pstmt.setString(4, etudiant[3]);  
                pstmt.setString(5, etudiant[4]);  
                pstmt.executeUpdate();
            }

            System.out.println(" Étudiants insérés avec succès !");
        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'insertion des étudiants : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        creerTables();
    }
}
