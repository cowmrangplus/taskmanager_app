package dao;

import model.Task;
import model.Project;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// CRUD task
public class TaskDAO {

    // ===== THÊM TASK =====
    public boolean insert(Task t) {
        String sql = "INSERT INTO task(tieude, mota, priority, deadline, hoanthanh, project_id) VALUES(?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getPriority());
            if (t.getDeadline() != null)
                ps.setTimestamp(4, Timestamp.valueOf(t.getDeadline()));
            else
                ps.setNull(4, Types.TIMESTAMP);
            ps.setBoolean(5, t.isCompleted());
            if (t.getProject() != null)
                ps.setInt(6, t.getProject().getId());
            else
                ps.setNull(6, Types.INTEGER);

            int r = ps.executeUpdate();
            if (r > 0) {
                try (ResultSet g = ps.getGeneratedKeys()) {
                    if (g.next()) t.setId(g.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== CẬP NHẬT TASK =====
    public boolean update(Task t) {
        String sql = "UPDATE task SET tieude=?, mota=?, priority=?, deadline=?, hoanthanh=?, project_id=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getPriority());
            if (t.getDeadline() != null)
                ps.setTimestamp(4, Timestamp.valueOf(t.getDeadline()));
            else
                ps.setNull(4, Types.TIMESTAMP);
            ps.setBoolean(5, t.isCompleted());
            if (t.getProject() != null)
                ps.setInt(6, t.getProject().getId());
            else
                ps.setNull(6, Types.INTEGER);
            ps.setInt(7, t.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== XÓA TASK =====
    public boolean delete(int id) {
        String sql = "DELETE FROM task WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== LẤY TOÀN BỘ TASK =====
    public List<Task> getAll() {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT t.*, p.id AS pid, p.ten AS pname " +
                "FROM task t LEFT JOIN project p ON t.project_id = p.id " +
                "ORDER BY deadline";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Task t = new Task();
                t.setId(rs.getInt("id"));
                t.setTitle(rs.getString("tieude"));
                t.setDescription(rs.getString("mota"));
                t.setPriority(rs.getString("priority"));
                Timestamp ts = rs.getTimestamp("deadline");
                if (ts != null) t.setDeadline(ts.toLocalDateTime());
                t.setCompleted(rs.getBoolean("hoanthanh"));

                int pid = rs.getInt("pid");
                if (!rs.wasNull()) {
                    Project p = new Project(pid, rs.getString("pname"));
                    t.setProject(p);
                }
                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== LỌC TASK THEO KHOẢNG NGÀY =====
    public List<Task> getTasksByDate(LocalDateTime from, LocalDateTime to) {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT t.*, p.id AS pid, p.ten AS pname " +
                "FROM task t LEFT JOIN project p ON t.project_id = p.id " +
                "WHERE deadline BETWEEN ? AND ? ORDER BY deadline";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Task t = new Task();
                    t.setId(rs.getInt("id"));
                    t.setTitle(rs.getString("tieude"));
                    t.setDescription(rs.getString("mota"));
                    t.setPriority(rs.getString("priority"));
                    Timestamp ts = rs.getTimestamp("deadline");
                    if (ts != null) t.setDeadline(ts.toLocalDateTime());
                    t.setCompleted(rs.getBoolean("hoanthanh"));

                    int pid = rs.getInt("pid");
                    if (!rs.wasNull()) {
                        Project p = new Project(pid, rs.getString("pname"));
                        t.setProject(p);
                    }
                    list.add(t);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
