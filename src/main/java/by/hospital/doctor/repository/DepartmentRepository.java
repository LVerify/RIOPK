package by.hospital.doctor.repository;

import by.hospital.doctor.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
  boolean existsByName(String name);
}
