package modele;

import jakarta.persistence.*;


@NamedQuery(name = "Salle.getAll",
        query = "SELECT s FROM Salle s"
)

@Entity
public class Salle {

    @Id
    private int numero;

    @Column(
            name = "name"
    )
    private String nom;

    public Salle() {
    }

    public Salle(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "numero=" + numero +
                ", nom='" + nom + '\'' +
                '}';
    }
}
