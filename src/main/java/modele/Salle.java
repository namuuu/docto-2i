package modele;

import jakarta.persistence.*;

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

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Salle{" +
                " numero=" + numero +
                ", nom='" + nom + '\'' +
                '}';
    }
}
