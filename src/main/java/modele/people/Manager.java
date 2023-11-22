package modele.people;

import jakarta.persistence.*;
import modele.HP;

import java.util.Date;

@Entity
public class Manager extends HP {

    @Column(
            name = "address"
    )
    private String address;

    public Manager() {
    }

    public Manager(String firstname, String name, Date birthdate, String login, String password, String address) {
        super(firstname, name, birthdate, login, password);
        this.address = address;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Manager{" +
                '}';
    }
}
