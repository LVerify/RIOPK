package by.hospital.disease.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DiseaseCreateUpdateDTO {
  @NotEmpty private String name;
  @NotEmpty private String description;
  @NotEmpty private String type;
}
