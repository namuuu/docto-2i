package modele;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rvid;
    @OneToOne
    private Integer peid;
    @OneToOne
    private Integer cid;
    @OneToOne
    private Integer numeroSalle;
    @OneToOne
    private Integer did;

    public RendezVous() {
    }

    public RendezVous(Integer rvid, Integer pid, Integer cid, Integer numeroSalle, Integer did) {
        this.rvid = rvid;
        this.peid = pid;
        this.cid = cid;
        this.numeroSalle = numeroSalle;
        this.did = did;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "rvid=" + rvid +
                ", pid=" + peid +
                ", cid=" + cid +
                ", numeroSalle=" + numeroSalle +
                ", did=" + did +
                '}';
    }
}
