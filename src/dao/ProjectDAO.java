package dao;

import model.Project;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CRUD project
public class ProjectDAO {

    public List<Project> getAll() {
        List<Project> list = new ArrayList<>();
        String sql = "SELECT id, name FROM project";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Project(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Project findById(int id) {
        String sql = "SELECT id, name FROM project WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Project(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(Project p) {
        String sql = "INSERT INTO project(name) VALUES(?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            int r = ps.executeUpdate();
            if (r > 0) {
                try (ResultSet g = ps.getGeneratedKeys()) {
                    if (g.next()) p.setId(g.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // update, delete tuong tu
}
