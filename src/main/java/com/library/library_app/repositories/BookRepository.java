package com.library.library_app.repositories;

// ...existing code...
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
        String sql = "SELECT id, title, author, year_published, genre FROM books";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("year_published"),
                rs.getString("genre")
            )
        );
    }

    // Get book by id
    public Book getBookById(int id) {
        String sql = "SELECT id, title, author, year_published, genre FROM books WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
            new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("year_published"),
                rs.getString("genre")
            )
        );
    }

    // Add a new book
    public int addBook(Book book) {
        String sql = "INSERT INTO books (title, author, year_published, genre) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getYearPublished(), book.getGenre());
    }

    // Update a book
    public int updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, year_published = ?, genre = ? WHERE id = ?";
        return jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getYearPublished(), book.getGenre(), book.getId());
    }

    // Delete a book
    public int deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
