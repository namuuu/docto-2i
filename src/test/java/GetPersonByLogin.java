import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modele.Person;

public class GetPersonByLogin {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("HP.getByLogin");
        query.setParameter("login", "gasparda");
        Person person = (Person)query.getSingleResult();
        System.out.println(person);
    }
}
