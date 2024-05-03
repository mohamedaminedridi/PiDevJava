package entities;

import java.sql.Date;

public class Voyage {

    int id,propriete_id,nbr_personne;
    String  image,description,title;
    double prixtotal;

    Date datedebut,datefin;

    public Voyage(int id, int propriete_id, int nbr_personne, String image, String description, String title, double prixtotal, Date datedebut, Date datefin) {
        this.id = id;
        this.propriete_id = propriete_id;
        this.nbr_personne = nbr_personne;
        this.image = image;
        this.description = description;
        this.title = title;
        this.prixtotal = prixtotal;
        this.datedebut = datedebut;
        this.datefin = datefin;
    }

    public Voyage(int propriete_id, int nbr_personne, String image, String description, String title, double prixtotal, Date datedebut, Date datefin) {
        this.propriete_id = propriete_id;
        this.nbr_personne = nbr_personne;
        this.image = image;
        this.description = description;
        this.title = title;
        this.prixtotal = prixtotal;
        this.datedebut = datedebut;
        this.datefin = datefin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPropriete_id() {
        return propriete_id;
    }

    public void setPropriete_id(int propriete_id) {
        this.propriete_id = propriete_id;
    }

    public int getNbr_personne() {
        return nbr_personne;
    }

    public void setNbr_personne(int nbr_personne) {
        this.nbr_personne = nbr_personne;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrixtotal() {
        return prixtotal;
    }

    public void setPrixtotal(double prixtotal) {
        this.prixtotal = prixtotal;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", propriete_id=" + propriete_id +
                ", nbr_personne=" + nbr_personne +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", prixtotal=" + prixtotal +
                ", datedebut=" + datedebut +
                ", datefin=" + datefin +
                '}';
    }
}
