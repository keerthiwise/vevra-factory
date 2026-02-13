package org.vevra.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.vevra.entity.Admin;
import org.vevra.repository.AdminRepository;
import org.vevra.repository.ContactMessageRepository;
import org.vevra.repository.QuoteRequestRepository;

@Controller
public class AdminController {

    private final AdminRepository adminRepository;
    private final ContactMessageRepository contactRepo;
    private final QuoteRequestRepository quoteRepo;

    public AdminController(AdminRepository adminRepository,
                           ContactMessageRepository contactRepo,
                           QuoteRequestRepository quoteRepo) {
        this.adminRepository = adminRepository;
        this.contactRepo = contactRepo;
        this.quoteRepo = quoteRepo;
    }

    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String username,
                             @RequestParam String password,
                             HttpSession session,
                             Model model) {

        Admin admin = adminRepository.findByUsernameAndPassword(username, password).orElse(null);

        if (admin == null) {
            model.addAttribute("error", "Invalid username or password");
            return "admin-login";
        }

        session.setAttribute("admin", admin.getUsername());
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin-dashboard";
    }

    @GetMapping("/admin/messages")
    public String viewMessages(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("messages", contactRepo.findAll());
        return "admin-messages";
    }

    @GetMapping("/admin/quotes")
    public String viewQuotes(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("quotes", quoteRepo.findAll());
        return "admin-quotes";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
