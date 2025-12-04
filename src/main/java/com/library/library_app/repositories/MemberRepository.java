package com.library.library_app.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.library.library_app.models.Member;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all members
    public List<Member> getAllMembers() {
        String sql = "SELECT id, name, email, password, fines FROM members";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("fines")
                )
        );
    }

    // Get member by id
    public Member getMemberById(int id) {
        String sql = "SELECT id, name, email, password, fines FROM members WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("fines")
                )
        );
    }

    // Add fine amount to a member
    public int addFine(int memberId, double amount) {
        String sql = "UPDATE members SET fines = fines + ? WHERE id = ?";
        return jdbcTemplate.update(sql, amount, memberId);
    }

    // Add a new member
    public int addMember(Member member) {
        String sql = "INSERT INTO members (name, email, password, fines) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword(), member.getFines());
    }

    // Update a member
    public int updateMember(Member member) {
        String sql = "UPDATE members SET name = ?, email = ?, password = ?, fines = ? WHERE id = ?";
        return jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword(), member.getFines(), member.getId());
    }

    // Delete a member
    public int deleteMember(int id) {
        String sql = "DELETE FROM members WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Find member by email and password (simple authentication)
    public Member findByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, name, email, password, fines FROM members WHERE email = ? AND password = ?";
        List<Member> list = jdbcTemplate.query(sql, ps -> {
                    ps.setString(1, email);
                    ps.setString(2, password);
                },
                (rs, rowNum) -> new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("fines")
                )
        );
        return list.isEmpty() ? null : list.get(0);
    }
}
