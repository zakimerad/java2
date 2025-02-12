package java2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginApp extends JFrame {
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton loginButton, signUpButton;

    public LoginApp() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel userLabel = new JLabel("Email:");
        userLabel.setBounds(10, 20, 80, 25);
        add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        add(userText);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setBounds(10, 50, 100, 25);
        add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(120, 50, 165, 25);
        add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 90, 100, 25);
        add(loginButton);

        signUpButton = new JButton("Créer un compte");
        signUpButton.setBounds(120, 90, 150, 25);
        add(signUpButton);

       
        loginButton.addActionListener(e -> verifierLogin());

        
        signUpButton.addActionListener(e -> {
            dispose(); 
            new SignIn(); 
        });

        setVisible(true);
    }

    private void verifierLogin() {
        String email = userText.getText();
        String motDePasse = new String(passwordText.getPassword());

        if (VerifierUser.verifierUtilisateur(email, motDePasse)) {
            JOptionPane.showMessageDialog(this, " Connexion réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new Dashboard();
        } else {
            JOptionPane.showMessageDialog(this, " Email ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginApp();
    }
}
