package by.hospital.diseasehistory.dto;

import by.hospital.diseasehistory.entity.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PatientCreateUpdateDTO {
  @NotEmpty private String firstName;
  @NotEmpty private String lastName;
  private String patronymicName;
  @NotNull private LocalDate dateOfBirth;
  @NotNull private Gender gender;
  @NotEmpty private String mobilePhone;
  @NotEmpty private String address;
}
