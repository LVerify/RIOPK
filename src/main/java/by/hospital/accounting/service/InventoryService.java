package by.hospital.accounting.service;

import by.hospital.accounting.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryRecord> getAllInventoryRecords() {
        return inventoryRepository.findAll();
    }

    public InventoryRecord createInventoryRecord(InventoryRecord record) {
        return inventoryRepository.save(record);
    }

    public void deleteInventoryRecord(Long id) {
        inventoryRepository.deleteById(id);
    }
}
