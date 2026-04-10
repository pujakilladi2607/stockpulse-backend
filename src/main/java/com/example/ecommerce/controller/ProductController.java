package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // ➕ Add Product
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 📄 Get All Products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ❌ Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
        return "Product deleted";
    }

    // ❌ Delete ALL Products (FIXED)
    @DeleteMapping("/delete-all")
    public String deleteAllProducts() {
        productRepository.deleteAll();
        return "All products deleted";
    }

    // ✏️ Update Product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {

        return productRepository.findById(id).map(product -> {

            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            product.setDescription(updatedProduct.getDescription());
            product.setCategory(updatedProduct.getCategory());
            product.setImage(updatedProduct.getImage());

            return productRepository.save(product);

        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // 🔥 Add Sample Products
    @GetMapping("/add-sample")
    public String addSampleProducts() {

        productRepository.save(new Product("Running Shoes", 999, 10, "Shoes", "Footwear",
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400"));

        productRepository.save(new Product("Sneakers", 1299, 8, "Sneakers", "Footwear",
                "https://images.unsplash.com/photo-1528701800489-20be3c6c2f87?w=400"));

        productRepository.save(new Product("Sandals", 499, 15, "Sandals", "Footwear",
                "https://images.unsplash.com/photo-1600185365483-26d7a4cc7519?w=400"));

        return "Sample products added";
    }

    // 🔍 DB Test
    @GetMapping("/test-db")
    public String testDB() {
        try {
            productRepository.findAll();
            return "DB Connected ✅";
        } catch (Exception e) {
            return "DB NOT Connected ❌";
        }
    }
}