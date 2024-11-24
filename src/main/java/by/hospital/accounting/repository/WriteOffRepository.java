package by.hospital.accounting.repository;

import by.hospital.accounting.model.WriteOffRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface WriteOffRepository extends JpaRepository<WriteOffRecord, Long> {
    List<WriteOffRecord> findByWriteOffDate(Date writeOffDate);
    List<WriteOffRecord> findByReasonContaining(String reason);
}
