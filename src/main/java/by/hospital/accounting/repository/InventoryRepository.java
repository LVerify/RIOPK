package by.hospital.accounting.repository;

import by.hospital.accounting.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryRecord, Long> {
    List<InventoryRecord> findByMedicine(Medicine medicine);
    List<InventoryRecord> findByRecordType(String recordType);
}
