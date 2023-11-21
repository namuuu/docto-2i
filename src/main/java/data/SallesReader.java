package data;

import modele.Salle;

public class SallesReader {

    /**
     * Insert a line from the CSV file into a Salle object
     * @param data id, number, name
     * @return Salle
     */
    public Salle insertLine(String[] data) {
        Salle salle = new Salle(Integer.parseInt(data[1]), data[2]);
        return salle;
    }
}
