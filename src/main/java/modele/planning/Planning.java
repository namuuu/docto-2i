package modele.planning;

import jakarta.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Planning.getById",
                query = "SELECT p FROM Planning p where p.id = :id"
        ),
        @NamedQuery(name = "Planning.getJourneesById",
                query = "SELECT pj FROM PlanningJournee pj JOIN pj.planning p WHERE p.id = :id"
        ),
        @NamedQuery(name = "Planning.getJourneeOfDoctorByDate",
                query = "SELECT rv FROM RendezVous rv JOIN FETCH PlanningJournee pj JOIN FETCH Doctor d ON rv.doctor = d JOIN FETCH Planning p " +
                        "WHERE rv.planningJournee.planning.id = :planningid " +
                        "AND rv.doctor.id = :doctorid " +
                        "AND rv.planningJournee.date = :date"
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

    @Override
    public String toString() {
        return "Planning{" +
                "id=" + id +
                '}';
    }
}
