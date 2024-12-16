package by.hospital.patient.dto;

import by.hospital.patient.entity.Gender;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PatientReadDTO {
  private String id;
  private String firstName;
  private String lastName;
  private String patronymicName;
  private LocalDate dateOfBirth;
  private Gender gender;
  private String mobilePhone;
  private String address;
}
