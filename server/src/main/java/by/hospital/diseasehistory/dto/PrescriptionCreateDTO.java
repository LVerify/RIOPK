package by.hospital.diseasehistory.dto;

import by.hospital.diseasehistory.entity.PrescriptionStatus;
import by.hospital.diseasehistory.entity.statusconstraint.AllowedStatuses;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrescriptionCreateDTO {
  @NotEmpty private String medicineId;
  @Positive private Double medicineAmount;
  @NotEmpty private String doctorId;
  @NotNull private String description;

  @Schema(
      description = "Статус назначения",
      allowableValues = {"CREATED", "APPROVED", "CANCELLED"})
  @AllowedStatuses({PrescriptionStatus.CREATED, PrescriptionStatus.APPROVED})
  private PrescriptionStatus status;
}
