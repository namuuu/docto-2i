package modele.people;

import jakarta.persistence.*;
import modele.Person;

import java.util.Date;

@Entity
public class Patient extends Person {

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

    public Patient(String firstname, String name, Date birthdate, boolean request_type, Integer identifier) {
        super(firstname, name, birthdate);
        this.request_type = request_type;
        this.identifier = identifier;
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
                "firstname='" + super.getFirstname() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", request_type=" + request_type +
                ", identifier=" + identifier +
                '}';
    }
}
