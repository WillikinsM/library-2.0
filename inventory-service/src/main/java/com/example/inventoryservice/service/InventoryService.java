package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryDetails;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.exceptions.NotFoundException;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRespository inventoryRespository;

    public InventoryService(InventoryRespository inventoryRespository) {
        this.inventoryRespository = inventoryRespository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> bookIds){
        return inventoryRespository.findByBookIdIn(bookIds)
                .stream()
                .map(inventory ->
                    new InventoryResponse(inventory.getBookId(), inventory.getQuantity() > 0)
                ).toList();
    }

    public InventoryDetails findByBookId(String bookId){
        return new InventoryDetails(inventoryRespository.findInventoryByBookId(bookId).orElseThrow(()-> new NotFoundException("Object not found")));
    }

    public InventoryDetails create(Inventory inventory){
        return new InventoryDetails(inventoryRespository.save(inventory));
    }

    public InventoryDetails update(Inventory inventory){
        return new InventoryDetails(inventoryRespository.save(inventory));
    }

}
