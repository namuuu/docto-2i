import jakarta.persistence.*;
import modele.RendezVous;
import modele.planning.PlanningJournee;

import java.util.List;

public class GetDoctorPlanning {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();

                Query query = em.createNamedQuery("Planning.getJourneeOfDoctorByDate");
                query.setParameter("planningid", 1);
                query.setParameter("doctorid", 4);
                query.setParameter("date", "20231101");

                List<RendezVous> list = query.getResultList();
                for(RendezVous rv : list) {
                    System.out.println(rv);
                }
//                List<PlanningJournee> list =  query.getResultList();
//                for(PlanningJournee rv : list) {
//                    System.out.println(rv);
//                }

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
