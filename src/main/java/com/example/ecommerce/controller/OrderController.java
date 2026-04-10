package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // 🛒 Place Order
    @PostMapping
    public String placeOrder(@RequestBody Order order) {

        double total = 0;

        for (OrderItem item : order.getItems()) {

            Optional<Product> optionalProduct = productRepository.findById(item.getProductId());

            // ❌ Product not found
            if (optionalProduct.isEmpty()) {
                return "Product not found with ID: " + item.getProductId();
            }

            Product product = optionalProduct.get();

            // ❌ Out of stock
            if (product.getStock() <= 0) {
                return "Product out of stock: " + product.getName();
            }

            // ❌ Not enough stock
            if (product.getStock() < item.getQuantity()) {
                return "Only " + product.getStock() + " items left for " + product.getName();
            }

            // ✅ Reduce stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            // ✅ Calculate total
            total += product.getPrice() * item.getQuantity();
        }

        order.setTotalAmount(total);
        order.setStatus("PLACED");

        Order savedOrder = orderRepository.save(order);

        return "Order placed successfully. Order ID: " + savedOrder.getId();
    }

    // 📄 Get all orders
    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    // 🔍 Get order by ID
    @GetMapping("/{id}")
    public Object getOrderById(@PathVariable String id) {

        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isEmpty()) {
            return "Order not found";
        }

        return optionalOrder.get();
    }

    // 🔄 Update status
    @PutMapping("/{id}")
    public String updateStatus(@PathVariable String id, @RequestParam String status) {

        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isEmpty()) {
            return "Order not found";
        }

        Order order = optionalOrder.get();
        order.setStatus(status);
        orderRepository.save(order);

        return "Order status updated to " + status;
    }

    // ❌ Delete order
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable String id) {
        orderRepository.deleteById(id);
        return "Order deleted successfully";
    }
}