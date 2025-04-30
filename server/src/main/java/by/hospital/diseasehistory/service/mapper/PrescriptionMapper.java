package by.hospital.diseasehistory.service.mapper;

import by.hospital.diseasehistory.dto.PrescriptionCreateDTO;
import by.hospital.diseasehistory.dto.PrescriptionReadDTO;
import by.hospital.diseasehistory.dto.PrescriptionUpdateDTO;
import by.hospital.diseasehistory.entity.Prescription;
import by.hospital.doctor.dto.DoctorReadDTO;
import by.hospital.doctor.entity.Doctor;
import by.hospital.medicine.dto.MedicineReadDTO;
import by.hospital.medicine.entity.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface PrescriptionMapper {
  Prescription toEntity(PrescriptionCreateDTO dto);

  PrescriptionReadDTO toDto(Prescription prescription);

  void updateEntity(PrescriptionUpdateDTO dto, @MappingTarget Prescription prescription);

  MedicineReadDTO toDto(Medicine medicine);

  DoctorReadDTO toDto(Doctor doctor);
}
