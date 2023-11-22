package modele.people;

import jakarta.persistence.*;
import modele.HP;
import modele.Person;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int did;

    @OneToOne
    private Person person;

    @OneToOne
    private HP hp;

    @Column(
            name = "health_professional_number"
    )
    private int healthProfessionalNumber;

    @Column(
            name = "intern"
    )
    private boolean intern;

    public Doctor() {
    }

    public Doctor(Person person, HP hp, String address, int healthProfessionalNumber, boolean intern) {
        this.person = person;
        this.hp = hp;
        this.hp.setAddress(address);
        this.healthProfessionalNumber = healthProfessionalNumber;
        this.intern = intern;
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

    public void setAddress(String address) {
        this.hp.setAddress(address);
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
                "id=" + did +
                ", person=" + person +
                ", hp=" + hp +
                ", address='" + hp.getAddress() + '\'' +
                ", healthProfessionalNumber=" + healthProfessionalNumber +
                ", intern=" + intern +
                '}';
    }
}
