package com.anirudh.inventory_service.service;

import com.anirudh.inventory_service.dto.OrderRequestDTO;
import com.anirudh.inventory_service.dto.OrderRequestItemDTO;
import com.anirudh.inventory_service.dto.ProductDTO;
import com.anirudh.inventory_service.entity.Product;
import com.anirudh.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllInventory(){
        log.info("Fetching all inventory items");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    public ProductDTO getProductById(Long id){
        log.info("Fetching Product with ID: {}", id);
        Optional<Product> inventory = productRepository.findById(id);
        return inventory.map(item -> modelMapper.map(item, ProductDTO.class))
                .orElseThrow(() -> new RuntimeException("Inventory not Found"));
    }

    @Transactional
    public Double reduceStocks(OrderRequestDTO orderRequestDTO) {

        log.info("Reducing Stocks");

        Double totalPrice = 0.0;
        for(OrderRequestItemDTO orderRequestItemDTO: orderRequestDTO.getItems()){
            Long productId = orderRequestItemDTO.getProductId();
            Integer quantity = orderRequestItemDTO.getQuantity();

            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new RuntimeException("Product not found")
            );

            if(product.getStock() < quantity){
                throw new RuntimeException("Product cannot be fulfilled for given quantity");
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            totalPrice = quantity * product.getPrice();
        }

        return totalPrice;

    }
}
