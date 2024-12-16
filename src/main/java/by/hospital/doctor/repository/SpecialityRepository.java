package by.hospital.doctor.repository;

import by.hospital.doctor.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, String> {
  boolean existsByName(String name);
}
