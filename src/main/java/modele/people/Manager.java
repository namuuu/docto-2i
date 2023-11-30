package modele.people;

import jakarta.persistence.*;
import modele.HP;

import java.util.Date;

@Entity
public class Manager extends HP {

    public Manager() {
    }

    public Manager(String firstname, String name, Date birthdate, String login, String password, String address) {
        super(firstname, name, birthdate, login, password, address);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "firstname='" + super.getFirstname() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", birthdate=" + super.getBirthdate() +
                ", login='" + super.getLogin() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                '}';
    }
}
