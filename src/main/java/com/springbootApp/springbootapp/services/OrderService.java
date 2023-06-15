package com.springbootApp.springbootapp.services;


import com.springbootApp.springbootapp.dao.OrderRepository;

import com.springbootApp.springbootapp.entity.FoodItem;
import com.springbootApp.springbootapp.entity.Order;


import com.springbootApp.springbootapp.entity.User;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.NoSuchElementException;

@Service
//@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
    }

  //  public Order createOrder(Order order) {
//        return orderRepository.save(order);
//    }
  public Order createOrder(Order order) {
      User user = order.getUser();
      List<FoodItem> foodItems = order.getFoodItems();

      // Set the user and food items for the order
      order.setUser(user);
      order.setFoodItems(foodItems);

      return orderRepository.save(order);
  }

    public Order updateOrder(Long id, Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Other methods as needed
    // ...
}
