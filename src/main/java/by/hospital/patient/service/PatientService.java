package by.hospital.patient.service;

import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import by.hospital.patient.dto.PatientCreateUpdateDTO;
import by.hospital.patient.dto.PatientReadDTO;
import by.hospital.patient.entity.Patient;
import by.hospital.patient.repository.PatientRepository;
import by.hospital.patient.service.mapper.PatientMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {
  private static final String PATIENT_NOT_FOUND = "Пациент не найден";

  private PatientMapper patientMapper;
  private PatientRepository patientRepository;

  public Patient savePatient(Patient patient) {
    return patientRepository.save(patient);
  }

  public PatientReadDTO createPatient(PatientCreateUpdateDTO dto) {
    if (patientRepository.existsByDateOfBirthAndMobilePhone(
        dto.getDateOfBirth(), dto.getMobilePhone()))
      throw new DataAlreadyExistsException("Такой пациент уже существует");
    return patientMapper.entityToDto(savePatient(patientMapper.dtoToEntity(dto)));
  }

  public PatientReadDTO updatePatient(String id, PatientCreateUpdateDTO dto) {
    Patient updatedPatient =
        patientRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(PATIENT_NOT_FOUND));
    patientMapper.updateEntity(dto, updatedPatient);
    return patientMapper.entityToDto(savePatient(updatedPatient));
  }

  public PatientReadDTO getPatient(String id) {
    Patient existingPatient =
        patientRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(PATIENT_NOT_FOUND));
    return patientMapper.entityToDto(existingPatient);
  }

  public List<PatientReadDTO> getAllPatients() {
    return patientRepository.findAll().stream().map(patientMapper::entityToDto).toList();
  }

  public void deletePatient(String id) {
    if (!patientRepository.existsById(id)) throw new DataNotFoundException(PATIENT_NOT_FOUND);
    patientRepository.deleteById(id);
  }
}
