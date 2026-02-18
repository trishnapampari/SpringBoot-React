package com.example.MyWebApplication.service;
import com.example.MyWebApplication.model.Product;
import com.example.MyWebApplication.repository.ProductRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }   

    public Product getProductById(int prodid) {
        return productRepo.findById(prodid).orElse(null);
    }

    public Product addProducts(Product prod,MultipartFile imageFile) {
        try {
            prod.setImageName(imageFile.getOriginalFilename());
            prod.setImageType(imageFile.getContentType());
            prod.setImageData(imageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productRepo.save(prod);
    }

    public String updateProduct(int id ,Product prod,MultipartFile imageFile) {
        if (!productRepo.existsById(id)) {
            return "Product not found";
        }
        try {
            prod.setImageName(imageFile.getOriginalFilename());
            prod.setImageType(imageFile.getContentType());
            prod.setImageData(imageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
       productRepo.save(prod);
       return "Product updated successfully";
    } 

    public String deleteProduct(int prodid) {
        if (productRepo.existsById(prodid)) {
            productRepo.deleteById(prodid);
            return "Product deleted successfully";
        } 
        return "Product not found";
    }

    public List<Product> searchProducts(String keyword) {
       return productRepo.searchProducts(keyword);
    }

}
