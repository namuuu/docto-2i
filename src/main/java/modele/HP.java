package modele;

import jakarta.persistence.*;
import modele.people.Doctor;
import modele.people.Manager;

import java.util.Date;

@NamedQueries({
        @NamedQuery(
                name = "HP.login",
                query = "SELECT hp FROM HP hp WHERE hp.login=:login AND hp.password=:password"
        )
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class HP extends Person {

    @Column(
            name = "login",
            nullable = false,
            unique = true
    )
    private String login;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "address"
    )
    private String address;

    public HP() {
    }

    public HP(String firstname, String name, Date birthdate, String login, String password, String address) {
        super(firstname, name, birthdate);
        this.login = login;
        this.password = password;
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    // Retourne 0 si c'est un docteur, 1 si c'est un manager, -1 si c'est autre chose
    public int isDoctorOrManager() {
        if (this instanceof Doctor) {
            return 0;
        } else if (this instanceof Manager) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Login{" +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
