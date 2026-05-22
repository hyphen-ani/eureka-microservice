package com.anirudh.order_service.controller;

import com.anirudh.order_service.clients.InventoryOpenFeignClient;
import com.anirudh.order_service.dto.OrderRequestDTO;
import com.anirudh.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class OrderController {

    private final OrderService orderService;

    @Value("${my.variable}")
    private String variable;


    @GetMapping("/helloOrders")
    public String helloOrders(){
        return "Hello, Order Service Here, Variable" + variable;
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders(){
        log.info("Fetching all orders via controller...");
        List<OrderRequestDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> getOrderById(@PathVariable Long id){
        log.info("Fetching order of id, {}", id);
        OrderRequestDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        OrderRequestDTO orderRequest = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(orderRequest);
    }





}
