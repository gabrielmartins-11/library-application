package com.library.library_app.web;

import com.library.library_app.repositories.BookRepository;
import com.library.library_app.models.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookWebController {
    private final BookRepository bookRepo;

    public BookWebController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepo.getAllBooks());
        return "books";
    }

    @GetMapping("/books/{id}")
    public String bookDetail(@PathVariable("id") int id, Model model) {
        Book book = bookRepo.getBookById(id);
        model.addAttribute("book", book);
        // TODO: Add borrowing history if needed
        return "book_detail";
    }
}
