package com.library.library_app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.library.library_app.repositories.BookRepository;
import com.library.library_app.repositories.MemberRepository;
import com.library.library_app.repositories.StaffRepository;
import com.library.library_app.repositories.BooksBorrowedRepository;

@Controller
public class HomeController {
    private final BookRepository bookRepo;
    private final MemberRepository memberRepo;
    private final StaffRepository staffRepo;
    private final BooksBorrowedRepository borrowedRepo;

    public HomeController(BookRepository bookRepo, MemberRepository memberRepo, StaffRepository staffRepo, BooksBorrowedRepository borrowedRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.staffRepo = staffRepo;
        this.borrowedRepo = borrowedRepo;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("bookCount", bookRepo.getAllBooks().size());
        model.addAttribute("memberCount", memberRepo.getAllMembers().size());
        model.addAttribute("staffCount", staffRepo.getAllStaff().size());
        model.addAttribute("borrowedCount", borrowedRepo.getAllBorrowedBooks().size());
        return "home";
    }
}
