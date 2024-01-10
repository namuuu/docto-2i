package modele.people;

import jakarta.persistence.*;
import modele.Person;

import java.util.Date;

@Entity
public class Patient extends Person {

    @Column(
            name = "request_type"
    )
    private boolean specialized;

    @Column(
            name = "identifier"
    )
    private Integer identifier;

    public Patient() {
    }

    public Patient(String firstname, String name, Date birthdate, boolean request_type, Integer identifier) {
        super(firstname, name, birthdate);
        this.specialized = request_type;
        this.identifier = identifier;
    }

    /**
     * Defines if the request needs a specialized doctor or not
     * @return true if the request needs a specialized doctor, false otherwise
     */
    public boolean isSpecialized() {
        return specialized;
    }

    public void setSpecialized(boolean specialized) {
        this.specialized = specialized;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstname='" + super.getFirstname() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", request_type=" + specialized +
                ", identifier=" + identifier +
                '}';
    }
}
