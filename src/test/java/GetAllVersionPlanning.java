import jakarta.persistence.*;
import modele.planning.Planning;

import java.util.List;

public class GetAllVersionPlanning {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();

                Query query = em.createNamedQuery("Planning.getAllVersion");

                List<Planning> list =  query.getResultList();
                for(Planning p : list) {
                    System.out.println(p);
                }

                et.commit();
            } catch (Exception e) {
                et.rollback();
                throw new RuntimeException(e);
            }
        } finally {
            if (em != null && em.isOpen())
                em.close();
            if (emf != null && emf.isOpen())
                emf.close();
        }
    }
}
