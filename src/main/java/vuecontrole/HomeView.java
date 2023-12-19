package vuecontrole;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelPlanning;
    private JTable tablePlanningGlobal;
    private JLabel labelNameApp;
    private JLabel labelDoctorName;
    private JButton logoutButton;

    public HomeView() {
        setContentPane(panelPlanning);
        InitialisationFenetre();
        InitialisationTableauPlanningGlobal();

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login1 = new Login();
                dispose();
            }
        });
    }

    private void InitialisationTableauPlanningGlobal() {
        tablePlanningGlobal.setEnabled(false);
        tablePlanningGlobal.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");
        model.addRow(new Object[]{"Heure", "Lundi"});
        for(int i = 8; i < 22; i++) {
            model.addRow(new Object[]{i + "h00", "Rendez-vous n°" + i + " - Patient n°" + i + " - Salle n°" + i});
        }
        tablePlanningGlobal.setModel(model);
        tablePlanningGlobal.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningGlobal.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    private void InitialisationFenetre() {
        this.setTitle("DOCTO2I - Planning");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        HomeView homeView1 = new HomeView();
    }
}
