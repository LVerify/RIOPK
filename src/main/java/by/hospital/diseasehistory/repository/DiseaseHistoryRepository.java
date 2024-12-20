package by.hospital.diseasehistory.repository;

import by.hospital.diseasehistory.entity.DiseaseHistory;
import by.hospital.diseasehistory.entity.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseHistoryRepository extends JpaRepository<DiseaseHistory, String> {
  List<DiseaseHistory> findAllByPatient(Patient patient);
}
