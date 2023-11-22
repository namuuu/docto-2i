package modele;

import jakarta.persistence.*;
import modele.people.Manager;

import java.util.Date;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int peid;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "firstname",
            nullable = false
    )
    private String firstname;

    @Column(
            name = "birthdate",
            nullable = false
    )
    private Date birthdate;

    public Person() {
    }

    public Person(String name, String firstname, Date birthdate) {
        this.name = name;
        this.firstname = firstname;
        this.birthdate = birthdate;
    }

    public int getId() {
        return peid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + peid +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
