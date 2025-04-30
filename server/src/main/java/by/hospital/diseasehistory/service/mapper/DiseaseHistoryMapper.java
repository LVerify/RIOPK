package by.hospital.diseasehistory.service.mapper;

import by.hospital.disease.dto.DiseaseReadDTO;
import by.hospital.disease.entity.Disease;
import by.hospital.diseasehistory.dto.DiseaseHistoryCreateUpdateDTO;
import by.hospital.diseasehistory.dto.DiseaseHistoryReadDTO;
import by.hospital.diseasehistory.dto.PrescriptionReadDTO;
import by.hospital.diseasehistory.entity.DiseaseHistory;
import by.hospital.diseasehistory.entity.Prescription;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiseaseHistoryMapper {
  DiseaseHistory toEntity(DiseaseHistoryCreateUpdateDTO dto);

  DiseaseHistoryReadDTO toDto(DiseaseHistory diseaseHistory);

  void updateEntity(
      DiseaseHistoryCreateUpdateDTO dto, @MappingTarget DiseaseHistory diseaseHistory);

  DiseaseReadDTO toDto(Disease disease);

  PrescriptionReadDTO toDto(Prescription prescription);

  List<PrescriptionReadDTO> toDto(List<Prescription> prescriptions);
}
