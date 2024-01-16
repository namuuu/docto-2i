package modele;

import jakarta.persistence.*;
import modele.people.Doctor;
import modele.people.Patient;
import modele.planning.PlanningJournee;

@Entity
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rvid;
    @OneToOne
    private Patient patient;
    @OneToOne
    private Creneau creneau;
    @OneToOne
    private Salle salle;
    @OneToOne
    private Doctor doctor;
    @ManyToOne
    private PlanningJournee planningJournee;

    public RendezVous() {
    }

    public RendezVous(Patient patient, Creneau creneau, Salle salle, Doctor doctor) {
        this.patient = patient;
        this.creneau = creneau;
        this.salle = salle;
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public Salle getSalle() {
        return salle;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setPlanningJournee(PlanningJournee planningJournee) {
        this.planningJournee = planningJournee;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "rvid=" + rvid +
                ", patient=" + patient +
                ", creneau=" + creneau +
                ", salle=" + salle +
                ", doctor=" + doctor +
                '}';
    }
}
