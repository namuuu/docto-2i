package modele;

import jakarta.persistence.*;
import modele.people.Doctor;
import modele.people.Patient;

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

    public RendezVous() {
    }

    public RendezVous(Patient patient, Creneau creneau, Salle salle, Doctor doctor) {
        this.patient = patient;
        this.creneau = creneau;
        this.salle = salle;
        this.doctor = doctor;
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
