package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE,
        RequestMethod.OPTIONS
})
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // 🛒 Place Order
    @PostMapping
    public String placeOrder(@RequestBody Order order) {

        System.out.println("🔥 ORDER RECEIVED: " + order);

        double total = 0;

        for (OrderItem item : order.getItems()) {

            Optional<Product> optionalProduct = productRepository.findById(item.getProductId());

            // ❌ Product not found
            if (!optionalProduct.isPresent()) {
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
            int updatedStock = product.getStock() - item.getQuantity();
            product.setStock(updatedStock);
            productRepository.save(product);

            // ✅ Calculate total
            total += product.getPrice() * item.getQuantity();
        }

        // ✅ Set order details
        order.setTotalAmount(total);
        order.setStatus("PLACED");

        System.out.println("✅ USER EMAIL: " + order.getUserEmail());

        Order savedOrder = orderRepository.save(order);

        System.out.println("💾 SAVED ORDER: " + savedOrder);

        return "Order placed successfully. Order ID: " + savedOrder.getId();
    }

    // 📄 Get all orders
    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    // 🔍 Get single order
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderRepository.findById(id).orElse(null);
    }

    // 🔄 Update order status
    @PutMapping("/{id}")
    public String updateStatus(@PathVariable String id, @RequestParam String status) {

        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return "Order not found";
        }

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