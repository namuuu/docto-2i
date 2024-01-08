package vuecontrole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modele.HP;
import modele.RendezVous;
import modele.Salle;
import modele.planning.Planning;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class HomeView extends JFrame {
    private JTabbedPane selectionPlanning;
    private JPanel panelPlanning;
    private JTable tablePlanningGlobal;
    private JLabel labelNameApp;
    private JLabel labelDoctorName;
    private JButton logoutButton;
    private JTable tablePlanningDocteur;
    private JTable tablePlanningSalle;
    private JLabel LabelDate;
    private JSpinner spinner;
    private JPanel jLabelRechercheSalle;
    private JLabel rechercheSalle;
    private JButton validerRecherche;
    private JComboBox salleCombo;
    private JComboBox planningCombo;
    private JPanel nomDocteur;
    private JPanel nomOnglet;
    private JScrollPane scrollTableSalle;
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
        validerRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialisationTableauPlanningSalle();
            }
        });

        initSalleCombo();
        initPlanningCombo();

    }

    public HomeView(HP hp) throws HeadlessException {
        this();
        this.hp = hp;
        this.labelDoctorName.setText(hp.getFirstname() + " " + hp.getName());
        if(this.hp.isDoctorOrManager() == 0) {
            this.selectionPlanning.remove(0);
            this.selectionPlanning.remove(1);
        } else {
            initialisationTableauPlanningGlobal();
        }
        initialisationFenetre();
        initialisationTableauPlanningDocteur();
        centerTable();
    }

    public void centerTable() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tablePlanningSalle.setDefaultRenderer(Object.class, centerRenderer);
        tablePlanningDocteur.setDefaultRenderer(Object.class, centerRenderer);
        tablePlanningGlobal.setDefaultRenderer(Object.class, centerRenderer);
    }

    private void initSalleCombo() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Salle.getAll");

        List<Salle> list =  query.getResultList();
        for(Salle s : list) {
            salleCombo.addItem(s.getNumero() + " - " + s.getNom());
        }
    }

    private void initPlanningCombo() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Planning.getAllVersion");

        List<Planning> list =  query.getResultList();
        for(Planning p : list) {
            planningCombo.addItem("Planning V" + p.getId());
        }
    }

    private void initialisationTableauPlanningGlobal() {
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

    private void initialisationTableauPlanningSalle(){
        tablePlanningSalle.setEnabled(false);
        tablePlanningSalle.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");

        this.getPlanningSalle(model, LocalDate.now());

        tablePlanningSalle.setModel(model);
        tablePlanningSalle.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningSalle.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    private void initialisationTableauPlanningDocteur(){
        tablePlanningDocteur.setEnabled(false);
        tablePlanningDocteur.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");

        this.getPlanningDocteur(model, hp, LocalDate.now());

        tablePlanningDocteur.setModel(model);
        tablePlanningDocteur.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningDocteur.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    private void initialisationFenetre() {
        this.setTitle("DOCTO2I - Planning");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void getPlanningDocteur(DefaultTableModel model, HP hp, LocalDate date) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Planning.getJourneeOfDoctorByDate");

        // Test avec une date en dur, prévoir quand aucun planning pour la journée !
        //query.setParameter("planningid", 1);
        query.setParameter("planningid", 1);
        query.setParameter("doctorid", hp.getId());
        //query.setParameter("doctorid", 4);
        System.out.println(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        //query.setParameter("date", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        query.setParameter("date", "20231226");

        List<RendezVous> planningDocteur = query.getResultList();
        planningDocteur.sort(Comparator.comparing(rv -> rv.getCreneau().getStartHour()));

        if(planningDocteur != null && planningDocteur.toArray().length > 0) {
            model.addRow(new Object[]{"Heure", "Rendez-vous"});
            for (RendezVous rv : planningDocteur) {
                model.addRow(new Object[]{rv.getCreneau().getStartHour() + "h00",
                        " Patient " + rv.getPatient().getFirstname() + " " + rv.getPatient().getName()
                        + " - Salle n°" + rv.getSalle().getNumero() + " - " + rv.getSalle().getNom()
                });
            }
        } else if(planningDocteur.toArray().length == 0) {
            model.addRow(new Object[]{"Heure", "Aucun rendez-vous"});
        } else {
            JOptionPane.showMessageDialog(this, "Echec du chargement du planning !");
        }
    }

    private void getPlanningSalle(DefaultTableModel model, LocalDate date) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Planning.getJourneeOfSalleByDate");

        query.setParameter("planningid", 1);

        // Récupérer l'élement sélectionné dans la combobox
        String salle = salleCombo.getSelectedItem().toString();
        String[] salleSplit = salle.split(" - ");
        query.setParameter("sallenum", Integer.parseInt(salleSplit[0]));
        //query.setParameter("date", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        query.setParameter("date", "20231226");

        List<RendezVous> planningSalle = query.getResultList();
        planningSalle.sort(Comparator.comparing(rv -> rv.getCreneau().getStartHour()));

        if(planningSalle != null && planningSalle.toArray().length > 0) {
            model.addRow(new Object[]{"Heure", "Rendez-vous"});
            for (RendezVous rv : planningSalle) {
                model.addRow(new Object[]{rv.getCreneau().getStartHour() + "h00",
                        " Patient " + rv.getPatient().getFirstname() + " " + rv.getPatient().getName()
                        + " - Salle n°" + rv.getSalle().getNumero() + " - " + rv.getSalle().getNom()
                        + "- Docteur " + rv.getDoctor().getFirstname() + " " + rv.getDoctor().getName()
                });
            }
        } else if(planningSalle.toArray().length == 0) {
            model.addRow(new Object[]{"Heure", "Aucun rendez-vous"});
        } else {
            JOptionPane.showMessageDialog(this, "Echec du chargement du planning !");
        }
    }

    public static void main(String[] args) {
        HomeView homeView1 = new HomeView();
    }

}
