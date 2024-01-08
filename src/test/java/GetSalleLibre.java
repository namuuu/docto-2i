import jakarta.persistence.*;
import modele.Salle;

import java.util.List;

public class GetSalleLibre {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();

                Query query = em.createNamedQuery("Planning.getSalleLibre");
                query.setParameter("planningid", 1);
                query.setParameter("date", "20231101");
                query.setParameter("starthour", 23);

                List<Salle> list =  query.getResultList();
                for(Salle s : list) {
                    System.out.println(s);
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
