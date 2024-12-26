package by.hospital.diseasehistory.dto;

import by.hospital.diseasehistory.entity.Gender;
import java.time.LocalDate;
import java.util.List;
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
  private List<DiseaseHistoryReadDTO> diseaseHistories;
}
