package modele;

import jakarta.persistence.*;

@Entity
public class HP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hpid;

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

    public HP(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return hpid;
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
                "id=" + hpid +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
