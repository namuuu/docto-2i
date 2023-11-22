package data;

import modele.HP;
import modele.Person;
import modele.people.Medecin;

import java.util.Date;
import java.util.Objects;

public class MedecinReader {

    /**
     * Insert a line from the CSV file into a Medecin object
     * @param data id, login, password, name, firstname, birthdate, address, hpnumber, intern
     * @return
     */
    public Medecin insertLine(String[] data) {
        Date date = new Date(5);
        Person person = new Person(data[4], data[3], date); // firstname, name, date
        HP hp = new HP(data[1], data[2]); // login, password

        Medecin medecin = new Medecin(person, hp,
                data[6], Integer.parseInt(data[0]), Objects.equals(data[7], "1")); // person, login, address, hpnumber, intern

        return medecin;
    }
}
