package by.hospital.doctor.service;

import by.hospital.doctor.dto.SpecialityCreateUpdateDTO;
import by.hospital.doctor.dto.SpecialityReadDTO;
import by.hospital.doctor.entity.Speciality;
import by.hospital.doctor.repository.SpecialityRepository;
import by.hospital.doctor.service.mapper.SpecialityMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpecialityService {
  private static final String SPECIALITY_NOT_FOUND = "Специальность не найдена";

  private SpecialityMapper specialityMapper;
  private SpecialityRepository specialityRepository;

  public Speciality saveSpeciality(Speciality speciality) {
    return specialityRepository.save(speciality);
  }

  public SpecialityReadDTO createSpeciality(SpecialityCreateUpdateDTO dto) {
    if (specialityRepository.existsByName(dto.getName()))
      throw new DataAlreadyExistsException("Такая специальность уже существует");
    return specialityMapper.entityToDto(saveSpeciality(specialityMapper.dtoToEntity(dto)));
  }

  public SpecialityReadDTO updateSpeciality(String id, SpecialityCreateUpdateDTO dto) {
    Speciality updatedSpeciality =
        specialityRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(SPECIALITY_NOT_FOUND));
    specialityMapper.updateEntity(dto, updatedSpeciality);
    return specialityMapper.entityToDto(saveSpeciality(updatedSpeciality));
  }

  public List<SpecialityReadDTO> getAllSpecialities() {
    return specialityRepository.findAll().stream().map(specialityMapper::entityToDto).toList();
  }

  public SpecialityReadDTO getSpeciality(String id) {
    Speciality existingSpeciality =
        specialityRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(SPECIALITY_NOT_FOUND));
    return specialityMapper.entityToDto(existingSpeciality);
  }

  public void deleteSpeciality(String id) {
    if (!specialityRepository.existsById(id)) throw new DataNotFoundException(SPECIALITY_NOT_FOUND);
    specialityRepository.deleteById(id);
  }
}
