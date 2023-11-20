package data;

import modele.Salle;
import modele.people.Manager;
import modele.people.Medecin;

import java.util.ArrayList;

public class ReadData {
    private ArrayList<Manager> managers = new ArrayList<>();
    private ArrayList<Medecin> medecins = new ArrayList<>();
    private ArrayList<Salle> salles = new ArrayList<>();

    public ReadData() {
    }

    public void addManager(Manager manager) {
        managers.add(manager);
    }

    public void addMedecin(Medecin medecin) {
        medecins.add(medecin);
    }

    public void addSalle(Salle salle) {
        salles.add(salle);
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public ArrayList<Medecin> getMedecins() {
        return medecins;
    }

    public ArrayList<Salle> getSalles() {
        return salles;
    }
}
