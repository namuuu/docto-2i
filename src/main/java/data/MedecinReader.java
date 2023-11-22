package data;

import modele.HP;
import modele.Person;
import modele.people.Doctor;

import java.util.Date;
import java.util.Objects;

public class MedecinReader {

    /**
     * Insert a line from the CSV file into a Doctor object
     * @param data id, login, password, name, firstname, birthdate, address, hpnumber, intern
     * @return new Doctor object
     */
    public Doctor insertLine(String[] data) {
        Date date = new Date(5);
        Person person = new Person(data[4], data[3], date); // firstname, name, date
        HP hp = new HP(data[1], data[2]); // login, password

        return new Doctor(person, hp,
                data[6], Integer.parseInt(data[0]), Objects.equals(data[7], "1"));
    }
}
