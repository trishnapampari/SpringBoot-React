package com.example.MyWebApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.MyWebApplication.model.Product;
import com.example.MyWebApplication.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/products/{prodid}")
    public ResponseEntity<Product> getProductById(@PathVariable int prodid) {
       System.out.println("Product ID: " + prodid);
        Product product = productService.getProductById(prodid);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProducts(@RequestPart Product prod,@RequestPart MultipartFile imageFile) {
        System.out.println("Adding product: " + prod);
        try{
        Product product=  productService.addProducts(prod,imageFile);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
            }catch(Exception e){
                e.printStackTrace();
                return new ResponseEntity<>("Failed to add product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @PutMapping("/products/{prodid}")
    public ResponseEntity<String> updateProduct(@PathVariable int prodid, @RequestPart Product prod,MultipartFile imageFile) {
       // prod.setProductid(prodid);
        try {
           String product=productService.updateProduct(prodid,prod,imageFile);
           return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update product", HttpStatus.BAD_REQUEST);
        }
       
    }

    @DeleteMapping("/products/{prodid}")
    public ResponseEntity<String> deleteProduct(@PathVariable int prodid){
      Product product= productService.getProductById(prodid);
      if(product!=null){
         productService.deleteProduct(prodid);
         return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);

        }else{
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/{prodid}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int prodid) {
       Product product =productService.getProductById(prodid);
       byte[] imageData = product.getImageData();

       return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
               .body(imageData);

    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products=productService.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }


}
