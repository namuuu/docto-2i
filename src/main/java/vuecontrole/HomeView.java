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
import java.util.*;
import java.util.List;

public class HomeView extends JFrame {
    /** Attributs fenêtre **/
    private JLabel labelDoctorName, labelDate, rechercheSalle, rechercheDocteur, labelNameApp;
    private JPanel panelPlanning, jLabelRechercheSalle, nomDocteur, nomOnglet;
    private JTable tablePlanningGlobal, tablePlanningDocteur, tablePlanningSalle;
    private JButton logoutButton, validerRechercheDocteur, optimiserButton, validerRechercheSalle;
    private JTabbedPane selectionPlanning;
    private JComboBox salleCombo, planningCombo, docteurCombo;
    private JScrollPane scrollTableSalle;

    /** Attribut de gestion **/
    private HP hp;
    private String date = "20231226";
    private EntityManagerFactory emf;
    private EntityManager em;
    private int planningId;

    // Constructeur par défaut
    public HomeView() {
        this.initEntityManager();
        String date = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRENCH).format(new Date());
        this.hp = null;
        this.labelDate.setText(date);
        setContentPane(panelPlanning);

        this.initSalleCombo();
        this.initPlanningCombo();
        this.recupererVersionPlanning();

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login1 = new Login();
                dispose();
            }
        });
        validerRechercheSalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialisationTableauPlanningSalle();
            }
        });

        validerRechercheDocteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String docteurId = Objects.requireNonNull(docteurCombo.getSelectedItem()).toString();
                String[] docteurIdSplit = docteurId.split(" - ");
                initialisationTableauPlanningDocteur(Integer.parseInt(docteurIdSplit[0]));
            }
        });

        planningCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recupererVersionPlanning();
                reinitTableau();
            }
        });

    }

    public HomeView(HP hp) throws HeadlessException {
        this();
        this.hp = hp;
        this.labelDoctorName.setText(hp.getFirstname() + " " + hp.getName());
        if(this.hp.isDoctorOrManager() == 0) {
            this.selectionPlanning.remove(0);
            this.selectionPlanning.remove(1);
            this.initialisationTableauPlanningDocteur(0);
            this.desacRechercheMedecin();
        } else if (this.hp.isDoctorOrManager() == 1) {
            this.initialisationTableauPlanningGlobal();
            this.initDocteurCombo();
        }
        this.initialisationFenetre();
        this.centerTable();
    }

    private void reinitTableau() {
        if(this.hp.isDoctorOrManager() == 0) {
            this.initialisationTableauPlanningDocteur(0);
        } else if (this.hp.isDoctorOrManager() == 1) {
            this.initialisationTableauPlanningGlobal();
        }
    }

    private void recupererVersionPlanning() {
        // Récupérer la string sélectionné (ex:"PlanningVX") et ne garder que la valeur X
        String planning = Objects.requireNonNull(planningCombo.getSelectedItem()).toString();
        String[] planningSplit = planning.split("V");
        this.planningId = Integer.parseInt(planningSplit[1]);
        System.out.println(this.planningId);
    }

    private void initEntityManager() {
        this.emf = Persistence.createEntityManagerFactory("Docto2IPU");
        this.em = emf.createEntityManager();
    }

    private void centerTable() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tablePlanningSalle.setDefaultRenderer(Object.class, centerRenderer);
        tablePlanningDocteur.setDefaultRenderer(Object.class, centerRenderer);
        tablePlanningGlobal.setDefaultRenderer(Object.class, centerRenderer);
    }

    private void desacRechercheMedecin() {
        rechercheDocteur.setVisible(false);
        docteurCombo.setVisible(false);
        validerRechercheDocteur.setVisible(false);
    }

    private void initDocteurCombo() {

            Query query = em.createNamedQuery("Doctor.getDoctorsOfDay");
            query.setParameter("planningid", this.planningId);
            query.setParameter("date", date);

            List<HP> docteurs =  query.getResultList();
            for(HP d : docteurs) {
                docteurCombo.addItem(d.getId() + " - " + d.getFirstname() + " " + d.getName());
            }
    }

    private void initSalleCombo() {
        Query query = em.createNamedQuery("Salle.getAll");

        List<Salle> list =  query.getResultList();
        for(Salle s : list) {
            salleCombo.addItem(s.getNumero() + " - " + s.getNom());
        }
    }

    private void initPlanningCombo() {
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

        this.getPlanningGlobal(model, LocalDate.now());

        tablePlanningGlobal.setModel(model);
        tablePlanningGlobal.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningGlobal.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    private void initialisationTableauPlanningSalle(){
        tablePlanningSalle.setEnabled(false);
        tablePlanningSalle.setRowHeight(50);
        tablePlanningSalle.setTableHeader(null);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");

        this.getPlanningSalle(model, LocalDate.now());

        tablePlanningSalle.setModel(model);
        tablePlanningSalle.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningSalle.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    private void initialisationTableauPlanningDocteur(int hp){

        tablePlanningDocteur.setEnabled(false);
        tablePlanningDocteur.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");

        if(hp == 0) {
            this.getPlanningDocteur(model, this.hp.getId(), LocalDate.now());
        } else {
            this.getPlanningDocteur(model, hp, LocalDate.now());
        }

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

    private void getPlanningDocteur(DefaultTableModel model, int hp, LocalDate date) {

        Query query = em.createNamedQuery("Planning.getJourneeOfDoctorByDate");


        query.setParameter("planningid", this.planningId);
        query.setParameter("doctorid", hp);
        query.setParameter("date", this.date);

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

        Query query = em.createNamedQuery("Planning.getJourneeOfSalleByDate");

        query.setParameter("planningid", this.planningId);

        // Récupérer l'élement sélectionné dans la combobox
        String salle = salleCombo.getSelectedItem().toString();
        String[] salleSplit = salle.split(" - ");
        query.setParameter("sallenum", Integer.parseInt(salleSplit[0]));
        //query.setParameter("date", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        query.setParameter("date", this.date);

        List<RendezVous> planningSalle = query.getResultList();
        planningSalle.sort(Comparator.comparing(rv -> rv.getCreneau().getStartHour()));

        if(planningSalle != null && planningSalle.toArray().length > 0) {
            model.addRow(new Object[]{"Heure", "Rendez-vous"});
            for (RendezVous rv : planningSalle) {
                model.addRow(new Object[]{rv.getCreneau().getStartHour() + "h00",
                        " Patient " + rv.getPatient().getFirstname() + " " + rv.getPatient().getName()
                        + " - Salle n°" + rv.getSalle().getNumero() + " - " + rv.getSalle().getNom()
                        + " - Docteur " + rv.getDoctor().getFirstname() + " " + rv.getDoctor().getName()
                });
            }
        } else if(planningSalle.toArray().length == 0) {
            model.addRow(new Object[]{"Heure", "Aucun rendez-vous"});
        } else {
            JOptionPane.showMessageDialog(this, "Echec du chargement du planning !");
        }
    }

    private void getPlanningGlobal(DefaultTableModel model, LocalDate date) {

        Query query = em.createNamedQuery("Planning.getAllRdv");
        query.setParameter("planningid", this.planningId);
        query.setParameter("date", this.date);

        List<RendezVous> planningGlobal =  query.getResultList();
        planningGlobal.sort(Comparator.comparing(rv -> rv.getCreneau().getStartHour()));
        if(planningGlobal.toArray().length == 0) {
            model.addRow(new Object[]{"Heure", "Aucun rendez-vous"});
        } else {
            model.addRow(new Object[]{"Heure", "Rendez-vous"});
            for(RendezVous r : planningGlobal) {
                model.addRow(new Object[]{r.getCreneau().getStartHour() + "h00",
                        " Patient " + r.getPatient().getFirstname() + " " + r.getPatient().getName()
                                + " - Salle n°" + r.getSalle().getNumero() + " - " + r.getSalle().getNom()
                                + " - Docteur " + r.getDoctor().getFirstname() + " " + r.getDoctor().getName()
                });
            }
        }
    }

    public static void main(String[] args) {
        HomeView homeView1 = new HomeView();
    }

}
