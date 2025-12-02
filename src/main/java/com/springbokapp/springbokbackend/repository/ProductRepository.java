package com.springbokapp.springbokbackend.repository;

import com.springbokapp.springbokbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
