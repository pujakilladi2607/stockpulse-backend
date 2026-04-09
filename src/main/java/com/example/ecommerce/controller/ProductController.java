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

    // ❌ Delete ALL Products
    @GetMapping("/delete-all")
    public String deleteAllProducts() {
        productRepository.deleteAll();
        return "All products deleted";
    }

    // ✏️ Update Product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return null;

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setDescription(updatedProduct.getDescription());
        product.setCategory(updatedProduct.getCategory());
        product.setImage(updatedProduct.getImage());

        return productRepository.save(product);
    }

    // 🔥 Add Sample Products (5 categories × 3 products)
    @GetMapping("/add-sample")
    public String addSampleProducts() {

        // Footwear
        productRepository.save(new Product("Running Shoes", 999, 10, "Shoes",
                "Footwear",
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400"));

        productRepository.save(new Product("Sneakers", 1299, 8, "Sneakers",
                "Footwear",
                "https://images.unsplash.com/photo-1528701800489-20be3c6c2f87?w=400"));

        productRepository.save(new Product("Sandals", 499, 15, "Sandals",
                "Footwear",
                "https://images.unsplash.com/photo-1600185365483-26d7a4cc7519?w=400"));

        // Clothing
        productRepository.save(new Product("Casual Shirt", 499, 15, "Shirt",
                "Clothing",
                "https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=400"));

        productRepository.save(new Product("T-Shirt", 399, 20, "Tshirt",
                "Clothing",
                "https://images.unsplash.com/photo-1520975916090-3105956dac38?w=400"));

        productRepository.save(new Product("Jeans", 899, 12, "Jeans",
                "Clothing",
                "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=400"));

        // Electronics
        productRepository.save(new Product("Smart Watch", 1999, 8, "Watch",
                "Electronics",
                "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400"));

        productRepository.save(new Product("Headphones", 1499, 10, "Headphones",
                "Electronics",
                "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400"));

        productRepository.save(new Product("Speaker", 999, 9, "Speaker",
                "Electronics",
                "https://images.unsplash.com/photo-1585386959984-a4155224a1ad?w=400"));

        // Bags
        productRepository.save(new Product("Backpack", 799, 12, "Bag",
                "Bags",
                "https://images.unsplash.com/photo-1547949003-9792a18a2601?w=400"));

        productRepository.save(new Product("Travel Bag", 1199, 6, "Bag",
                "Bags",
                "https://images.unsplash.com/photo-1502920917128-1aa500764b06?w=400"));

        productRepository.save(new Product("Handbag", 999, 10, "Bag",
                "Bags",
                "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=400"));

        // Accessories
        productRepository.save(new Product("Watch", 899, 10, "Watch",
                "Accessories",
                "https://images.unsplash.com/photo-1524592094714-0f0654e20314?w=400"));

        productRepository.save(new Product("Sunglasses", 599, 14, "Glasses",
                "Accessories",
                "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=400"));

        productRepository.save(new Product("Cap", 299, 20, "Cap",
                "Accessories",
                "https://images.unsplash.com/photo-1521369909029-2afed882baee?w=400"));

        return "Fresh products added";
    }
}