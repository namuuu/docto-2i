package vuecontrole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modele.HP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Login extends JFrame {
    /** Attributs Fenêtre **/
    private JPasswordField passwordField;
    private JPanel panelLogin;
    private JTextField loginTextField;
    private JButton buttonSubmit;
    private JLabel labelLogin, pageName, logo, labelPassword;


    public Login() throws HeadlessException {
        setContentPane(panelLogin);
        initialisationFenetre();
        chargementLogo();
        listenerConnexion();
    }

    private void chargementLogo() {
        // Récupération de l'image depuis le fichier local
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/logo.png")));

        // Redimensionnement de l'image
        Image image = logo.getImage();
        Image newimg = image.getScaledInstance(144, 120,  java.awt.Image.SCALE_SMOOTH);
        logo = new ImageIcon(newimg);
        // Centrer l'image
        logo.setImageObserver(this.logo);

        // Ajout de l'image à l'interface graphique
        this.logo.setIcon(logo);
    }


    // Requete de connexion de l'utilisateur
    private void initConnexion() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            HP hp = em.createNamedQuery("HP.login", HP.class)
                    .setParameter("login", loginTextField.getText())
                    .setParameter("password", passwordField.getText())
                    .getSingleResult();
            if(hp != null) {
                HomeView homeView1 = new HomeView(hp);
                this.dispose();
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Login ou mot de passe incorrect",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    // Initialisation de la fenêtre
    private void initialisationFenetre() {
        this.setTitle("DOCTO2I - Login");
        this.setSize(400, 550);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // Ajout d'un Listener sur le bouton de validation de connexion
    private void listenerConnexion() {
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initConnexion();
            }
        });
    }

    public static void main(String[] args) {
        Login login1 = new Login();
    }
}
