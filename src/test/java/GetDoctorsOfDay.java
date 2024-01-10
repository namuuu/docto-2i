import jakarta.persistence.*;
import modele.people.Doctor;

import java.util.List;

public class GetDoctorsOfDay {
    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();

        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();

                Query query = em.createNamedQuery("Doctor.getDoctorsOfDay");
                query.setParameter("planningid", 1);
                query.setParameter("date", "20231101");

                List<Doctor> list = query.getResultList();
                for(Doctor m : list) {
                    System.out.println(m);
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
