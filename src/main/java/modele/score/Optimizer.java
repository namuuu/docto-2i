package modele.score;

import jakarta.persistence.*;
import modele.RendezVous;
import modele.planning.Planning;
import modele.planning.PlanningJournee;

import java.util.ArrayList;

public class Optimizer {

    public void optimize() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();

                Query query = em.createNamedQuery("Planning.getJourneesById");
                query.setParameter("id", 1);

                ArrayList<PlanningJournee> planningJournees = (ArrayList<PlanningJournee>) query.getResultList();

                Planning newPlanning = new Planning();

                for (PlanningJournee planningJournee : planningJournees) {
                    optimizeDay(em, planningJournee, newPlanning);
                }

                em.persist(newPlanning);

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

    private PlanningJournee optimizeDay(EntityManager em, PlanningJournee planningJournee, Planning newPlanning) {
        ArrayList<RendezVous> rendezVous = new ArrayList<>(planningJournee.getRendezVous());

        System.out.println("Journee : " + planningJournee.getDate()
                + " - " + rendezVous.size() + " rendez-vous"
                + " - " + planningJournee.getDoctors().size() + " docteurs"
                + " - " + planningJournee.getSalles().size() + " salles");

        PlanningJournee newPlanningJournee = new PlanningJournee(
                planningJournee.getDate(),
                planningJournee.getResponsibleManager(),
                newPlanning,
                new ArrayList<>(planningJournee.getDoctors()),
                new ArrayList<>(planningJournee.getManagers()),
                new ArrayList<>(planningJournee.getSalles()),
                new ArrayList<>()
        );

        em.persist(newPlanningJournee);
        return newPlanningJournee;
    }
}
