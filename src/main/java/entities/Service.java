package entities;

public class Service {
    int id,prix;
    String nom,description;

    public Service(int id, int prix, String nom, String description) {
        this.id = id;
        this.prix = prix;
        this.nom = nom;
        this.description = description;
    }

    public Service(int prix, String nom, String description) {
        this.prix = prix;
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", prix=" + prix +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
