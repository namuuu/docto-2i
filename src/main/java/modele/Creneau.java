package modele;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

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

    public Creneau(Integer cid, Timestamp startHour) {
        this.cid = cid;
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
