package java2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerifierUser {
    public static boolean verifierUtilisateur(String email, String motDePasse) {
        String sql = "SELECT * FROM users WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, motDePasse);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // ✅ Si un utilisateur existe, retourne true

        } catch (SQLException e) {
            System.out.println("❌ Erreur SQL : " + e.getMessage());
            return false;
        }
    }
}
