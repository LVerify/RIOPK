package by.hospital.doctor.service.mapper;

import by.hospital.doctor.dto.*;
import by.hospital.doctor.entity.Department;
import by.hospital.doctor.entity.Doctor;
import by.hospital.doctor.entity.Speciality;
import by.hospital.user.dto.UserReadDTO;
import by.hospital.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface DoctorMapper {
  Doctor toEntity(DoctorCreateDTO dto);

  DoctorReadDTO toDto(Doctor doctor);

  void updateEntity(DoctorUpdateDTO dto, @MappingTarget Doctor doctor);

  DepartmentReadDTO toDto(Department department);

  SpecialityReadDTO toDto(Speciality speciality);

  UserReadDTO toDto(User user);
}
