package modele.people;

import jakarta.persistence.*;
import modele.Person;

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paid;

    @OneToOne
    private Person person;

    @Column(
            name = "request_type"
    )
    private boolean request_type;

    @Column(
            name = "identifier"
    )
    private Integer identifier;

    public Patient() {
    }

    public Patient(Integer paid, Person person, boolean request_type, Integer identifier) {
        this.paid = paid;
        this.person = person;
        this.request_type = request_type;
        this.identifier = identifier;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isRequest_type() {
        return request_type;
    }

    public void setRequest_type(boolean request_type) {
        this.request_type = request_type;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "ppid=" + paid +
                ", person=" + person +
                ", request_type=" + request_type +
                ", identifier=" + identifier +
                '}';
    }
}
