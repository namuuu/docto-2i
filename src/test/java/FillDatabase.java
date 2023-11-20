import data.DataReader;
import data.ReadData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class FillDatabase {


    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU");
        final EntityManager em = emf.createEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                DataReader dr = new DataReader();
                ReadData readData = dr.readFiles("./src/main/resources/instances");
                dr.importData(em, readData);

                et.commit();
            } catch (Exception e) {
                System.err.println("Caught exception: " + e);
                System.err.println("Applying Rollback...");
                et.rollback();
            }
        } finally {
            if(em != null && em.isOpen())
                em.close();
            if(emf != null && emf.isOpen())
                emf.close();
        }
    }
}
