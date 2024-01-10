package modele.people;

import jakarta.persistence.*;
import modele.HP;

import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Doctor.getAllDoctors",
                query = "SELECT d FROM Doctor d"
        ),
        @NamedQuery(
                name="Doctor.getDoctorsOfDay",
                query = "SELECT d FROM Doctor d JOIN FETCH PlanningJournee pj JOIN FETCH Planning p " +
                        "WHERE pj.planning.id = :planningid " +
                        "AND pj.date = :date"
        )
})
public class Doctor extends HP {



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

    public Doctor(String firstname, String name, Date birthdate, String login, String password, String address, int healthProfessionalNumber, boolean intern) {
        super(firstname, name, birthdate, login, password, address);
        this.healthProfessionalNumber = healthProfessionalNumber;
        this.intern = intern;
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
                "firstname='" + super.getFirstname() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", id=" + super.getId() +
                ", healthProfessionalNumber=" + healthProfessionalNumber +
                ", intern=" + intern +
                '}';
    }
}
