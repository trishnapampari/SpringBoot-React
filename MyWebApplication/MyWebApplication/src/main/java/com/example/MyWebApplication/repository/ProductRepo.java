package com.example.MyWebApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.MyWebApplication.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("Select p from Product p where " +
            "LOWER(p.productname) like LOWER(CONCAT('%', :keyword, '%')) or " +
            "LOWER(p.description) like LOWER(CONCAT('%', :keyword, '%')) or " +
            "LOWER(p.brand) like LOWER(CONCAT('%', :keyword, '%')) or " +
            "LOWER(p.category) like LOWER(CONCAT('%', :keyword, '%'))") 
    List<Product> searchProducts(String keyword);
}
