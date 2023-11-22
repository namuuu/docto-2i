package data;

import modele.people.Doctor;

import java.util.Date;
import java.util.Objects;

public class DoctorReader {

    /**
     * Insert a line from the CSV file into a Doctor object
     * @param data id, login, password, name, firstname, birthdate, address, hpnumber, intern
     * @return new Doctor object
     */
    public Doctor insertLine(String[] data) {
        Date date = new Date(5);

        return new Doctor(data[3], data[4], date, data[1], data[2], data[6], Integer.parseInt(data[0]), Objects.equals(data[7], "1"));
    }
}
