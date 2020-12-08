package com.devlabs.ecommerce.inventory.repository;

import com.devlabs.ecommerce.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}