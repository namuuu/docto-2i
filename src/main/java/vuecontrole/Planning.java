package vuecontrole;

import javax.swing.*;

public class Planning extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelPlanning;

    public Planning() {
        setContentPane(panelPlanning);
        InitialisationFenetre();
    }

    private void InitialisationFenetre() {
        this.setTitle("DOCTO2I - Planning");
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE);
        //this.add(panel1);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Planning planning1 = new Planning();
    }
}
