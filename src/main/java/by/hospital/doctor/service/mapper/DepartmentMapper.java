package by.hospital.doctor.service.mapper;

import by.hospital.doctor.dto.DepartmentCreateUpdateDTO;
import by.hospital.doctor.dto.DepartmentReadDTO;
import by.hospital.doctor.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
  Department toEntity(DepartmentCreateUpdateDTO dto);

  DepartmentReadDTO toDto(Department department);

  void updateEntity(DepartmentCreateUpdateDTO dto, @MappingTarget Department department);
}
