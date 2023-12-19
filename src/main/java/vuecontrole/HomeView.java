package vuecontrole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modele.HP;
import modele.RendezVous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class HomeView extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelPlanning;
    private JTable tablePlanningGlobal;
    private JLabel labelNameApp;
    private JLabel labelDoctorName;
    private JButton logoutButton;
    private JTable tablePlanningDocteur;
    private JTable tablePlanningSalle;
    private JLabel LabelDate;
    private HP hp;
    private final String date;

    public HomeView() {
        this.date = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRANCE));
        this.hp = null;
        this.LabelDate.setText(date);
        setContentPane(panelPlanning);
        InitialisationFenetre();
        InitialisationTableauPlanningGlobal();
        InitialisationTableauPlanningDocteur();
        InitialisationTableauPlanningSalle();

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login1 = new Login();
                dispose();
            }
        });
    }

    public HomeView(HP hp) throws HeadlessException {
        this();
        this.hp = hp;
        this.labelDoctorName.setText(hp.getFirstname() + " " + hp.getName());
    }

    private void InitialisationTableauPlanningGlobal() {
        tablePlanningGlobal.setEnabled(false);
        tablePlanningGlobal.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");
        model.addRow(new Object[]{"Heure", "Rendez-vous"});
        /*for(int i = 8; i < 22; i++) {
            model.addRow(new Object[]{i + "h00", "Rendez-vous n°" + i + " - Patient n°" + i + " - Salle n°" + i});
        }*/
        tablePlanningGlobal.setModel(model);
        tablePlanningGlobal.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningGlobal.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    private void InitialisationTableauPlanningSalle(){
        tablePlanningSalle.setEnabled(false);
        tablePlanningSalle.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");
        model.addRow(new Object[]{"Heure", date});
        for(int i = 8; i < 22; i++) {
            model.addRow(new Object[]{i + "h00", "Rendez-vous n°" + i + " - Patient n°" + i + " - Salle n°" + i});
        }
        tablePlanningSalle.setModel(model);
        tablePlanningSalle.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningSalle.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    private void InitialisationTableauPlanningDocteur(){
        tablePlanningDocteur.setEnabled(false);
        tablePlanningDocteur.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");
        model.addRow(new Object[]{"Heure", date});
        this.GetPlanningDocteur(model);
        /*for(int i = 8; i < 22; i++) {
            model.addRow(new Object[]{i + "h00", "Rendez-vous n°" + i + " - Patient n°" + i + " - Salle n°" + i});
        }*/
        tablePlanningDocteur.setModel(model);
        tablePlanningDocteur.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningDocteur.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    private void InitialisationFenetre() {
        this.setTitle("DOCTO2I - Planning");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void GetPlanningDocteur(DefaultTableModel model){
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Planning.getJourneeOfDoctorByDate");
        query.setParameter("planningid", 1);
        query.setParameter("doctorid", 4);
        query.setParameter("date", "20231101");

        List<RendezVous> planningDocteur = query.getResultList();

        if(planningDocteur != null) {
            for (RendezVous rv : planningDocteur) {
                //rv.substring(0, 2);
                System.out.println(rv);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Echec du chargement du planning !");
        }
    }

    public static void main(String[] args) {
        HomeView homeView1 = new HomeView();
    }
}
