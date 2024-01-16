package modele.planning;

import modele.RendezVous;
import modele.Salle;
import modele.people.Doctor;

import java.util.ArrayList;

/**
 * RDVBricks are existing to simplify the process of Optimizing the different days.
 */
public class RDVBrick {

    private ArrayList<RendezVous> rendezVous = new ArrayList<>();
    private Doctor doctor;
    private boolean specialized = false;
    private int startHour;
    private int endHour = 0;
    private Salle salle;

    public RDVBrick() {
    }

    public ArrayList<RendezVous> getRendezVous() {
        return rendezVous;
    }

    public void addRendezVous(RendezVous rendezVous) {
        this.rendezVous.add(rendezVous);
    }

    public void setRendezVous(ArrayList<RendezVous> rendezVous) {
        this.rendezVous = rendezVous;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public boolean isSpecialized() {
        return specialized;
    }

    public void setSpecialized(boolean specialized) {
        this.specialized = specialized;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    @Override
    public String toString() {
        return "RDVBrick{" +
                "rendezVous=" + rendezVous +
                ", doctor=" + doctor +
                ", specialized=" + specialized +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", salle=" + salle +
                '}';
    }
}
