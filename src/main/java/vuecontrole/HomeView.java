package vuecontrole;

import javax.swing.*;

public class HomeView extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelPlanning;

    public HomeView() {
        setContentPane(panelPlanning);
        InitialisationFenetre();
    }

    private void InitialisationFenetre() {
        this.setTitle("DOCTO2I - Planning");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE);
        //this.add(panel1);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        HomeView homeView1 = new HomeView();
    }
}
