package com.library.library_app.repositories;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.library.library_app.models.Book;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all books
    public List<Book> getAllBooks() {
        String sql = "SELECT id, title, author, isbn, category, year_published, available_copies FROM books";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn"),
                rs.getString("category"),
                rs.getInt("year_published"),
                rs.getInt("available_copies")
            )
        );
    }

    // Get available books (not currently borrowed and not in overdue list)
    public List<Book> getAvailableBooks() {
        String sql = "SELECT b.id, b.title, b.author, b.isbn, b.category, b.year_published, b.available_copies " +
                     "FROM books b " +
                     "WHERE b.id NOT IN (SELECT bb.book_id FROM books_borrowed bb WHERE bb.return_date IS NULL) " +
                     "AND b.id NOT IN (SELECT ob.book_id FROM overdue_books ob)";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn"),
                rs.getString("category"),
                rs.getInt("year_published"),
                rs.getInt("available_copies")
            )
        );
    }

    // Get book by id
    public Book getBookById(int id) {
        String sql = "SELECT id, title, author, isbn, category, year_published, available_copies FROM books WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
            new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn"),
                rs.getString("category"),
                rs.getInt("year_published"),
                rs.getInt("available_copies")
            )
        );
    }

    // Add a new book
    public int addBook(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, category, year_published, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getCategory(), book.getYearPublished(), book.getAvailableCopies());
    }

    // Update a book
    public int updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, isbn = ?, category = ?, year_published = ?, available_copies = ? WHERE id = ?";
        return jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getCategory(), book.getYearPublished(), book.getAvailableCopies(), book.getId());
    }

    // Delete a book
    public int deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Search books by title, author, or ISBN (case-insensitive LIKE)
    public List<Book> searchBooks(String query) {
        String like = "%" + query + "%";
        String sql = "SELECT id, title, author, isbn, category, year_published, available_copies FROM books " +
                "WHERE LOWER(title) LIKE LOWER(?) OR LOWER(author) LIKE LOWER(?) OR LOWER(isbn) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, ps -> {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
        }, (rs, rowNum) -> new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn"),
                rs.getString("category"),
                rs.getInt("year_published"),
                rs.getInt("available_copies")
        ));
    }

    // Decrement available copies for a book if available_copies > 0
    public int decrementAvailableCopies(int bookId) {
        String sql = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ? AND available_copies > 0";
        return jdbcTemplate.update(sql, bookId);
    }

    // Increment available copies for a book
    public int incrementAvailableCopies(int bookId) {
        String sql = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ?";
        return jdbcTemplate.update(sql, bookId);
    }
}
