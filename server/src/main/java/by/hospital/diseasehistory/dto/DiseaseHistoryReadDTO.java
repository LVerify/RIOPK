package by.hospital.diseasehistory.dto;

import by.hospital.disease.dto.DiseaseReadDTO;
import java.util.List;
import lombok.Data;

@Data
public class DiseaseHistoryReadDTO {
  private String id;
  private DiseaseReadDTO disease;
  private List<PrescriptionReadDTO> prescriptions;
  private String conclusion;
}
