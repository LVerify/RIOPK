package by.hospital.doctor.repository;

import by.hospital.doctor.entity.Doctor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
  boolean existsByUser_Name(String userId);

  Optional<Doctor> getByUser_Id(String userId);
}
