package modele.score;

import jakarta.persistence.*;
import modele.RendezVous;
import modele.Salle;
import modele.people.Doctor;
import modele.planning.Planning;
import modele.planning.PlanningJournee;
import modele.planning.RDVBrick;

import java.util.ArrayList;

public class Optimizer {

    private OptimizerUtil util = new OptimizerUtil();

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

                //optimizeDay(em, planningJournees.get(0), newPlanning);

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
        ArrayList<RendezVous> rendezVousTBD = new ArrayList<>(planningJournee.getRendezVous());
        rendezVousTBD.sort((o1, o2) -> o2.getCreneau().getStartHour() - o1.getCreneau().getStartHour());
        System.out.println("Journee : " + planningJournee.getDate()
                + " - " + rendezVousTBD.size() + " rendez-vous"
                + " - " + planningJournee.getDoctors().size() + " docteurs"
                + " - " + planningJournee.getSalles().size() + " salles");

        ArrayList<RDVBrick> rdvBricks = new ArrayList<>();

        while(!rendezVousTBD.isEmpty()) {
            RDVBrick brick = new RDVBrick();
            util.fillRDVBricks(brick, rendezVousTBD);
            rdvBricks.add(brick);
        }

        System.out.println("Bricks : " + rdvBricks.size());

        for(RDVBrick brick : rdvBricks) {
            Doctor bestDoctor = util.findFirstDoctorAvailable(rdvBricks, planningJournee.getDoctors(), brick);

            if(bestDoctor == null) {
                System.out.println("\tNo doctor available for this brick");
                System.out.println("\tBrick : " + brick.getRendezVous().size() + " rendez-vous"
                        + " - " + brick.getStartHour() + "h - " + brick.getEndHour() + "h"
                        + " - Spécialisation : " + brick.isSpecialized());
                for(Doctor doc : planningJournee.getDoctors()) {
                    System.out.println("\tDoctor : " + doc.getFirstname() + " " + doc.getName()
                            + " - " + doc.isIntern()
                            + " - " + util.getDoctorLastHour(rdvBricks, doc) + "h");
                }
            }

            brick.setDoctor(bestDoctor);

            Salle bestSalle = util.findFirstSalleAvailable(rdvBricks, planningJournee.getSalles(), brick);

            if(bestSalle == null) {
                System.out.println("\tNo salle available for this brick");
                System.out.println("\tBrick : " + brick.getRendezVous().size() + " rendez-vous"
                        + " - " + brick.getStartHour() + "h - " + brick.getEndHour() + "h"
                        + " - Spécialisation : " + brick.isSpecialized());
                for(Salle salle : planningJournee.getSalles()) {
                    System.out.println("\tSalle: " + salle.getNumero()
                            + " - " + salle.getNom()
                            + " - " + util.getSallesLastHour(rdvBricks, salle) + " h");
                }
            }

            brick.setSalle(bestSalle);

            System.out.println("Brick : " + brick.getRendezVous().size() + " rendez-vous"
                    + " - " + brick.getStartHour() + "h - " + brick.getEndHour() + "h"
                    + " - Spécialisation : " + brick.isSpecialized()
                    + " - Docteur : " + brick.getDoctor().getFirstname() + " " + brick.getDoctor().getName()
                    + " - Salle : " + brick.getSalle().getNumero());
        }

        ArrayList<RendezVous> rendezVousToImport = new ArrayList<>();

        for(RDVBrick brick : rdvBricks) {
            for(RendezVous rendezVous : brick.getRendezVous()) {
                rendezVousToImport.add(
                        new RendezVous(
                                rendezVous.getPatient(),
                                rendezVous.getCreneau(),
                                brick.getSalle(),
                                brick.getDoctor()
                        )
                );
            }
        }

        PlanningJournee newPlanningJournee = new PlanningJournee(
                planningJournee.getDate(),
                planningJournee.getResponsibleManager(),
                newPlanning,
                new ArrayList<>(planningJournee.getDoctors()),
                new ArrayList<>(planningJournee.getManagers()),
                new ArrayList<>(planningJournee.getSalles()),
                rendezVousToImport
        );

        em.persist(newPlanningJournee);
        return newPlanningJournee;
    }

    public static void main(String[] args) {
        Optimizer optimizer = new Optimizer();
        optimizer.optimize();
    }
}
