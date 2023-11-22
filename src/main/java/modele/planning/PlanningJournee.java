package modele.planning;

import jakarta.persistence.*;
import modele.RendezVous;
import modele.Salle;
import modele.people.Doctor;
import modele.people.Manager;

import java.util.ArrayList;
import java.util.Date;

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
    private Date date;

    @OneToOne
    private Manager responsibleManager;

    @ManyToMany
    private ArrayList<Doctor> doctors = new ArrayList<>();

    @ManyToMany
    private ArrayList<Manager> managers = new ArrayList<>();

    @ManyToMany
    private ArrayList<Salle> salles = new ArrayList<>();

    @ManyToMany
    private ArrayList<RendezVous> rendezVous = new ArrayList<>();

    public PlanningJournee() {
    }

    public PlanningJournee(Date date, Manager responsibleManager) {
        this.date = date;
        this.responsibleManager = responsibleManager;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Manager getResponsibleManager() {
        return responsibleManager;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public ArrayList<Salle> getSalles() {
        return salles;
    }

    public ArrayList<RendezVous> getRendezVous() {
        return rendezVous;
    }
}
