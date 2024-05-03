package services;

import entities.Propriete;
import interfaces.IService;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprieteService implements IService<Propriete> {

    Connection cnx;
    public ProprieteService() {
        cnx = MyDatabase.getInstance().getConnection();
    }




    @Override
    public void add(Propriete propriete) throws SQLException {
        String req = "INSERT INTO propriete(proprietaire_id , titre, description, adresse, nombrechambre, nombresalle, prixnuit, capacite, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, propriete.getProprietaire_id());
        ps.setString(2, propriete.getTitre());
        ps.setString(3, propriete.getDescription());
        ps.setString(4, propriete.getAdresse());
        ps.setInt(5, propriete.getNombrechambre());
        ps.setInt(6, propriete.getNombresalle());
        ps.setDouble(7, propriete.getPrixnuit());
        ps.setInt(8, propriete.getCapacite());
        ps.setString(9, propriete.getImage());
        ps.executeUpdate();
    }

    @Override
    public void update(Propriete propriete) throws SQLException {
        String req = "UPDATE propriete SET proprietaire_id = ?, titre = ?, description = ?, adresse = ?, nombrechambre = ?, nombresalle = ?, prixnuit = ?, capacite = ?, image = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, propriete.getProprietaire_id());
        ps.setString(2, propriete.getTitre());
        ps.setString(3, propriete.getDescription());
        ps.setString(4, propriete.getAdresse());
        ps.setInt(5, propriete.getNombrechambre());
        ps.setInt(6, propriete.getNombresalle());
        ps.setDouble(7, propriete.getPrixnuit());
        ps.setInt(8, propriete.getCapacite());
        ps.setString(9, propriete.getImage());
        ps.setInt(10, propriete.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(Propriete propriete) throws SQLException {
        String req = "DELETE FROM propriete WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, propriete.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Propriete> read() throws SQLException {
        List<Propriete> Proprietes = new ArrayList<>();
        String req = "SELECT * FROM Propriete";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Propriete propriete = new Propriete(
                    rs.getInt("id"),
                    rs.getInt("proprietaire_id"),
                    rs.getInt("nombrechambre"),
                    rs.getInt("nombresalle"),
                    rs.getInt("capacite"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getString("adresse"),
                    rs.getString("image"),
                    rs.getDouble("prixnuit")
            );
            Proprietes.add(propriete);
        }
        System.out.println(Proprietes);
        return Proprietes;
    }

    public String NameFromId(int id) throws  SQLException {
        String req = "SELECT titre FROM propriete WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("titre");
        }
        return null;
    }
}
