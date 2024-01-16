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

    public int oldScore = 0;
    public int newScore = 0;
    public int finalScore = 0;

    public Score(int planningid) {
        oldScore = calculateScore(planningid);
    }

    public int calculateScore(int planningid) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et =em.getTransaction();
            try {
                et.begin();

                int score = 0;

                List<PlanningJournee> pjList = em.createNamedQuery("Planning.getJourneesById", PlanningJournee.class)
                        .setParameter("id", planningid)
                        .getResultList();

                for(PlanningJournee pj : pjList) {
                    for(Doctor doc : pj.getDoctors()) {
                        List<RendezVous> rvList = em.createNamedQuery("Planning.getJourneeOfDoctorByDate", RendezVous.class)
                                .setParameter("planningid", planningid)
                                .setParameter("doctorid", doc.getId())
                                .setParameter("date", pj.getDate())
                                .getResultList();

                        for(int i = 0; i < rvList.size(); i++) {
                            if(i == 0)
                                continue;

                            // S'il y a un trou entre ce créneau et le précédent
                            // Cela calcule à la fois tm et vm
                            if(rvList.get(i).getCreneau().getStartHour() - rvList.get(i-1).getCreneau().getStartHour() > 1) {
                                score += rvList.get(i).getCreneau().getStartHour() - rvList.get(i-1).getCreneau().getStartHour();
                            }

                            // S'ily a changement de salle
                            if(!rvList.get(i).getSalle().equals(rvList.get(i-1).getSalle())) {
                                score += 1;
                            }
                        }
                    }
                }

                et.commit();

                return score;
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

    public static void main(String[] args) {
        Score score = new Score(1);
        Optimizer optimizer = new Optimizer();
        optimizer.optimize(2);
        score.newScore = score.calculateScore(2);
        score.finalScore = score.newScore - score.oldScore;
    }
}
