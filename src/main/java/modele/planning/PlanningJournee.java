package modele.planning;

import jakarta.persistence.*;
import modele.RendezVous;
import modele.Salle;
import modele.people.Doctor;
import modele.people.Manager;

import java.util.*;

@Entity
public class PlanningJournee {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    @Column(
            name = "date"
    )
    private String date;

    @OneToOne
    private Manager responsibleManager;

    @ManyToOne
    private Planning planning;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    private List<Doctor> doctors = new ArrayList<>();

    @ManyToMany
    private List<Manager> managers = new ArrayList<>();

    @ManyToMany
    private List<Salle> salles = new ArrayList<>();

    @OneToMany
    private List<RendezVous> rendezVous = new ArrayList<>();

    public PlanningJournee() {
    }

    public PlanningJournee(String date, Manager responsibleManager, Planning planning, List<Doctor> doctors, List<Manager> managers, List<Salle> salles, List<RendezVous> rendezVous) {
        this.date = date;
        this.responsibleManager = responsibleManager;
        this.planning = planning;
        this.doctors = doctors;
        this.managers = managers;
        this.salles = salles;
        this.rendezVous = rendezVous;

        for(RendezVous rv : rendezVous) {
            rv.setPlanningJournee(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Manager getResponsibleManager() {
        return responsibleManager;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public List<Salle> getSalles() {
        return salles;
    }

    public List<RendezVous> getRendezVous() {
        return rendezVous;
    }

    @Override
    public String toString() {
        return "PlanningJournee{" +
                "id=" + id +
                ", date=" + date +
                ", responsibleManager=" + responsibleManager +
                ", planning=" + planning +
                ", doctors=" + doctors +
                ", managers=" + managers +
                ", salles=" + salles +
                ", rendezVous=" + rendezVous +
                '}';
    }
}
