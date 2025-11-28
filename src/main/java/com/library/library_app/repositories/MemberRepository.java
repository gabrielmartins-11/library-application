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
        String sql = "SELECT id, first_name, last_name, email FROM members";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                )
        );
    }

    // Get member by id
    public Member getMemberById(int id) {
        String sql = "SELECT id, first_name, last_name, email FROM members WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                )
        );
    }

    // Add a new member
    public int addMember(Member member) {
        String sql = "INSERT INTO members (first_name, last_name, email) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, member.getFirstName(), member.getLastName(), member.getEmail());
    }

    // Update a member
    public int updateMember(Member member) {
        String sql = "UPDATE members SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
        return jdbcTemplate.update(sql, member.getFirstName(), member.getLastName(), member.getEmail(), member.getId());
    }

    // Delete a member
    public int deleteMember(int id) {
        String sql = "DELETE FROM members WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
