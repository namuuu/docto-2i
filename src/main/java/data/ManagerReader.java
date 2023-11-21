package data;

import modele.Login;
import modele.Person;
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
        Person person = new Person(data[4], data[3], date); // firstname, name, date
        Login login = new Login(data[1], data[2]); // login, password

        return new Manager(person, login, data[6]);
    }
}