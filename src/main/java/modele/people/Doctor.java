package modele.people;

import jakarta.persistence.*;
import modele.HP;

import java.util.Date;

@Entity
public class Doctor extends HP {

    @Column(
            name = "health_professional_number"
    )
    private int healthProfessionalNumber;

    @Column(
            name = "intern"
    )
    private boolean intern;

    @Column(
            name = "address"
    )
    private String address;

    public Doctor() {
    }

    public Doctor(String firstname, String name, Date birthdate, String login, String password, String address, int healthProfessionalNumber, boolean intern) {
        super(firstname, name, birthdate, login, password);
        this.healthProfessionalNumber = healthProfessionalNumber;
        this.intern = intern;
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

    public int getHealthProfessionalNumber() {
        return healthProfessionalNumber;
    }

    public void setHealthProfessionalNumber(int healthProfessionalNumber) {
        this.healthProfessionalNumber = healthProfessionalNumber;
    }

    public boolean isIntern() {
        return intern;
    }

    public void setIntern(boolean intern) {
        this.intern = intern;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                ", healthProfessionalNumber=" + healthProfessionalNumber +
                ", intern=" + intern +
                '}';
    }
}
