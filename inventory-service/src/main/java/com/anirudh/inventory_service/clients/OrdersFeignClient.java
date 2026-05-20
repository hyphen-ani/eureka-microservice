package com.anirudh.inventory_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "orders-service", path = "/orders")
public interface OrdersFeignClient {

    @PutMapping("/core/helloOrders")
    String getHelloFromOrderService();
}

