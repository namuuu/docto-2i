package data;

import modele.Salle;
import modele.people.Manager;
import modele.people.Doctor;

import java.util.ArrayList;

public class ReadData {
    private final ArrayList<Manager> managers = new ArrayList<>();
    private final ArrayList<Doctor> doctors = new ArrayList<>();
    private final ArrayList<Salle> salles = new ArrayList<>();

    public ReadData() {
    }

    public void addManager(Manager manager) {
        managers.add(manager);
    }

    public void addMedecin(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addSalle(Salle salle) {
        salles.add(salle);
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public ArrayList<Doctor> getMedecins() {
        return doctors;
    }

    public ArrayList<Salle> getSalles() {
        return salles;
    }
}
