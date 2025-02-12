package java2;

import javax.swing.*;
import java.awt.*;

public class SignIn extends JFrame {
    private JTextField nomField, emailField;
    private JPasswordField passwordField;
    private JButton signUpButton, backButton, loginButton;

    public SignIn() {
        setTitle("Inscription");
        setSize(350, 300); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Nom:"));
        nomField = new JTextField();
        add(nomField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        add(passwordField);

        signUpButton = new JButton("S'inscrire");
        backButton = new JButton("Retour");
        loginButton = new JButton("Déjà un compte ? Se connecter");

        add(signUpButton);
        add(backButton);
        add(loginButton); 

        signUpButton.addActionListener(e -> inscrireUtilisateur());
        backButton.addActionListener(e -> {
            dispose(); 
            new LoginApp();
        });
        loginButton.addActionListener(e -> {
            dispose(); 
            new LoginApp(); 
        });

        setVisible(true);
    }

    private void inscrireUtilisateur() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());

        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        CreateUser.addUser(nom, email, motDePasse);
        JOptionPane.showMessageDialog(this, "Utilisateur inscrit avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        new Dashboard();
        
        nomField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        new SignIn();
    }
}