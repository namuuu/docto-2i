import jakarta.persistence.*;
import modele.HP;
import vuecontrole.HomeView;
import vuecontrole.Login;

import javax.swing.*;

public class LoginApp {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        EntityManager em = emf.createEntityManager();

        HP hp = em.createNamedQuery("HP.login", HP.class)
                .setParameter("login", "gasparda")
                .setParameter("password", "AlzaGard")
                .getSingleResult();

        System.out.println(hp);

        em.close();
        emf.close();
    }
}
