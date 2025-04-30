package by.hospital.patient.repository;

import by.hospital.patient.entity.Patient;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
  boolean existsByDateOfBirthAndMobilePhone(LocalDate dateOfBirth, String mobilePhone);
}
