package vuecontrole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modele.HP;
import modele.RendezVous;
import modele.Salle;
import modele.planning.Planning;
import modele.score.Optimizer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class HomeView extends JFrame {
    // Attributs fenêtre
    private JLabel labelDoctorName, rechercheSalle, rechercheDocteur, logo;
    private JPanel panelPlanning, jLabelRechercheSalle, nomDocteur, nomOnglet;
    private JTable tablePlanningGlobal, tablePlanningDocteur, tablePlanningSalle;
    private JButton logoutButton, validerRechercheDocteur, optimiserButton, validerRechercheSalle;
    private JTabbedPane selectionPlanning;
    private JComboBox salleCombo, planningCombo, docteurCombo, comboDate;
    private JScrollPane scrollTableSalle, scrollPaneGlobal, scrollbarDocteur;

    // Attributs de gestion
    private HP hp;
    private EntityManagerFactory emf;
    private EntityManager em;
    private int planningId;
    private String date;
    private String dateFormated;

    // Constructeur par défaut
    public HomeView() {
        this.hp = null;
        tablePlanningSalle.getTableHeader().setVisible(false);

        // Initialisation des composants
        this.initEntityManager();
        setContentPane(panelPlanning);

        // Initialisation des comboBox
        this.initPlanningCombo();
        this.recupererVersionPlanning();
        this.initDateCombo();
        this.initSalleCombo();

        // Récupération de la date
        this.recupererDate();

        // Listener sur les boutons & comboBox
        this.listenerLogout();
        this.listenerRechercheSalle();
        this.listenerRechercheDocteur();
        this.listenerPlanningCombo();
        this.listenerComboDate();
        this.listenerOptimiser();
        this.chargementLogo();
    }
    // Constructeur avec un HP en paramètre
    public HomeView(HP hp) throws HeadlessException {
        this();
        this.hp = hp;
        this.labelDoctorName.setText(hp.getFirstname() + " " + hp.getName());

        // Initialisation des composants en fonction du type de HP
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

    // Initialisation de l'entity manager
    private void initEntityManager() {
        this.emf = Persistence.createEntityManagerFactory("Docto2IPU");
        this.em = emf.createEntityManager();
    }

    // Initialisation de la fenêtre
    private void initialisationFenetre() {
        this.setTitle("DOCTO2I - Planning");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.ORANGE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // Centrer les différents tableaux de l'application (Global, Docteur, Salle)
    private void centerTable() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tablePlanningSalle.setDefaultRenderer(Object.class, centerRenderer);
        tablePlanningDocteur.setDefaultRenderer(Object.class, centerRenderer);
        tablePlanningGlobal.setDefaultRenderer(Object.class, centerRenderer);
    }

    // Re-initialisation des tableaux en fonction des comboBox
    private void reInitTableau() {

        // Récupération de la date
        this.recupererDate();

        // Initialisation des composants en fonction du type de HP
        if(this.hp.isDoctorOrManager() == 0) {
            this.initialisationTableauPlanningDocteur(0);
        } else if (this.hp.isDoctorOrManager() == 1) {
            // Récupération de l'id du docteur sélectionné
            String docteurId = Objects.requireNonNull(docteurCombo.getSelectedItem()).toString();
            String[] docteurIdSplit = docteurId.split(" - ");
            initialisationTableauPlanningDocteur(Integer.parseInt(docteurIdSplit[0]));

            this.initialisationTableauPlanningGlobal();
            this.initialisationTableauPlanningSalle();
        }
    }

    // Récupération de la version du planning sélectionné (ex: PlanningV1 => 1)
    private void recupererVersionPlanning() {
        if(planningCombo.getSelectedItem() == null) return;
        String planning = Objects.requireNonNull(planningCombo.getSelectedItem()).toString();
        String[] planningSplit = planning.split("V");
        this.planningId = Integer.parseInt(planningSplit[1]);
    }

    // Désactivation des composants de recherche de docteur lorsqu'on est un manager
    private void desacRechercheMedecin() {
        rechercheDocteur.setVisible(false);
        docteurCombo.setVisible(false);
        validerRechercheDocteur.setVisible(false);
    }

    // Initialisation des comboBox pour la recherche du planning docteur
    private void initDocteurCombo() {
        Query query = em.createNamedQuery("Doctor.getDoctorsOfDay");
        query.setParameter("planningid", this.planningId);
        query.setParameter("date", this.dateFormated);

        List<HP> docteurs =  query.getResultList();
        for(HP d : docteurs) docteurCombo.addItem(d.getId() + " - " + d.getFirstname() + " " + d.getName());
    }

    // Initialisation de la comboBox pour la mise à jour de date du planning
    private void initDateCombo() {
        Query query = em.createNamedQuery("Planning.getAllDate");
        query.setParameter("planningid", this.planningId);

        List<String> dates =  query.getResultList();
        for(String d : dates)  {
            // Mise en forme de la date sous le format JJ/MM/AAAA
            d = d.substring(6,8) + "/" + d.substring(4,6) + "/" + d.substring(0,4);
            comboDate.addItem(d);
        }
    }

    // Initialisation de la comboBox pour la recherche de salle
    private void initSalleCombo() {
        Query query = em.createNamedQuery("Salle.getAll");

        List<Salle> list =  query.getResultList();
        for(Salle s : list) salleCombo.addItem(s.getNumero() + " - " + s.getNom());
    }

    // Initialisation de la comboBox pour le planning
    private void initPlanningCombo() {
        Query query = em.createNamedQuery("Planning.getAllVersion");

        planningCombo.removeAllItems();

        List<Planning> list =  query.getResultList();
        for(Planning p : list) {
            System.out.println(p.getId());
            planningCombo.addItem("Planning V" + p.getId());
        }
    }

    // Initialisation du tableau du planning global
    private void initialisationTableauPlanningGlobal() {
        tablePlanningGlobal.setEnabled(false);
        tablePlanningGlobal.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");

        this.getPlanningGlobal(model);
        tablePlanningGlobal.getTableHeader().setVisible(false);

        tablePlanningGlobal.setModel(model);
        tablePlanningGlobal.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningGlobal.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    // Initialisation du tableau du planning salle
    private void initialisationTableauPlanningSalle(){
        tablePlanningSalle.setEnabled(false);
        tablePlanningSalle.setRowHeight(50);
        tablePlanningSalle.setTableHeader(null);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");

        this.getPlanningSalle(model);

        tablePlanningSalle.setModel(model);
        tablePlanningSalle.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningSalle.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    // Initialisation du tableau du planning docteur
    private void initialisationTableauPlanningDocteur(int hp){

        tablePlanningDocteur.setEnabled(false);
        tablePlanningDocteur.setRowHeight(50);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Heure");
        model.addColumn("Jour");

        if(hp == 0) {
            this.getPlanningDocteur(model, this.hp.getId());
            this.optimiserButton.setVisible(false);
        } else {
            this.getPlanningDocteur(model, hp);
        }
        tablePlanningDocteur.getTableHeader().setVisible(false);

        tablePlanningDocteur.setModel(model);
        tablePlanningDocteur.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePlanningDocteur.getColumnModel().getColumn(1).setPreferredWidth(750);
    }

    // Requete pour récupérer le planning du docteur
    private void getPlanningDocteur(DefaultTableModel model, int hp) {

        Query query = em.createNamedQuery("Planning.getJourneeOfDoctorByDate");


        query.setParameter("planningid", this.planningId);
        query.setParameter("doctorid", hp);
        query.setParameter("date", this.dateFormated);

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

    // Requete pour récupérer le planning de la salle
    private void getPlanningSalle(DefaultTableModel model) {

        Query query = em.createNamedQuery("Planning.getJourneeOfSalleByDate");
        query.setParameter("planningid", this.planningId);

        String salle = salleCombo.getSelectedItem().toString();
        String[] salleSplit = salle.split(" - ");
        query.setParameter("sallenum", Integer.parseInt(salleSplit[0]));
        query.setParameter("date", this.dateFormated);

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

    // Requete pour récupérer le planning global
    private void getPlanningGlobal(DefaultTableModel model) {

        Query query = em.createNamedQuery("Planning.getAllRdv");
        query.setParameter("planningid", this.planningId);
        query.setParameter("date", this.dateFormated);

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

    // Permet la déconnexion de l'utilisateur connecté et le retour à la page de connexion
    private void listenerLogout() {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login1 = new Login();
                dispose();
            }
        });
    }

    // Permet la validation et le lancement de la requête de recherche de salle
    private void listenerRechercheSalle() {
        validerRechercheSalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialisationTableauPlanningSalle();
            }
        });
    }

    private void listenerOptimiser() {
        optimiserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Optimizer optimizer = new Optimizer();
                optimizer.optimize(planningId);
                initPlanningCombo();
            }
        });
    }

    // Permet la recherche de docteur uniquement si l'utilisateur est connecté en manager
    private void listenerRechercheDocteur() {
        validerRechercheDocteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String docteurId = Objects.requireNonNull(docteurCombo.getSelectedItem()).toString();
                String[] docteurIdSplit = docteurId.split(" - ");
                initialisationTableauPlanningDocteur(Integer.parseInt(docteurIdSplit[0]));
            }
        });
    }

    // Permet la mise à jour du planning en fonction du planning sélectionné
    private void listenerPlanningCombo() {
        planningCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recupererVersionPlanning();
                reInitTableau();
            }
        });
    }

    // Permet la mise à jour du planning en fonction de la date sélectionnée
    private void listenerComboDate() {
        comboDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reInitTableau();
            }
        });
    }

    private void recupererDate() {
        this.date = Objects.requireNonNull(comboDate.getSelectedItem()).toString();
        String[] dateSplit = date.split("/");
        this.dateFormated = dateSplit[2] + dateSplit[1] + dateSplit[0];
  
    private void chargementLogo() {
        // Récupération de l'image depuis le fichier local
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/logo-white.png")));

        // Redimensionnement de l'image
        Image image = logo.getImage();
        Image newimg = image.getScaledInstance(72, 60,  java.awt.Image.SCALE_SMOOTH);
        logo = new ImageIcon(newimg);
        // Centrer l'image
        logo.setImageObserver(this.logo);

        // Ajout de l'image à l'interface graphique
        this.logo.setIcon(logo);
    }

    public static void main(String[] args) {
        HomeView homeView1 = new HomeView();
    }

}
