package modele;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @Column(
            name = "start_hour"
    )
    private Timestamp startHour;

    public Creneau() {
    }

    public Creneau(Timestamp startHour) {
        this.startHour = startHour;
    }

    public Timestamp getStartHour() {
        return startHour;
    }

    public void setStartHour(Timestamp startHour) {
        this.startHour = startHour;
    }

    @Override
    public String toString() {
        return "Creneau{" +
                "cid=" + cid +
                ", startHour=" + startHour +
                '}';
    }
}
