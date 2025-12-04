package com.library.library_app.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.library.library_app.models.BooksBorrowed;

@Repository
public class BooksBorrowedRepository {
    private final JdbcTemplate jdbcTemplate;

    public BooksBorrowedRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all borrowed records
    public List<BooksBorrowed> getAllBorrowedBooks() {
        String sql = "SELECT id, book_id, member_id, borrow_date, due_date, return_date FROM books_borrowed";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("borrow_date", LocalDate.class),
                        rs.getObject("due_date", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Get borrowed record by id
    public BooksBorrowed getBorrowedById(int id) {
        String sql = "SELECT id, book_id, member_id, borrow_date, due_date, return_date FROM books_borrowed WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("borrow_date", LocalDate.class),
                        rs.getObject("due_date", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Get all borrowed records for a specific member
    public List<BooksBorrowed> getBorrowedByMemberId(int memberId) {
        String sql = "SELECT id, book_id, member_id, borrow_date, due_date, return_date FROM books_borrowed WHERE member_id = ?";
        return jdbcTemplate.query(sql, new Object[]{memberId}, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("borrow_date", LocalDate.class),
                        rs.getObject("due_date", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Get all borrowed records for a specific book
    public List<BooksBorrowed> getBorrowedByBookId(int bookId) {
        String sql = "SELECT id, book_id, member_id, borrow_date, due_date, return_date FROM books_borrowed WHERE book_id = ?";
        return jdbcTemplate.query(sql, new Object[]{bookId}, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("borrow_date", LocalDate.class),
                        rs.getObject("due_date", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Get all unreturned books (return_date is NULL)
    public List<BooksBorrowed> getUnreturnedBooks() {
        String sql = "SELECT id, book_id, member_id, borrow_date, due_date, return_date FROM books_borrowed WHERE return_date IS NULL";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("borrow_date", LocalDate.class),
                        rs.getObject("due_date", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Create a new borrow record
    public int addBorrowedBook(BooksBorrowed borrowed) {
        String sql = "INSERT INTO books_borrowed (book_id, member_id, borrow_date, due_date, return_date) VALUES (?, ?, ?, ?, NULL)";
        return jdbcTemplate.update(sql, borrowed.getBookId(), borrowed.getMemberId(), borrowed.getBorrowDate(), borrowed.getDueDate());
    }

    // Update a borrowed record (e.g., set return_date)
    public int updateBorrowedBook(BooksBorrowed booksBorrowed) {
        String sql = "UPDATE books_borrowed SET book_id = ?, member_id = ?, borrow_date = ?, due_date = ?, return_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                booksBorrowed.getBookId(),
                booksBorrowed.getMemberId(),
                booksBorrowed.getBorrowDate(),
                booksBorrowed.getDueDate(),
                booksBorrowed.getReturnDate(),
                booksBorrowed.getId());
    }

    // Mark a book as returned (set return_date to today)
    public int returnBook(int borrowedId) {
        String sql = "UPDATE books_borrowed SET return_date = ? WHERE id = ? AND return_date IS NULL";
        return jdbcTemplate.update(sql, LocalDate.now(), borrowedId);
    }

    // Delete a borrowed record
    public int deleteBorrowedBook(int id) {
        String sql = "DELETE FROM books_borrowed WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Joined view for UI: book title, member full name
    public List<Map<String, Object>> getAllBorrowedWithDetails() {
        String sql = "SELECT bb.id, bb.book_id, b.title AS book_title, bb.member_id, m.name AS member_name, bb.borrow_date, bb.due_date, bb.return_date " +
                     "FROM books_borrowed bb " +
                     "JOIN books b ON b.id = bb.book_id " +
                     "JOIN members m ON m.id = bb.member_id " +
                     "ORDER BY bb.id";
        return jdbcTemplate.queryForList(sql);
    }

    // Joined view for a specific member: include book title & author
    public List<Map<String, Object>> getBorrowedByMemberWithDetails(int memberId) {
        String sql = "SELECT bb.id, bb.book_id, b.title AS book_title, b.author AS book_author, bb.member_id, bb.borrow_date, bb.due_date, bb.return_date " +
                     "FROM books_borrowed bb " +
                     "JOIN books b ON b.id = bb.book_id " +
                     "WHERE bb.member_id = ? " +
                     "ORDER BY bb.id";
        return jdbcTemplate.queryForList(sql, memberId);
    }

    // Joined view: overdue books (return_date IS NULL and due_date < today)
    public List<Map<String, Object>> getOverdueWithDetails() {
        String sql = "SELECT bb.id, bb.book_id, b.title AS book_title, bb.member_id, m.name AS member_name, " +
                     "bb.borrow_date, bb.due_date, bb.return_date " +
                     "FROM books_borrowed bb " +
                     "JOIN books b ON b.id = bb.book_id " +
                     "JOIN members m ON m.id = bb.member_id " +
                     "WHERE bb.return_date IS NULL AND bb.due_date < CURRENT_DATE " +
                     "ORDER BY bb.due_date ASC";
        return jdbcTemplate.queryForList(sql);
    }

    // Use overdue_books table with joins for UI
    public List<Map<String, Object>> getOverdueTableWithDetails() {
        String sql = "SELECT ob.id, ob.book_id, b.title AS book_title, ob.member_id, m.name AS member_name, " +
                     "ob.due_date, ob.fine_amount " +
                     "FROM overdue_books ob " +
                     "JOIN books b ON b.id = ob.book_id " +
                     "JOIN members m ON m.id = ob.member_id " +
                     "ORDER BY ob.due_date ASC";
        return jdbcTemplate.queryForList(sql);
    }
}
