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
        ),
        @NamedQuery(name = "Planning.getJourneeOfSalleByDate",
                query = "SELECT rv FROM RendezVous rv JOIN FETCH PlanningJournee pj JOIN FETCH Salle s ON rv.salle = s JOIN FETCH Planning p " +
                        "WHERE rv.planningJournee.planning.id = :planningid " +
                        "AND rv.salle.numero = :sallenum " +
                        "AND rv.planningJournee.date = :date"
        ),
        @NamedQuery(name = "Planning.getAllVersion",
                query = "SELECT p FROM Planning p"
        ),
        @NamedQuery(name = "Planning.getSalleLibre",
                query = "SELECT s FROM Salle s WHERE s.numero " +
                        "NOT IN (SELECT rv.salle.numero " +
                                "FROM RendezVous rv JOIN PlanningJournee pj " +
                                "JOIN pj.planning p " +
                                "JOIN rv.creneau c " +
                                "WHERE p.id = :planningid AND pj.date = :date AND c.startHour = :starthour )"
        ),
        @NamedQuery(name = "Planning.getAllRdv",
                query = "SELECT rv FROM RendezVous rv JOIN FETCH PlanningJournee pj JOIN FETCH Planning p " +
                        "WHERE rv.planningJournee.planning.id = :planningid " +
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
