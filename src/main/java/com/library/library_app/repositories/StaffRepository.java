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
        String sql = "SELECT id, name, email, password, role FROM staff";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Staff(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                )
        );
    }

    // Get staff by id
    public Staff getStaffById(int id) {
        String sql = "SELECT id, name, email, password, role FROM staff WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Staff(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                )
        );
    }

    // Add a new staff
    public int addStaff(Staff staff) {
        String sql = "INSERT INTO staff (name, email, password, role) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, staff.getName(), staff.getEmail(), staff.getPassword(), staff.getRole());
    }

    // Update a staff
    public int updateStaff(Staff staff) {
        String sql = "UPDATE staff SET name = ?, email = ?, password = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql, staff.getName(), staff.getEmail(), staff.getPassword(), staff.getRole(), staff.getId());
    }

    // Delete a staff
    public int deleteStaff(int id) {
        String sql = "DELETE FROM staff WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Find staff by email and password (simple authentication)
    public Staff findByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, name, email, password, role FROM staff WHERE email = ? AND password = ?";
        List<Staff> list = jdbcTemplate.query(sql, ps -> {
                    ps.setString(1, email);
                    ps.setString(2, password);
                },
                (rs, rowNum) -> new Staff(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                )
        );
        return list.isEmpty() ? null : list.get(0);
    }
}
