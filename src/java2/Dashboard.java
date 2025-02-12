package java2;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private JComboBox<String> anneeComboBox;
    private JButton validerButton;

    public Dashboard() {
        setTitle("Dashboard - Sélection d'année");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Sélectionnez une année :");
        add(label);

        
        String[] annees = {"L1", "L2", "L3", "M1", "M2"};
        anneeComboBox = new JComboBox<>(annees);
        add(anneeComboBox);

        validerButton = new JButton("Afficher Étudiants");
        add(validerButton);

        
        validerButton.addActionListener(e -> {
            String anneeChoisie = (String) anneeComboBox.getSelectedItem();
            if (anneeChoisie != null) {
                new ListeEtudiants(anneeChoisie);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
