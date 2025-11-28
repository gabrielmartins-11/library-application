package com.library.library_app.repositories;

import java.time.LocalDate;
import java.util.List;
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
        String sql = "SELECT id, book_id, member_id, date_borrowed, return_date FROM books_borrowed";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("date_borrowed", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Get borrowed record by id
    public BooksBorrowed getBorrowedById(int id) {
        String sql = "SELECT id, book_id, member_id, date_borrowed, return_date FROM books_borrowed WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("date_borrowed", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Get all borrowed records for a specific member
    public List<BooksBorrowed> getBorrowedByMemberId(int memberId) {
        String sql = "SELECT id, book_id, member_id, date_borrowed, return_date FROM books_borrowed WHERE member_id = ?";
        return jdbcTemplate.query(sql, new Object[]{memberId}, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("date_borrowed", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Get all borrowed records for a specific book
    public List<BooksBorrowed> getBorrowedByBookId(int bookId) {
        String sql = "SELECT id, book_id, member_id, date_borrowed, return_date FROM books_borrowed WHERE book_id = ?";
        return jdbcTemplate.query(sql, new Object[]{bookId}, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("date_borrowed", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Get all unreturned books (return_date is NULL)
    public List<BooksBorrowed> getUnreturnedBooks() {
        String sql = "SELECT id, book_id, member_id, date_borrowed, return_date FROM books_borrowed WHERE return_date IS NULL";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new BooksBorrowed(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getObject("date_borrowed", LocalDate.class),
                        rs.getObject("return_date", LocalDate.class)
                )
        );
    }

    // Add a new borrowed record
    public int addBorrowedBook(BooksBorrowed booksBorrowed) {
        String sql = "INSERT INTO books_borrowed (book_id, member_id, date_borrowed, return_date) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
                booksBorrowed.getBookId(), 
                booksBorrowed.getMemberId(), 
                booksBorrowed.getDateBorrowed(), 
                booksBorrowed.getReturnDate());
    }

    // Update a borrowed record (e.g., set return_date)
    public int updateBorrowedBook(BooksBorrowed booksBorrowed) {
        String sql = "UPDATE books_borrowed SET book_id = ?, member_id = ?, date_borrowed = ?, return_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, 
                booksBorrowed.getBookId(), 
                booksBorrowed.getMemberId(), 
                booksBorrowed.getDateBorrowed(), 
                booksBorrowed.getReturnDate(), 
                booksBorrowed.getId());
    }

    // Mark a book as returned (set return_date to today)
    public int returnBook(int borrowedId) {
        String sql = "UPDATE books_borrowed SET return_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, LocalDate.now(), borrowedId);
    }

    // Delete a borrowed record
    public int deleteBorrowedBook(int id) {
        String sql = "DELETE FROM books_borrowed WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
