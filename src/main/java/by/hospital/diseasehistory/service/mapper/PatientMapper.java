package by.hospital.diseasehistory.service.mapper;

import by.hospital.disease.dto.DiseaseReadDTO;
import by.hospital.diseasehistory.dto.DiseaseHistoryReadDTO;
import by.hospital.diseasehistory.dto.PatientCreateUpdateDTO;
import by.hospital.diseasehistory.dto.PatientReadDTO;
import by.hospital.diseasehistory.entity.DiseaseHistory;
import by.hospital.diseasehistory.entity.Patient;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

  Patient toEntity(PatientCreateUpdateDTO dto);

  PatientReadDTO toDto(Patient patient);

  void updateEntity(PatientCreateUpdateDTO dto, @MappingTarget Patient patient);

  DiseaseReadDTO toDto(DiseaseHistory diseaseHistory);

  List<DiseaseHistoryReadDTO> toDto(List<DiseaseHistory> diseaseHistories);
}
