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
import org.springframework.stereotype.Component;

@Component
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface DoctorMapper {
  Doctor dtoToEntity(DoctorCreateDTO dto);

  DoctorReadDTO entityToDto(Doctor doctor);

  void updateEntity(DoctorUpdateDTO dto, @MappingTarget Doctor doctor);

  DepartmentReadDTO departmentEntityToDto(Department department);

  SpecialityReadDTO specialityEntityToDto(Speciality speciality);

  UserReadDTO userEntityToDto(User user);
}
