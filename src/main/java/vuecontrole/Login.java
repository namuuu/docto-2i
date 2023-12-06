package vuecontrole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPasswordField passwordField;
    private JPanel panelLogin;
    private JTextField loginTextField;
    private JLabel pageName;
    private JButton buttonSubmit;

    public Login() throws HeadlessException {
        setContentPane(panelLogin);
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
        if(this.loginTextField.getText().equals("admin") && this.passwordField.getText().equals("admin")) {
            //JOptionPane.showMessageDialog(this, "Connexion réussie !");
            Planning planning1 = new Planning();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Connexion échouée !");
        }
    }

    public static void main(String[] args) {
        Login login1 = new Login();
    }
}
