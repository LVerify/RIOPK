package by.hospital.accounting.controller;


import by.hospital.accounting.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryRecord>> getAllInventoryRecords() {
        List<InventoryRecord> records = inventoryService.getAllInventoryRecords();
        return ResponseEntity.ok(records);
    }

    @PostMapping
    public ResponseEntity<InventoryRecord> createInventoryRecord(@RequestBody InventoryRecord record) {
        InventoryRecord createdRecord = inventoryService.createInventoryRecord(record);
        return ResponseEntity.ok(createdRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryRecord(@PathVariable Long id) {
        inventoryService.deleteInventoryRecord(id);
        return ResponseEntity.noContent().build();
    }
}
