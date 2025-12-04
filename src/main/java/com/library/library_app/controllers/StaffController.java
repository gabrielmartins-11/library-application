package com.library.library_app.controllers;

import com.library.library_app.models.Book;
import com.library.library_app.models.Member;
import com.library.library_app.repositories.BookRepository;
import com.library.library_app.repositories.MemberRepository;
import com.library.library_app.repositories.BooksBorrowedRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class StaffController {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BooksBorrowedRepository booksBorrowedRepository;

    public StaffController(BookRepository bookRepository,
                           MemberRepository memberRepository,
                           BooksBorrowedRepository booksBorrowedRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.booksBorrowedRepository = booksBorrowedRepository;
    }

    // Staff dashboard
    @GetMapping("/staff")
    public String staffDashboard(HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        return "staff/dashboard";
    }

    // Add Book
    @GetMapping("/staff/books/add")
    public String addBookForm(HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        return "staff/add_book";
    }

    @PostMapping("/staff/books/add")
    public String addBookSubmit(@RequestParam String title,
                                @RequestParam String author,
                                @RequestParam String category,
                                @RequestParam int year,
                                @RequestParam String isbn,
                                @RequestParam int copies,
                                Model model,
                                HttpSession session,
                                RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(author);
        b.setCategory(category);
        b.setYearPublished(year);
        b.setIsbn(isbn);
        b.setAvailableCopies(copies);
        bookRepository.addBook(b);
        model.addAttribute("message", "Book added successfully.");
        return "staff/add_book_result";
    }

    // Manage Books
    @GetMapping("/staff/books")
    public String manageBooks(@RequestParam(value = "q", required = false) String q, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        List<Book> books = (q == null || q.isBlank()) ? bookRepository.getAllBooks() : bookRepository.searchBooks(q);
        model.addAttribute("books", books);
        model.addAttribute("query", q);
        return "staff/manage_books";
    }

    @PostMapping("/staff/books/{id}/edit")
    public String editBook(@PathVariable int id,
                           @RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String category,
                           @RequestParam int year,
                           @RequestParam int copies,
                           HttpSession session,
                           RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        Book existing = bookRepository.getBookById(id);
        existing.setTitle(title);
        existing.setAuthor(author);
        existing.setCategory(category);
        existing.setYearPublished(year);
        existing.setAvailableCopies(copies);
        bookRepository.updateBook(existing);
        return "redirect:/staff/books";
    }

    @PostMapping("/staff/books/{id}/delete")
    public String deleteBook(@PathVariable int id, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        bookRepository.deleteBook(id);
        return "redirect:/staff/books";
    }

    // Manage Members
    @GetMapping("/staff/members")
    public String manageMembers(Model model, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        model.addAttribute("members", memberRepository.getAllMembers());
        return "staff/manage_members";
    }

    @GetMapping("/staff/members/{memberId}/history")
    public String memberHistory(@PathVariable int memberId, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        model.addAttribute("member", memberRepository.getMemberById(memberId));
        model.addAttribute("borrowed", booksBorrowedRepository.getBorrowedByMemberId(memberId));
        return "staff/member_history";
    }

    @PostMapping("/staff/members/{memberId}/fines/clear")
    public String clearFines(@PathVariable int memberId, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        Member m = memberRepository.getMemberById(memberId);
        m.setFines(0.0);
        memberRepository.updateMember(m);
        return "redirect:/staff/members";
    }

    @PostMapping("/staff/members/{memberId}/fines/set")
    public String setFines(@PathVariable int memberId, @RequestParam double amount, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        Member m = memberRepository.getMemberById(memberId);
        m.setFines(amount);
        memberRepository.updateMember(m);
        return "redirect:/staff/members";
    }

    @PostMapping("/staff/members/add")
    public String addMember(@RequestParam String name,
                            @RequestParam String email,
                            @RequestParam String password,
                            HttpSession session,
                            RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        Member m = new Member();
        m.setName(name);
        m.setEmail(email);
        m.setPassword(password);
        m.setFines(0.0);
        memberRepository.addMember(m);
        ra.addFlashAttribute("message", "Member added.");
        return "redirect:/staff/members";
    }

    @PostMapping("/staff/members/{memberId}/delete")
    public String deleteMember(@PathVariable int memberId, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        memberRepository.deleteMember(memberId);
        ra.addFlashAttribute("message", "Member deleted.");
        return "redirect:/staff/members";
    }

    // View all borrowed books
    @GetMapping("/staff/borrowed")
    public String viewBorrowed(Model model, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        List<Map<String, Object>> list = booksBorrowedRepository.getAllBorrowedWithDetails();
        // Normalize and precompute status flags to avoid template date comparisons
        LocalDate today = LocalDate.now();
        for (Map<String, Object> r : list) {
            Object dueObj = r.get("due_date");
            Object retObj = r.get("return_date");
            boolean returned = retObj != null;
            boolean overdue = false;
            if (!returned && dueObj != null) {
                try {
                    if (dueObj instanceof java.sql.Date d) {
                        overdue = d.toLocalDate().isBefore(today);
                    } else if (dueObj instanceof LocalDate ld) {
                        overdue = ld.isBefore(today);
                    }
                } catch (Exception ignored) {}
            }
            r.put("returned", returned);
            r.put("overdue", overdue);
        }
        model.addAttribute("borrowed", list);
        model.addAttribute("today", today);
        return "staff/borrowed";
    }

    // View overdue books
    @GetMapping("/staff/overdue")
    public String viewOverdue(Model model, HttpSession session, RedirectAttributes ra) {
        if (!"STAFF".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Staff only.");
            return "redirect:/";
        }
        var list = booksBorrowedRepository.getOverdueTableWithDetails();
        model.addAttribute("overdue", list);
        return "staff/overdue";
    }
}
