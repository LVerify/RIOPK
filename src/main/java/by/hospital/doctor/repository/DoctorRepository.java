package by.hospital.doctor.repository;

import by.hospital.doctor.entity.Doctor;
import by.hospital.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
  boolean existsByUser(User user);
}
