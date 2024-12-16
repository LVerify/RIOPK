package by.hospital.doctor.service.mapper;

import by.hospital.doctor.dto.SpecialityCreateUpdateDTO;
import by.hospital.doctor.dto.SpecialityReadDTO;
import by.hospital.doctor.entity.Speciality;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialityMapper {
  Speciality dtoToEntity(SpecialityCreateUpdateDTO dto);

  SpecialityReadDTO entityToDto(Speciality speciality);

  void updateEntity(SpecialityCreateUpdateDTO dto, @MappingTarget Speciality speciality);
}
