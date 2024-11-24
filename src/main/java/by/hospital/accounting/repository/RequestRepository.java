package by.hospital.accounting.repository;

import by.hospital.accounting.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequestType(String requestType);
    List<Request> findByIsApproved(boolean isApproved);
}