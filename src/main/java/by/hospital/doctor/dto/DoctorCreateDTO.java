package by.hospital.doctor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DoctorCreateDTO {
  @NotEmpty private String firstName;
  @NotEmpty private String lastName;
  private String patronymicName;
  @NotEmpty private String department;
  @NotEmpty private String speciality;
  @NotEmpty private String mobilePhone;
}
