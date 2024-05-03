package services;

import entities.Propriete;
import entities.Voyage;
import interfaces.IService;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoyageService implements IService<Voyage> {
    Connection cnx;
    public VoyageService() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Voyage voyage) throws SQLException {
        String req = "INSERT INTO voyage(propriete_id , nbr_personne, image, description, title, prixtotal, datedebut, datefin) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, voyage.getPropriete_id());
        ps.setInt(2, voyage.getNbr_personne());
        ps.setString(3, voyage.getImage());
        ps.setString(4, voyage.getDescription());
        ps.setString(5, voyage.getTitle());
        ps.setDouble(6, voyage.getPrixtotal());
        ps.setDate(7, voyage.getDatedebut());
        ps.setDate(8, voyage.getDatefin());
        ps.executeUpdate();
    }

    @Override
    public void update(Voyage voyage) throws SQLException {
        String req = "UPDATE voyage SET propriete_id = ?, nbr_personne= ? , image= ? , description = ?, title = ?, prixtotal = ?, datedebut = ?, datefin = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, voyage.getPropriete_id());
        ps.setInt(2, voyage.getNbr_personne());
        ps.setString(3, voyage.getImage());
        ps.setString(4, voyage.getDescription());
        ps.setString(5, voyage.getTitle());
        ps.setDouble(6, voyage.getPrixtotal());
        ps.setDate(7, voyage.getDatedebut());
        ps.setDate(8, voyage.getDatefin());
        ps.setInt(9, voyage.getId());

        ps.executeUpdate();
    }

    @Override
    public void delete(Voyage voyage) throws SQLException {
        String req = "DELETE FROM voyage WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, voyage.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Voyage> read() throws SQLException {
        List<Voyage> Voyages = new ArrayList<>();
        String req = "SELECT * FROM voyage";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Voyage voyage = new Voyage(
                    rs.getInt("id"),
                    rs.getInt("propriete_id"),
                    rs.getInt("nbr_personne"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getString("title"),
                    rs.getDouble("prixtotal"),
                    rs.getDate("datedebut"),
                    rs.getDate("datefin")
            );
            Voyages.add(voyage);
        }
        System.out.println(Voyages);
        return Voyages;
    }
}
