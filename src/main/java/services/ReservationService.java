package services;

import entities.Reservation;
import interfaces.IService;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {

    Connection cnx;
    public ReservationService() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Reservation reservation) throws SQLException {
        String req = "INSERT INTO reservation(user_id  , voyage_id , numberguest, totalprice, status) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, reservation.getUser_id());
        ps.setInt(2, reservation.getVoyage_id());
        ps.setInt(3, reservation.getNumberguest());
        ps.setString(4, reservation.getTotalprice());
        ps.setString(5, reservation.getStatus());
        ps.executeUpdate();
    }

    @Override
    public void update(Reservation reservation) throws SQLException {

    }

    @Override
    public void delete(Reservation reservation) throws SQLException {

    }

    @Override
    public List<Reservation> read() throws SQLException {

        List<Reservation> Reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Reservation voyage = new Reservation(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("voyage_id"),
                    rs.getInt("numberguest"),
                    rs.getString("totalprice"),
                    rs.getString("status")
            );
            Reservations.add(voyage);
        }
        System.out.println(Reservations);
        return Reservations;
    }

    public String GetUserNameByID(int id) throws SQLException {
        String req = "SELECT nom FROM user WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("nom");
        }
        return null;
    }

    public String GetVoyageTitleByID(int id) throws SQLException {
        String req = "SELECT title FROM voyage WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("title");
        }
        return null;
    }

    public void updateStatus(int id,String status) throws SQLException {
        String req = "UPDATE reservation SET status = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(2, id);
        ps.setString(1, status);
        ps.executeUpdate();
    }
}
