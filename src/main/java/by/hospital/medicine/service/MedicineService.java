package by.hospital.medicine.service;

import by.hospital.exception.DataNotFoundException;
import by.hospital.medicine.dto.MedicineCreateUpdateDTO;
import by.hospital.medicine.dto.MedicineReadDTO;
import by.hospital.medicine.entity.Medicine;
import by.hospital.medicine.repository.MedicineRepository;
import by.hospital.medicine.service.mapper.MedicineMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MedicineService {

  private static final String MEDICINE_NOT_FOUND = "Лекарственный препарат не найден";

  private MedicineMapper medicineMapper;
  private MedicineRepository medicineRepository;

  public Medicine saveMedicine(Medicine medicine) {
    return medicineRepository.save(medicine);
  }

  public MedicineReadDTO createMedicine(MedicineCreateUpdateDTO dto) {
    return medicineMapper.toDto(saveMedicine(medicineMapper.toEntity(dto)));
  }

  public MedicineReadDTO updateMedicine(String id, MedicineCreateUpdateDTO dto) {
    Medicine updatedMedicine =
        medicineRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(MEDICINE_NOT_FOUND));
    medicineMapper.updateEntity(dto, updatedMedicine);
    return medicineMapper.toDto(saveMedicine(updatedMedicine));
  }

  public MedicineReadDTO getMedicine(String id) {
    Medicine existingMedicine =
        medicineRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(MEDICINE_NOT_FOUND));
    return medicineMapper.toDto(existingMedicine);
  }

  public List<MedicineReadDTO> getAllMedicines() {
    return medicineRepository.findAll().stream().map(medicineMapper::toDto).toList();
  }

  public void deleteMedicine(String id) {
    if (!medicineRepository.existsById(id)) throw new DataNotFoundException(MEDICINE_NOT_FOUND);
    medicineRepository.deleteById(id);
  }
}
