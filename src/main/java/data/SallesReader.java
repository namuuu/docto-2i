package data;

import modele.Salle;

public class SallesReader {

    public Salle insertLine(String[] data) {
        Salle salle = new Salle(Integer.parseInt(data[1]), data[2]);
        return salle;
    }
}
