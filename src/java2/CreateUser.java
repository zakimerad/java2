package java2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUser {

    public static void addUser(String nom, String email, String motDePasse) {
        String sql = "INSERT INTO users (nom, email, mot_de_passe) VALUES (?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, email);
            pstmt.setString(3, motDePasse);
            pstmt.executeUpdate();
            System.out.println("✅ Utilisateur ajouté avec succès !");

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        addUser("Zakaria", "zakaria@example.com", "123456");
    }
}
