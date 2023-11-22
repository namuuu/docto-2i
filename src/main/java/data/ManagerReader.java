package data;

import modele.people.Manager;

import java.util.Date;

public class ManagerReader {

    /**
     * Insert a line from the CSV file into a Manager object
     * @param data id, login, password, name, firstname, birthdate, service
     * @return
     */
    public Manager insertLine(String[] data) {
        Date date = new Date(data[5]);

        return new Manager(data[3], data[4], date, data[1], data[2], data[6]);
    }
}
