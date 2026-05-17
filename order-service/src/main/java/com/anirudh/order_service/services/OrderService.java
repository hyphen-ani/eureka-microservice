package com.anirudh.order_service.services;

import com.anirudh.order_service.dto.OrderRequestDTO;
import com.anirudh.order_service.entity.Orders;
import com.anirudh.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;


    public List<OrderRequestDTO> getAllOrders(){
        log.info("Fetching all orders...");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderRequestDTO.class)).toList();
    }

    public OrderRequestDTO getOrderById(Long id){
        log.info("Fetching all order for id {}", id);
        Orders orders = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not Found")
        );

        return modelMapper.map(orders, OrderRequestDTO.class);
    }




}
