package by.hospital.medicine.service.mapper;

import by.hospital.medicine.dto.MedicineCreateUpdateDTO;
import by.hospital.medicine.dto.MedicineReadDTO;
import by.hospital.medicine.entity.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicineMapper {
  Medicine toEntity(MedicineCreateUpdateDTO dto);

  MedicineReadDTO toDto(Medicine medicine);

  void updateEntity(MedicineCreateUpdateDTO dto, @MappingTarget Medicine medicine);
}
