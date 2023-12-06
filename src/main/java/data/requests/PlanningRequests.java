package data.requests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modele.planning.PlanningJournee;

import java.util.ArrayList;
import java.util.List;

public class PlanningRequests {

    public static ArrayList<PlanningJournee> getJourneesById(int id) {
        System.out.println("chapeau chapeau");
        final EntityManager em;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU")) {
            em = emf.createEntityManager();
            Query query = em.createNamedQuery("Planning.getJourneesById");
            query.setParameter("id", id);
            ArrayList<PlanningJournee> pjArray = new ArrayList<>();
            List pjList = query.getResultList();
            PlanningJournee planningJournee = (PlanningJournee) pjList.get(1);
            planningJournee.getDoctors();


            return pjArray;
        }
    }

    public static void main(String[] args) {
        ArrayList<PlanningJournee> pjArray = getJourneesById(1);
//        System.out.println(pjArray.get(0).getDoctors().get(0));
    }
}
