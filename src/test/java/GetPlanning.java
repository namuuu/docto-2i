import jakarta.persistence.*;
import modele.RendezVous;
import modele.people.Doctor;
import modele.planning.Planning;
import modele.planning.PlanningJournee;

import java.util.List;

public class GetPlanning {

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();

                /*Query query = em.createNamedQuery("Planning.getJourneesById");
                query.setParameter("id", 1);

                List<PlanningJournee> list =  query.getResultList();
                for(PlanningJournee pj : list) {
                    System.out.println(pj);
                }*/

                Query query = em.createNamedQuery("Planning.getJourneeOfDoctorByDate");
                query.setParameter("planningid", 1);
                query.setParameter("date", "20231101");
                query.setParameter("doctorid", 4);

                List<RendezVous> list =  query.getResultList();
                for(RendezVous pj : list) {
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
