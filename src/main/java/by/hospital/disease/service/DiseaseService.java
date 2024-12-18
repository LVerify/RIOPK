package by.hospital.disease.service;

import by.hospital.disease.dto.DiseaseCreateUpdateDTO;
import by.hospital.disease.dto.DiseaseReadDTO;
import by.hospital.disease.entity.Disease;
import by.hospital.disease.repository.DiseaseRepository;
import by.hospital.disease.service.mapper.DiseaseMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiseaseService {
  private static final String DISEASE_NOT_FOUND = "Заболевание не найдено";

  private DiseaseMapper diseaseMapper;
  private DiseaseRepository diseaseRepository;

  public Disease saveDisease(Disease disease) {
    return diseaseRepository.save(disease);
  }

  public DiseaseReadDTO createDisease(DiseaseCreateUpdateDTO dto) {
    if (diseaseRepository.existsByName(dto.getName()))
      throw new DataAlreadyExistsException("Такое заболевание уже существует");
    return diseaseMapper.toDto(saveDisease(diseaseMapper.toEntity(dto)));
  }

  public DiseaseReadDTO updateDisease(String id, DiseaseCreateUpdateDTO dto) {
    Disease updatedDisease =
        diseaseRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DISEASE_NOT_FOUND));
    diseaseMapper.updateEntity(dto, updatedDisease);
    return diseaseMapper.toDto(saveDisease(updatedDisease));
  }

  public DiseaseReadDTO getDisease(String id) {
    Disease existingDisease =
        diseaseRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DISEASE_NOT_FOUND));
    return diseaseMapper.toDto(existingDisease);
  }

  public List<DiseaseReadDTO> getAllDiseases() {
    return diseaseRepository.findAll().stream().map(diseaseMapper::toDto).toList();
  }

  public void deleteDisease(String id) {
    if (!diseaseRepository.existsById(id)) throw new DataNotFoundException(DISEASE_NOT_FOUND);
    diseaseRepository.deleteById(id);
  }
}
