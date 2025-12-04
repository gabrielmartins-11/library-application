package com.library.library_app.controllers;

import com.library.library_app.Services.LibraryService;
import com.library.library_app.models.Book;
import com.library.library_app.models.BooksBorrowed;
import com.library.library_app.repositories.BookRepository;
import com.library.library_app.repositories.BooksBorrowedRepository;
import com.library.library_app.repositories.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MemberController {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BooksBorrowedRepository booksBorrowedRepository;
    private final LibraryService libraryService;

    private static final int LOAN_DAYS = 14;
    private static final double DAILY_RATE = 0.25;

    public MemberController(BookRepository bookRepository,
                            MemberRepository memberRepository,
                            BooksBorrowedRepository booksBorrowedRepository,
                            LibraryService libraryService) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.booksBorrowedRepository = booksBorrowedRepository;
        this.libraryService = libraryService;
    }

    // Dashboard
    @GetMapping("/members")
    public String memberDashboard(@RequestParam(value = "name", required = false) String name, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        model.addAttribute("memberName", name != null ? name : session.getAttribute("displayName"));
        model.addAttribute("memberId", session.getAttribute("memberId"));
        return "member/dashboard";
    }

    // Search Books page
    @GetMapping("/members/search")
    public String searchPage(HttpSession session, RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        return "member/search";
    }

    @GetMapping("/members/search/results")
    public String searchResults(@RequestParam("q") String query, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        List<Book> results = bookRepository.searchBooks(query);
        model.addAttribute("results", results);
        model.addAttribute("query", query);
        // Provide memberId for borrow form actions in the template
        Object memberId = session.getAttribute("memberId");
        if (memberId != null) {
            model.addAttribute("memberId", memberId);
        }
        return "member/search_results";
    }

    // Borrow book action
    @PostMapping("/members/borrow/{bookId}")
    public String borrowBook(@PathVariable int bookId, @RequestParam("memberId") int memberId, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        // Validate availability
        Book book = bookRepository.getBookById(bookId);
        if (book.getAvailableCopies() <= 0) {
            model.addAttribute("message", "Book is not available to borrow.");
            model.addAttribute("book", book);
            return "member/borrow_result";
        }
        // Create borrow record
        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(LOAN_DAYS);
        BooksBorrowed bb = new BooksBorrowed();
        bb.setBookId(bookId);
        bb.setMemberId(memberId);
        bb.setBorrowDate(today);
        bb.setDueDate(due);
        booksBorrowedRepository.addBorrowedBook(bb);
        // Decrement available copies
        bookRepository.decrementAvailableCopies(bookId);
        model.addAttribute("message", "You borrowed " + book.getTitle() + ". Due date: " + due + ".");
        model.addAttribute("book", book);
        return "member/borrow_result";
    }

    // My Borrowed Books page
    @GetMapping("/members/borrowed")
    public String myBorrowed(@RequestParam("memberId") int memberId, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        // Fetch with details
        List<java.util.Map<String, Object>> all = booksBorrowedRepository.getBorrowedByMemberWithDetails(memberId);
        // filter to only unreturned
        all.removeIf(b -> b.get("return_date") != null);
        model.addAttribute("borrowed", all);
        return "member/borrowed";
    }

    // Return a book
    @PostMapping("/members/borrowed/{borrowedId}/return")
    public String returnBorrowed(@PathVariable int borrowedId, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        double fine = libraryService.returnBookAndApplyFine(borrowedId);
        BooksBorrowed record = booksBorrowedRepository.getBorrowedById(borrowedId);
        // increment available copies
        bookRepository.incrementAvailableCopies(record.getBookId());
        if (fine > 0) {
            long daysLate = Math.round(fine / DAILY_RATE);
            model.addAttribute("message", "Late by " + daysLate + " days. Fine added: $" + fine);
        } else {
            model.addAttribute("message", "Book returned successfully.");
        }
        return "member/return_result";
    }

    // My Fines page
    @GetMapping("/members/fines")
    public String fines(@RequestParam("memberId") int memberId, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        model.addAttribute("member", memberRepository.getMemberById(memberId));
        return "member/fines";
    }

    // Pay fine action
    @PostMapping("/members/fines/pay")
    public String payFine(@RequestParam("memberId") int memberId,
                          @RequestParam("amount") double amount,
                          HttpSession session,
                          RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        var m = memberRepository.getMemberById(memberId);
        double newFines = m.getFines() - Math.max(0, amount);
        if (newFines < 0) newFines = 0;
        m.setFines(newFines);
        memberRepository.updateMember(m);
        ra.addFlashAttribute("message", "Payment applied.");
        return "redirect:/members/fines?memberId=" + memberId;
    }

    // Profile page (simple)
    @GetMapping("/members/profile")
    public String profile(@RequestParam("memberId") int memberId, Model model, HttpSession session, RedirectAttributes ra) {
        if (!"MEMBER".equals(session.getAttribute("role"))) {
            ra.addFlashAttribute("message", "Access denied: Member only.");
            return "redirect:/";
        }
        model.addAttribute("member", memberRepository.getMemberById(memberId));
        return "member/profile";
    }
}
