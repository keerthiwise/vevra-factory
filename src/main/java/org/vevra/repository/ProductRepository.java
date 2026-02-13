package org.vevra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vevra.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryContainingIgnoreCase(String category);

    List<Product> findBySizeContainingIgnoreCase(String size);

    List<Product> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndSizeContainingIgnoreCase(
            String name,
            String category,
            String size
    );
}
