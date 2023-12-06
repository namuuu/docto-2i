package data.manager;

import jakarta.persistence.*;

public class HopitalManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public HopitalManager() {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("Docto2IPU")) {
            final EntityManager em = emf.createEntityManager();
            this.em = em;
        }
    }

    public EntityManager getEntityManager() {
        return em;
    }

    private static final HopitalManager hopitalManager = new HopitalManager();

    public static HopitalManager getInstance() {
        return hopitalManager;
    }

    public static void main(String[] args) {

    }
}
