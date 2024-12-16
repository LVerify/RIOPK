package by.hospital.doctor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DepartmentCreateUpdateDTO {
  @NotEmpty private String name;
}
