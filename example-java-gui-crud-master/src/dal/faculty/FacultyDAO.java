package dal.faculty;

import models.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.Database;

public class FacultyDAO {
    public FacultyDAO() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS faculty (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "faculty_number TEXT NOT NULL," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "subjects TEXT NOT NULL," +
                "units INTEGER NOT NULL," +
                "year_started TEXT NOT NULL)";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFaculty(Faculty faculty) {
        String sql = "INSERT INTO faculty(faculty_number, first_name, last_name, subjects, units, year_started) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, faculty.getFacultyNumber());
            pstmt.setString(2, faculty.getFirstName());
            pstmt.setString(3, faculty.getLastName());
            pstmt.setString(4, faculty.getSubjects());
            pstmt.setInt(5, faculty.getUnits());
            pstmt.setString(6, faculty.getYearStarted());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Faculty> getAllFaculty() {
        List<Faculty> facultyList = new ArrayList<>();
        String sql = "SELECT * FROM faculty";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                facultyList.add(new Faculty(
                        rs.getInt("id"),
                        rs.getString("faculty_number"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("subjects"),
                        rs.getInt("units"),
                        rs.getString("year_started")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultyList;
    }

    public void updateFaculty(Faculty faculty) {
        String sql = "UPDATE faculty SET faculty_number = ?, first_name = ?, last_name = ?, subjects = ?, units = ?, year_started = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, faculty.getFacultyNumber());
            pstmt.setString(2, faculty.getFirstName());
            pstmt.setString(3, faculty.getLastName());
            pstmt.setString(4, faculty.getSubjects());
            pstmt.setInt(5, faculty.getUnits());
            pstmt.setString(6, faculty.getYearStarted());
            pstmt.setInt(7, faculty.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFaculty(int id) {
        String sql = "DELETE FROM faculty WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}