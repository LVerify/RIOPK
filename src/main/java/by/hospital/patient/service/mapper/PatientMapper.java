package by.hospital.patient.service.mapper;

import by.hospital.patient.dto.PatientCreateUpdateDTO;
import by.hospital.patient.dto.PatientReadDTO;
import by.hospital.patient.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

  Patient dtoToEntity(PatientCreateUpdateDTO dto);

  PatientReadDTO entityToDto(Patient patient);

  void updateEntity(PatientCreateUpdateDTO dto, @MappingTarget Patient patient);
}
