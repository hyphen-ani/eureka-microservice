package com.anirudh.inventory_service.controller;

import com.anirudh.inventory_service.dto.ProductDTO;
import com.anirudh.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/fetch-order")
    public String fetchFromOrderService(){
        ServiceInstance orderService = discoveryClient.getInstances("orders-service").getFirst();

        return restClient.get()
                .uri(orderService.getUri() + "/orders/core/helloOrders")
                .retrieve()
                .body(String.class);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllInventory(){
        List<ProductDTO> products = productService.getAllInventory();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
