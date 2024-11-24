package by.hospital.accounting.repository;

import by.hospital.accounting.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Дополнительные методы, например:
    List<Patient> findByLastName(String lastName);
}
