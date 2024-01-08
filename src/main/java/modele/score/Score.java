package modele.score;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.RendezVous;
import modele.people.Doctor;
import modele.planning.Planning;
import modele.planning.PlanningJournee;

import java.util.List;

public class Score {

    private Planning planning;

    private Planning oldPlanning = null;

    private int scoreInitial = 0;

    private int scoreFinal = 0;

    /**
     * Creer le score d'un planning sans comparaison (généralement le planning initial)
     * @param planning
     */
    public Score(Planning planning) {
        this.planning = planning;
    }

    public Score(int planningid) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et =em.getTransaction();
            try {
                et.begin();

                Planning planning = em.createNamedQuery("Planning.getById", Planning.class)
                        .setParameter("id", planningid)
                        .getSingleResult();

                this.planning = planning;

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

    /**
     * Creer le score d'un planning avec comparaison (généralement le planning final)
     * @param planning
     * @param oldPlanning
     */
    public Score(Planning planning, Planning oldPlanning) {
        this.planning = planning;
        this.oldPlanning = oldPlanning;
    }

    public void calculateInitialScore() {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et =em.getTransaction();
            try {
                et.begin();

                List<PlanningJournee> pjList = em.createNamedQuery("Planning.getJourneesById", PlanningJournee.class)
                        .setParameter("id", planning.getId())
                        .getResultList();

                for(PlanningJournee pj : pjList) {
                    for(Doctor doc : pj.getDoctors()) {
                        List<RendezVous> rvList = em.createNamedQuery("Planning.getJourneeOfDoctorByDate", RendezVous.class)
                                .setParameter("planningid", planning.getId())
                                .setParameter("doctorid", doc.getId())
                                .setParameter("date", pj.getDate())
                                .getResultList();

                        for(int i = 0; i < rvList.size(); i++) {
                            if(i == 0)
                                continue;

                            // S'il y a un trou entre ce créneau et le précédent
                            // Cela calcule à la fois tm et vm
                            if(rvList.get(i).getCreneau().getStartHour() - rvList.get(i-1).getCreneau().getStartHour() > 1) {
                                scoreInitial += rvList.get(i).getCreneau().getStartHour() - rvList.get(i-1).getCreneau().getStartHour();
                            }

                            // S'ily a changement de salle
                            if(!rvList.get(i).getSalle().equals(rvList.get(i-1).getSalle())) {
                                scoreInitial += 1;
                            }
                        }
                    }
                }

                System.out.println("Score initial : " + scoreInitial);

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


    public Planning getPlanning() {
        return planning;
    }

    public Planning getOldPlanning() {
        return oldPlanning;
    }
}
