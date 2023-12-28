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
                query = "SELECT pj.rendezVous FROM RendezVous rv JOIN PlanningJournee pj JOIN pj.planning p JOIN Doctor d ON rv.doctor = d " +
                        "WHERE p.id = :planningid " +
                        "AND d.id = :doctorid " +
                        "AND pj.date = :date"
        ),
        @NamedQuery(name = "Planning.getJourneeOfSalleByDate",
                query = "SELECT pj.rendezVous FROM RendezVous rv JOIN PlanningJournee pj JOIN pj.planning p JOIN Salle s ON rv.salle = s " +
                        "WHERE p.id = :planningid " +
                        "AND s.id = :salleid " +
                        "AND pj.date = :date"
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
