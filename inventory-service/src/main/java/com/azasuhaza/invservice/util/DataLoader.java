package com.azasuhaza.invservice.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.azasuhaza.invservice.model.Inventory;
import com.azasuhaza.invservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;
    @Override
    public void run(String... args) throws Exception {
        Inventory inventory = new Inventory();
        inventory.setSkuCode("AZASUHAZA01");
        inventory.setQuantity(100);

        Inventory inventory1 = new Inventory();
        inventory1.setSkuCode("AZASUHAZA02");
        inventory1.setQuantity(0);

        inventoryRepository.save(inventory);
        inventoryRepository.save(inventory1);
    }
}
