package by.hospital.doctor.service.mapper;

import by.hospital.doctor.dto.DepartmentCreateUpdateDTO;
import by.hospital.doctor.dto.DepartmentReadDTO;
import by.hospital.doctor.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
  Department dtoToEntity(DepartmentCreateUpdateDTO dto);

  DepartmentReadDTO entityToDto(Department department);

  void updateEntity(DepartmentCreateUpdateDTO dto, @MappingTarget Department department);
}
