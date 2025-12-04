package com.library.library_app.web;

import com.library.library_app.repositories.MemberRepository;
import com.library.library_app.repositories.StaffRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final MemberRepository memberRepo;
    private final StaffRepository staffRepo;

    public LoginController(MemberRepository memberRepo, StaffRepository staffRepo) {
        this.memberRepo = memberRepo;
        this.staffRepo = staffRepo;
    }

    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping("/auth/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        // Try member
        var members = memberRepo.getAllMembers();
        var member = members.stream().filter(m -> email.equalsIgnoreCase(m.getEmail()) && password.equals(m.getPassword())).findFirst().orElse(null);
        if (member != null) {
            session.setAttribute("role", "MEMBER");
            session.setAttribute("memberId", member.getId());
            session.setAttribute("displayName", member.getName());
            redirectAttributes.addFlashAttribute("memberName", member.getName());
            redirectAttributes.addFlashAttribute("memberId", member.getId());
            return "redirect:/members";
        }
        // Try staff
        var staffList = staffRepo.getAllStaff();
        var staff = staffList.stream().filter(s -> email.equalsIgnoreCase(s.getEmail()) && password.equals(s.getPassword())).findFirst().orElse(null);
        if (staff != null) {
            session.setAttribute("role", "STAFF");
            session.setAttribute("staffId", staff.getId());
            session.setAttribute("displayName", staff.getName());
            session.setAttribute("staffRole", staff.getRole());
            return "redirect:/staff";
        }
        redirectAttributes.addFlashAttribute("message", "Invalid credentials.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "Logged out.");
        return "redirect:/";
    }
}
