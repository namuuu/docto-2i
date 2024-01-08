import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modele.HP;

public class GetHP {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        EntityManager em = emf.createEntityManager();

        HP hp = em.createNamedQuery("HP.login", HP.class)
                .setParameter("login", "gasparda")
                .setParameter("password", "a")
                .getSingleResult();

        System.out.println(hp);

        em.close();
        emf.close();
    }
}
