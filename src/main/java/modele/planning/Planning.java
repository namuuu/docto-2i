package modele.planning;

import jakarta.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Planning.getById",
                query = "SELECT p FROM Planning p where p.id = :id"
        ),
        @NamedQuery(name = "Planning.getJourneesById",
                query = "SELECT pj FROM PlanningJournee pj JOIN pj.planning p WHERE p.id = :id"
        )
})
@Entity
public class Planning {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    public Planning() {
    }

    public int getId() {
        return id;
    }
}
