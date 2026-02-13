package org.vevra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.vevra.entity.ContactMessage;
import org.vevra.entity.QuoteRequest;
import org.vevra.repository.ContactMessageRepository;
import org.vevra.repository.QuoteRequestRepository;

@Controller
public class ContactController {

    private final ContactMessageRepository contactRepo;
    private final QuoteRequestRepository quoteRepo;

    public ContactController(ContactMessageRepository contactRepo,
                             QuoteRequestRepository quoteRepo) {
        this.contactRepo = contactRepo;
        this.quoteRepo = quoteRepo;
    }

    // PUBLIC CONTACT PAGE
    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("contact", new ContactMessage());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContact(@ModelAttribute("contact") ContactMessage contactMessage,
                                Model model) {

        contactRepo.save(contactMessage);
        model.addAttribute("success", "Thanks! We received your message.");
        model.addAttribute("contact", new ContactMessage());
        return "contact";
    }

    // PUBLIC QUOTE PAGE
    @GetMapping("/quote")
    public String quotePage(Model model) {
        model.addAttribute("quote", new QuoteRequest());
        return "quote";
    }

    @PostMapping("/quote")
    public String submitQuote(@ModelAttribute("quote") QuoteRequest quoteRequest,
                              Model model) {

        quoteRepo.save(quoteRequest);
        model.addAttribute("success", "Thanks! Your quote request is submitted.");
        model.addAttribute("quote", new QuoteRequest());
        return "quote";
    }
}
