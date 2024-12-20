package by.hospital.disease.service.mapper;

import by.hospital.disease.dto.DiseaseCreateUpdateDTO;
import by.hospital.disease.dto.DiseaseReadDTO;
import by.hospital.disease.entity.Disease;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiseaseMapper {
  Disease toEntity(DiseaseCreateUpdateDTO dto);

  DiseaseReadDTO toDto(Disease disease);

  void updateEntity(DiseaseCreateUpdateDTO dto, @MappingTarget Disease disease);
}
