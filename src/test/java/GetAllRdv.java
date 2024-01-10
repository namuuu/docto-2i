import jakarta.persistence.*;
import modele.RendezVous;

import java.util.List;

public class GetAllRdv {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();

                Query query = em.createNamedQuery("Planning.getAllRdv");
                query.setParameter("planningid", 1);
                query.setParameter("date", "20231226");

                List<RendezVous> list =  query.getResultList();
                for(RendezVous r : list) {
                    System.out.println(r);
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
