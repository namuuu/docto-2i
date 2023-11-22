package modele;

import jakarta.persistence.*;

import java.util.Date;

@MappedSuperclass
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

    public HP(String firstname, String name, Date birthdate, String login, String password) {
        super(firstname, name, birthdate);
        this.login = login;
        this.password = password;
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
