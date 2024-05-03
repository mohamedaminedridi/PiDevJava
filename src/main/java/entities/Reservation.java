package entities;

public class Reservation {
    int id,user_id,voyage_id,numberguest;
    String totalprice,status;

    public Reservation(int id, int user_id, int voyage_id, int numberguest, String totalprice, String status) {
        this.id = id;
        this.user_id = user_id;
        this.voyage_id = voyage_id;
        this.numberguest = numberguest;
        this.totalprice = totalprice;
        this.status = status;
    }

    public Reservation(int user_id, int voyage_id, int numberguest, String totalprice, String status) {
        this.user_id = user_id;
        this.voyage_id = voyage_id;
        this.numberguest = numberguest;
        this.totalprice = totalprice;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVoyage_id() {
        return voyage_id;
    }

    public void setVoyage_id(int voyage_id) {
        this.voyage_id = voyage_id;
    }

    public int getNumberguest() {
        return numberguest;
    }

    public void setNumberguest(int numberguest) {
        this.numberguest = numberguest;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", voyage_id=" + voyage_id +
                ", numberguest=" + numberguest +
                ", totalprice='" + totalprice + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
