package by.hospital.patient.repository;

import by.hospital.patient.entity.Patient;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {
  boolean existsByDateOfBirthAndMobilePhone(LocalDate dateOfBirth, String mobilePhone);
}
