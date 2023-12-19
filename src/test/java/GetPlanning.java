import jakarta.persistence.*;
import modele.people.Doctor;
import modele.planning.Planning;
import modele.planning.PlanningJournee;

import java.util.List;

public class GetPlanning {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et =em.getTransaction();
            try {
                et.begin();

                Query query = em.createNamedQuery("Planning.getJourneesById");
                query.setParameter("id", 1);

                List<PlanningJournee> list =  query.getResultList();
                for(PlanningJournee pj : list) {
                    System.out.println(pj);
                }

                et.commit();
            } catch (Exception e) {
                System.err.println(e);
                et.rollback();
            }
        } finally {
            if (em != null && em.isOpen())
                em.close();
            if (emf != null && emf.isOpen())
                emf.close();
        }
    }
}
