package com.library.library_app.web;

import com.library.library_app.repositories.BooksBorrowedRepository;
import com.library.library_app.repositories.BookRepository;
import com.library.library_app.repositories.MemberRepository;
import com.library.library_app.models.BooksBorrowed;
import com.library.library_app.models.Book;
import com.library.library_app.models.Member;
import com.library.library_app.Services.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BorrowWebController {
    private final BooksBorrowedRepository borrowedRepo;
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final LibraryService libraryService;

    public BorrowWebController(BooksBorrowedRepository borrowedRepo, BookRepository bookRepo, MemberRepository memberRepo, LibraryService libraryService) {
        this.borrowedRepo = borrowedRepo;
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.libraryService = libraryService;
    }

    @GetMapping("/borrowed")
    public String listBorrowed(Model model) {
        model.addAttribute("borrowedList", borrowedRepo.getAllBorrowedBooks());
        return "borrowed";
    }

    @GetMapping("/borrow")
    public String borrowForm(Model model) {
        model.addAttribute("members", memberRepo.getAllMembers());
        model.addAttribute("books", bookRepo.getAllBooks());
        model.addAttribute("today", LocalDate.now());
        return "borrow_form";
    }

    @PostMapping("/borrow")
    public String borrowBook(@RequestParam int memberId, @RequestParam int bookId, @RequestParam String dateBorrowed, RedirectAttributes redirectAttributes) {
        BooksBorrowed borrowed = new BooksBorrowed();
        borrowed.setMemberId(memberId);
        borrowed.setBookId(bookId);
        borrowed.setDateBorrowed(LocalDate.parse(dateBorrowed));
        borrowed.setReturnDate(null);
        borrowedRepo.addBorrowedBook(borrowed);
        redirectAttributes.addFlashAttribute("message", "Book borrowed successfully.");
        return "redirect:/borrowed";
    }

    @PostMapping("/borrowed/{id}/return")
    public String returnBook(@PathVariable("id") int borrowedId, RedirectAttributes redirectAttributes) {
        double fine = libraryService.returnBookAndApplyFine(borrowedId);
        if (fine > 0) {
            redirectAttributes.addFlashAttribute("message", "Book returned. Late fine applied: $" + fine);
        } else {
            redirectAttributes.addFlashAttribute("message", "Book returned on time. No fine.");
        }
        return "redirect:/borrowed";
    }
}
