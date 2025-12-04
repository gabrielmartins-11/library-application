package com.library.library_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.library.library_app.repositories.MemberRepository;
import com.library.library_app.repositories.StaffRepository;
import com.library.library_app.models.Member;
import com.library.library_app.models.Staff;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;

    public HomeController(MemberRepository memberRepository, StaffRepository staffRepository) {
        this.memberRepository = memberRepository;
        this.staffRepository = staffRepository;
    }

    // Make root URL show the login page
    @GetMapping("/")
    public String home(Model model) {
        // Optional message placeholder
        return "login"; // resolves to templates/login.html
    }

    // Handle POST login with DB-backed auth
    @PostMapping("/login")
    public String doLogin(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            RedirectAttributes redirectAttributes) {

        if (role != null && role.equalsIgnoreCase("staff")) {
            Staff staff = staffRepository.findByEmailAndPassword(email, password);
            if (staff == null) {
                redirectAttributes.addFlashAttribute("message", "Invalid staff credentials");
                return "redirect:/login";
            }
            return "redirect:/staff";
        } else {
            Member member = memberRepository.findByEmailAndPassword(email, password);
            if (member == null) {
                redirectAttributes.addFlashAttribute("message", "Invalid member credentials");
                return "redirect:/login";
            }
            redirectAttributes.addFlashAttribute("memberName", member.getName());
            redirectAttributes.addFlashAttribute("memberId", member.getId());
            return "redirect:/members";
        }
    }

    @GetMapping("/books_borrowed")
    public String booksBorrowedRedirect(HttpSession session, RedirectAttributes ra) {
        Object role = session.getAttribute("role");
        if ("MEMBER".equals(role)) {
            Object memberId = session.getAttribute("memberId");
            if (memberId == null) {
                ra.addFlashAttribute("message", "Missing member session.");
                return "redirect:/";
            }
            return "redirect:/members/borrowed?memberId=" + memberId;
        }
        if ("STAFF".equals(role)) {
            return "redirect:/staff/borrowed";
        }
        ra.addFlashAttribute("message", "Please log in.");
        return "redirect:/";
    }
}
