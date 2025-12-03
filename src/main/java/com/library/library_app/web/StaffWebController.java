package com.library.library_app.web;

import com.library.library_app.repositories.StaffRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaffWebController {
    private final StaffRepository staffRepo;

    public StaffWebController(StaffRepository staffRepo) {
        this.staffRepo = staffRepo;
    }

    @GetMapping("/staff")
    public String listStaff(Model model) {
        model.addAttribute("staff", staffRepo.getAllStaff());
        return "staff";
    }
}
