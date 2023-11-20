package modele.people;

import jakarta.persistence.*;
import modele.Login;
import modele.Person;

@Entity
public class Medecin {

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

    @Column(
            name = "health_professional_number"
    )
    private int healthProfessionalNumber;

    @Column(
            name = "intern"
    )
    private boolean intern;

    public Medecin() {
    }

    public Medecin(Person person, Login login, String address, int healthProfessionalNumber, boolean intern) {
        this.person = person;
        this.login = login;
        this.address = address;
        this.healthProfessionalNumber = healthProfessionalNumber;
        this.intern = intern;
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
        return "Medecin{" +
                "id=" + id +
                ", person=" + person +
                ", login=" + login +
                ", address='" + address + '\'' +
                ", healthProfessionalNumber=" + healthProfessionalNumber +
                ", intern=" + intern +
                '}';
    }
}
