package com.library.library_app.repositories;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.library.library_app.models.Staff;

@Repository
public class StaffRepository {
    private final JdbcTemplate jdbcTemplate;

    public StaffRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all staff
    public List<Staff> getAllStaff() {
        String sql = "SELECT id, first_name, last_name, email FROM staff";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Staff(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                )
        );
    }

    // Get staff by id
    public Staff getStaffById(int id) {
        String sql = "SELECT id, first_name, last_name, email FROM staff WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Staff(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                )
        );
    }

    // Add a new staff
    public int addStaff(Staff staff) {
        String sql = "INSERT INTO staff (first_name, last_name, email) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, staff.getFirstName(), staff.getLastName(), staff.getEmail());
    }

    // Update a staff
    public int updateStaff(Staff staff) {
        String sql = "UPDATE staff SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
        return jdbcTemplate.update(sql, staff.getFirstName(), staff.getLastName(), staff.getEmail(), staff.getId());
    }

    // Delete a staff
    public int deleteStaff(int id) {
        String sql = "DELETE FROM staff WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
