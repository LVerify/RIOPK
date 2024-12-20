package by.hospital.doctor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DoctorUpdateDTO {
  @NotEmpty private String firstName;
  @NotEmpty private String lastName;
  private String patronymicName;
  @NotEmpty private String departmentId;
  @NotEmpty private String specialityId;
  @NotEmpty private String mobilePhone;
}
