package by.hospital.diseasehistory.dto;

import by.hospital.diseasehistory.entity.PrescriptionStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrescriptionUpdateDTO {
  @NotEmpty private String medicineId;
  @Positive private Double medicineAmount;
  @NotEmpty private String doctorId;
  @NotNull private String description;
  private PrescriptionStatus status;
}
