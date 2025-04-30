package by.hospital.doctor.dto;

import by.hospital.user.dto.UserReadDTO;
import lombok.Data;

@Data
public class DoctorReadDTO {
  private String id;
  private UserReadDTO user;
  private String firstName;
  private String lastName;
  private String patronymicName;
  private DepartmentReadDTO department;
  private SpecialityReadDTO speciality;
  private String mobilePhone;
}
