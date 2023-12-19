package modele;

import jakarta.persistence.*;

@Entity
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @Column(
            name = "start_hour"
    )
    private int startHour;

    public Creneau() {
    }

    public Creneau(int startHour) {
        this.startHour = startHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
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
