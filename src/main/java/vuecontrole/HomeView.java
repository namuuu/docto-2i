package vuecontrole;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;
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
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

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
    private String date;

    public HomeView() {
        String date = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRENCH).format(new Date());

        this.hp = null;
        this.LabelDate.setText(date.toString());
        setContentPane(panelPlanning);

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
        InitialisationFenetre();
        InitialisationTableauPlanningGlobal();
        InitialisationTableauPlanningDocteur();
        InitialisationTableauPlanningSalle();
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

        this.GetPlanningDocteur(model, hp, LocalDate.now());

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

    private void GetPlanningDocteur(DefaultTableModel model, HP hp, LocalDate date) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Planning.getJourneeOfDoctorByDate");

        // Test avec une date en dur, prévoir quand aucun planning pour la journée !
        query.setParameter("planningid", 1);
        //query.setParameter("doctorid", hp.getId());
        query.setParameter("doctorid", 4);
        //query.setParameter("date", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        query.setParameter("date", "20231101");

        List<RendezVous> planningDocteur = query.getResultList();
        planningDocteur.sort(Comparator.comparing(rv -> rv.getCreneau().getStartHour()));

        if(planningDocteur != null) {
            for (RendezVous rv : planningDocteur) {
                model.addRow(new Object[]{rv.getCreneau().getStartHour() + "h00", " - Patient n°" + rv.getPatient().getId() + " - Salle n°" + rv.getSalle().getNumero()});
            }
        } else {
            JOptionPane.showMessageDialog(this, "Echec du chargement du planning !");
        }
    }

    private void GetPlanningSalle(DefaultTableModel model, HP hp, LocalDate date) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Planning.getJourneeOfDoctorByDate");

        List<RendezVous> planningDocteur = query.getResultList();
    }

    public static void main(String[] args) {
        HomeView homeView1 = new HomeView();
    }
}
