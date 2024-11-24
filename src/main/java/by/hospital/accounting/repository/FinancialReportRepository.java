package by.hospital.accounting.repository;

import by.hospital.accounting.model.FinancialReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface FinancialReportRepository extends JpaRepository<FinancialReport, Long> {
    List<FinancialReport> findByReportDateBetween(Date startDate, Date endDate);
}
