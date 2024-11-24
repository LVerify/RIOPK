package by.hospital.accounting.repository;

import by.hospital.accounting.model.Prescription;
import by.hospital.accounting.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatient(Patient patient);
}
