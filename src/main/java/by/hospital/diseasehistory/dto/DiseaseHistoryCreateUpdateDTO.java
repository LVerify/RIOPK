package by.hospital.diseasehistory.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DiseaseHistoryCreateUpdateDTO {
  @NotEmpty private String diseaseId;
  @NotEmpty private String conclusion;
}
