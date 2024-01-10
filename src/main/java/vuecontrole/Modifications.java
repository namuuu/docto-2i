package vuecontrole;

import javax.swing.*;
import java.awt.*;

public class Modifications extends JFrame {
    private JLabel nomOnglet;
    private JTabbedPane selectionModifs;
    private JComboBox salleLibreCombo;
    private JLabel salleChgt;
    private JPanel deplacerOnglet;
    private JPanel decalerOnglet;
    private JButton confirmerDplcmtButton;
    private JComboBox horaireLibreCombo;
    private JButton confirmerDclButton;
    private JPanel changerOnglet;
    private JLabel medecinChgt;
    private JButton confirmerChgtButton;
    private JComboBox medecinLibreCombo;
    private JLabel horaireChgt;
    private JPanel panelModifs;

    public Modifications() {
        this.setContentPane(panelModifs);
        initialisationFenetre();
    }

    private void initialisationFenetre() {
        this.setTitle("Docto2IPU");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.ORANGE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Modifications modifs = new Modifications();
    }
}
