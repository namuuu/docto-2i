package modele;

import jakarta.persistence.*;

import java.util.Date;

@NamedQueries({
        @NamedQuery(
                name = "HP.login",
                query = "SELECT COUNT(*) AS loginTrue FROM HP WHERE login=:login AND password=:password"
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

    @Override
    public String toString() {
        return "Login{" +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
