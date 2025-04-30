package by.hospital.disease.repository;

import by.hospital.disease.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, String> {
  boolean existsByName(String name);
}
