package vuecontrole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
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

        // Chargement du logo
        // TODO : à corriger
        try {
            System.out.println("test");
            BufferedImage monLogo = ImageIO.read(new File("src/main/resources/images/logo.png"));
            JLabel pic = new JLabel(new ImageIcon(monLogo));
            pic.setPreferredSize(new Dimension(200, 200));
            logo.add(pic, BorderLayout.CENTER);
        } catch (Exception e) {
            // Affichage erreur en cas de problème
            e.printStackTrace();
        }
        InitialisationFenetre();
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initConnexion();
            }
        });
    }

    private void InitialisationFenetre() {
        this.setTitle("DOCTO2I - Login");
        this.setSize(400, 550);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.ORANGE);
        //this.add(panel1);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

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
            JOptionPane.showMessageDialog(null, "Login ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        Login login1 = new Login();
    }
}
