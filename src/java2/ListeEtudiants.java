package java2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ListeEtudiants extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> etudiantsList;
    private JTextField searchField;
    private JTextField nomField, prenomField, emailField, telephoneField;
    private String selectedAnnee;

    public ListeEtudiants(String annee) {
        selectedAnnee = annee;
        setTitle("Étudiants - " + annee);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        etudiantsList = new JList<>(listModel);
        add(new JScrollPane(etudiantsList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        searchField = new JTextField();
        panel.add(new JLabel("Rechercher:"));
        panel.add(searchField);

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        telephoneField = new JTextField();

        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Téléphone:"));
        panel.add(telephoneField);

        add(panel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton rechercherBtn = new JButton("Rechercher");
        JButton ajouterBtn = new JButton("Ajouter");
        JButton modifierBtn = new JButton("Modifier");
        JButton supprimerBtn = new JButton("Supprimer");

        buttonPanel.add(rechercherBtn);
        buttonPanel.add(ajouterBtn);
        buttonPanel.add(modifierBtn);
        buttonPanel.add(supprimerBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        chargerEtudiants();

        rechercherBtn.addActionListener(e -> rechercherEtudiant());
        ajouterBtn.addActionListener(e -> ajouterEtudiant());
        modifierBtn.addActionListener(e -> modifierEtudiant());
        supprimerBtn.addActionListener(e -> supprimerEtudiant());

        setVisible(true);
    }

    private void chargerEtudiants() {
        listModel.clear();
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT nom, prenom FROM etudiants WHERE annee = ?")) {

            pstmt.setString(1, selectedAnnee);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nomComplet = rs.getString("nom") + " " + rs.getString("prenom");
                listModel.addElement(nomComplet);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rechercherEtudiant() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) return;

        listModel.clear();
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT nom, prenom FROM etudiants WHERE annee = ? AND nom LIKE ?")) {

            pstmt.setString(1, selectedAnnee);
            pstmt.setString(2, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                listModel.addElement(rs.getString("nom") + " " + rs.getString("prenom"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ajouterEtudiant() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();

        if (nom.isEmpty() || prenom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs !");
            return;
        }

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO etudiants (nom, prenom, email, telephone, annee) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, email);
            pstmt.setString(4, telephone);
            pstmt.setString(5, selectedAnnee);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Étudiant ajouté !");
            chargerEtudiants();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierEtudiant() {
        String selectedValue = etudiantsList.getSelectedValue();
        if (selectedValue == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un étudiant à modifier !");
            return;
        }

        String[] parts = selectedValue.split(" ");
        String nom = parts[0];

        String newNom = nomField.getText();
        String newPrenom = prenomField.getText();

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE etudiants SET nom = ?, prenom = ? WHERE nom = ? AND annee = ?")) {

            pstmt.setString(1, newNom);
            pstmt.setString(2, newPrenom);
            pstmt.setString(3, nom);
            pstmt.setString(4, selectedAnnee);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Étudiant modifié !");
            chargerEtudiants();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerEtudiant() {
        String selectedValue = etudiantsList.getSelectedValue();
        if (selectedValue == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un étudiant à supprimer !");
            return;
        }

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM etudiants WHERE nom = ? AND annee = ?")) {

            pstmt.setString(1, selectedValue.split(" ")[0]);
            pstmt.setString(2, selectedAnnee);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Étudiant supprimé !");
            chargerEtudiants();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
