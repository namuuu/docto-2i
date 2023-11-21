package modele.people;

import jakarta.persistence.*;
import modele.Login;
import modele.Person;

@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Person person;

    @OneToOne
    private Login login;

    @Column(
            name = "address"
    )
    private String address;

    public Manager() {
    }

    public Manager(Person person, Login login, String address) {
        this.person = person;
        this.login = login;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Login getLogin() {
        return login;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", person=" + person +
                ", login=" + login +
                '}';
    }
}
