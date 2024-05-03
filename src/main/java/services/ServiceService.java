package services;

import entities.Service;
import entities.Voyage;
import interfaces.IService;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceService implements IService<Service> {

    Connection cnx;
    public ServiceService() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Service service) throws SQLException {
        String req = "INSERT INTO service(nom , description, prix) VALUES(?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, service.getNom());
        ps.setString(2, service.getDescription());
        ps.setInt(3, service.getPrix());
        ps.executeUpdate();
    }

    @Override
    public void update(Service service) throws SQLException {
        String req = "UPDATE service SET nom = ?, description = ?, prix = ? WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, service.getNom());
        ps.setString(2, service.getDescription());
        ps.setInt(3, service.getPrix());
        ps.setInt(4, service.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(Service service) throws SQLException {
        String req = "DELETE FROM service WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, service.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Service> read() throws SQLException {
        List<Service> Services = new ArrayList<>();
        String req = "SELECT * FROM service";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Service service = new Service(
                    rs.getInt("id"),
                    rs.getInt("prix"),
                    rs.getString("nom"),
                    rs.getString("description")
            );
            Services.add(service);
        }
        System.out.println(Services);
        return Services;
    }
}
