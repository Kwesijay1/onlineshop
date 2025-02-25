package com.ontrak.onlineshop.services;

import com.ontrak.onlineshop.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Integer> {


}
