package org.vevra.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.vevra.entity.Product;
import org.vevra.repository.ProductRepository;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // =========================
    // PUBLIC PRODUCTS PAGE
    // =========================
    @GetMapping("/products")
    public String productsPage(@RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "") String category,
                               @RequestParam(defaultValue = "") String size,
                               Model model) {

        model.addAttribute("products",
                productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndSizeContainingIgnoreCase(
                        search, category, size
                )
        );

        model.addAttribute("search", search);
        model.addAttribute("category", category);
        model.addAttribute("size", size);
        model.addAttribute("categories", new String[]{"PET", "Steel", "Glass", "Copper"});
        model.addAttribute("sizes", new String[]{"250ml", "500ml", "750ml", "1L", "2L"});

        return "products";
    }


    // =========================
    // ADMIN: LIST PRODUCTS
    // =========================
    @GetMapping("/admin/products")
    public String adminProducts(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("products", productRepository.findAll());
        return "admin-products";
    }

    // =========================
    // ADMIN: ADD PRODUCT PAGE
    // =========================
    @GetMapping("/admin/products/add")
    public String addProductPage(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("product", new Product());
        return "admin-product-form";
    }

    // =========================
    // ADMIN: SAVE PRODUCT
    // =========================
    @PostMapping("/admin/products/save")
    public String saveProduct(HttpSession session, @ModelAttribute Product product) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // =========================
    // ADMIN: EDIT PRODUCT PAGE
    // =========================
    @GetMapping("/admin/products/edit/{id}")
    public String editProductPage(HttpSession session, @PathVariable Long id, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/admin/products";
        }

        model.addAttribute("product", product);
        return "admin-product-form";
    }

    // =========================
    // ADMIN: DELETE PRODUCT
    // =========================
    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(HttpSession session, @PathVariable Long id) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}
