package entities;

public class Propriete {
        int Id,proprietaire_id,nombrechambre,nombresalle,capacite ;
        String titre,description,adresse,image;
        double prixnuit;

    public Propriete(int id, int proprietaire_id, int nombrechambre, int nombresalle, int capacite, String titre, String description, String adresse, String image, double prixnuit) {
        Id = id;
        this.proprietaire_id = proprietaire_id;
        this.nombrechambre = nombrechambre;
        this.nombresalle = nombresalle;
        this.capacite = capacite;
        this.titre = titre;
        this.description = description;
        this.adresse = adresse;
        this.image = image;
        this.prixnuit = prixnuit;
    }

    public Propriete(int proprietaire_id, int nombrechambre, int nombresalle, int capacite, String titre, String description, String adresse, String image, double prixnuit) {
        this.proprietaire_id = proprietaire_id;
        this.nombrechambre = nombrechambre;
        this.nombresalle = nombresalle;
        this.capacite = capacite;
        this.titre = titre;
        this.description = description;
        this.adresse = adresse;
        this.image = image;
        this.prixnuit = prixnuit;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProprietaire_id() {
        return proprietaire_id;
    }

    public void setProprietaire_id(int proprietaire_id) {
        this.proprietaire_id = proprietaire_id;
    }

    public int getNombrechambre() {
        return nombrechambre;
    }

    public void setNombrechambre(int nombrechambre) {
        this.nombrechambre = nombrechambre;
    }

    public int getNombresalle() {
        return nombresalle;
    }

    public void setNombresalle(int nombresalle) {
        this.nombresalle = nombresalle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrixnuit() {
        return prixnuit;
    }

    public void setPrixnuit(double prixnuit) {
        this.prixnuit = prixnuit;
    }

    @Override
    public String toString() {
        return "Propriete{" +
                "Id=" + Id +
                ", proprietaire_id=" + proprietaire_id +
                ", nombrechambre=" + nombrechambre +
                ", nombresalle=" + nombresalle +
                ", capacite=" + capacite +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", adresse='" + adresse + '\'' +
                ", image='" + image + '\'' +
                ", prixnuit=" + prixnuit +
                '}';
    }
}
