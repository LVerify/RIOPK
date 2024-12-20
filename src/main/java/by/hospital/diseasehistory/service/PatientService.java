package by.hospital.diseasehistory.service;

import by.hospital.diseasehistory.dto.PatientCreateUpdateDTO;
import by.hospital.diseasehistory.dto.PatientReadDTO;
import by.hospital.diseasehistory.entity.Patient;
import by.hospital.diseasehistory.repository.PatientRepository;
import by.hospital.diseasehistory.service.mapper.PatientMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {
  public static final String PATIENT_NOT_FOUND = "Пациент не найден";

  private PatientMapper patientMapper;
  private PatientRepository patientRepository;

  public Patient savePatient(Patient patient) {
    return patientRepository.save(patient);
  }

  public PatientReadDTO createPatient(PatientCreateUpdateDTO dto) {
    if (patientRepository.existsByDateOfBirthAndMobilePhone(
        dto.getDateOfBirth(), dto.getMobilePhone()))
      throw new DataAlreadyExistsException("Такой пациент уже существует");
    return patientMapper.toDto(savePatient(patientMapper.toEntity(dto)));
  }

  public PatientReadDTO updatePatient(String id, PatientCreateUpdateDTO dto) {
    Patient updatedPatient =
        patientRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(PATIENT_NOT_FOUND));
    patientMapper.updateEntity(dto, updatedPatient);
    return patientMapper.toDto(savePatient(updatedPatient));
  }

  public PatientReadDTO getPatient(String id) {
    Patient existingPatient =
        patientRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(PATIENT_NOT_FOUND));
    return patientMapper.toDto(existingPatient);
  }

  public List<PatientReadDTO> getAllPatients() {
    return patientRepository.findAll().stream().map(patientMapper::toDto).toList();
  }

  public void deletePatient(String id) {
    if (!patientRepository.existsById(id)) throw new DataNotFoundException(PATIENT_NOT_FOUND);
    patientRepository.deleteById(id);
  }
}
