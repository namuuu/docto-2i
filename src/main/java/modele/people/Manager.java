package modele.people;

import jakarta.persistence.*;
import modele.HP;
import modele.Person;

@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mid;

    @OneToOne
    private Person person;

    @OneToOne
    private HP hp;

    public Manager() {
    }

    public Manager(Person person, HP hp, String address) {
        this.person = person;
        this.hp = hp;
        this.hp.setAddress(address);
    }

    public int getId() {
        return mid;
    }

    public Person getPerson() {
        return person;
    }

    public HP getHp() {
        return hp;
    }

    public String getAddress() {
        return hp.getAddress();
    }

    public void setAddress(String address) { this.hp.setAddress(address); }

    @Override
    public String toString() {
        return "Manager{" +
                "id=" + mid +
                ", person=" + person +
                ", hp=" + hp +
                '}';
    }
}
