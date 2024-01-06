package vuecontrole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modele.HP;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Login extends JFrame {
    private JPasswordField passwordField;
    private JPanel panelLogin;
    private JTextField loginTextField;
    private JLabel pageName;
    private JButton buttonSubmit;
    private JLabel labelLogin;
    private JLabel labelPassword;
    private JLabel logo;

    public Login() throws HeadlessException {
        setContentPane(panelLogin);
        initialisationFenetre();
        chargementLogo();
        listenerConnexion();
    }

    // Chargement du logo visible sur la page de login
    private void chargementLogo() {
        try {
            BufferedImage monLogo = ImageIO.read(new File("src/main/resources/images/logo.png"));
            JLabel pic = new JLabel(new ImageIcon(monLogo));
            pic.setPreferredSize(new Dimension(200, 200));
            logo.add(pic, BorderLayout.CENTER);
        } catch (Exception e) {
            // Affichage erreur en cas de problème
            e.printStackTrace();
        }
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
                System.out.println("hp :" + hp);
                HomeView homeView1 = new HomeView(hp);
                this.dispose();
            }
        } catch(Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Login ou mot de passe incorrect",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Initialisation de la fenêtre
    private void initialisationFenetre() {
        this.setTitle("DOCTO2I - Login");
        this.setSize(400, 550);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.ORANGE);
        //this.add(panel1);
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
