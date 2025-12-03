package com.library.library_app.web;

import com.library.library_app.repositories.MemberRepository;
import com.library.library_app.models.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MemberWebController {
    private final MemberRepository memberRepo;

    public MemberWebController(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @GetMapping("/members")
    public String listMembers(Model model) {
        model.addAttribute("members", memberRepo.getAllMembers());
        return "members";
    }

    @GetMapping("/members/{id}")
    public String memberDetail(@PathVariable("id") int id, Model model) {
        Member member = memberRepo.getMemberById(id);
        model.addAttribute("member", member);
        // TODO: Add borrowed books for this member
        return "member_detail";
    }
}
