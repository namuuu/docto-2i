package modele;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    public Person(String firstname, String name, Date birthdate) {
        this.name = name;
        this.firstname = firstname;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
