package by.hospital.diseasehistory.service;

import static by.hospital.disease.service.DiseaseService.DISEASE_NOT_FOUND;
import static by.hospital.diseasehistory.service.PatientService.PATIENT_NOT_FOUND;

import by.hospital.disease.repository.DiseaseRepository;
import by.hospital.diseasehistory.dto.DiseaseHistoryCreateUpdateDTO;
import by.hospital.diseasehistory.dto.DiseaseHistoryReadDTO;
import by.hospital.diseasehistory.entity.DiseaseHistory;
import by.hospital.diseasehistory.repository.DiseaseHistoryRepository;
import by.hospital.diseasehistory.repository.PatientRepository;
import by.hospital.diseasehistory.service.mapper.DiseaseHistoryMapper;
import by.hospital.exception.DataConflictException;
import by.hospital.exception.DataNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiseaseHistoryService {
  public static final String DISEASE_HISTORY_NOT_FOUND = "История болезни не найдена";

  private DiseaseHistoryMapper diseaseHistoryMapper;
  private DiseaseHistoryRepository diseaseHistoryRepository;
  private PatientRepository patientRepository;
  private DiseaseRepository diseaseRepository;

  public DiseaseHistory saveDiseaseHistory(DiseaseHistory diseaseHistory) {
    return diseaseHistoryRepository.save(diseaseHistory);
  }

  public DiseaseHistoryReadDTO createDiseaseHistory(
      String patientId, DiseaseHistoryCreateUpdateDTO dto) {
    DiseaseHistory newDiseaseHistory = diseaseHistoryMapper.toEntity(dto);
    setPatient(newDiseaseHistory, patientId);
    setDisease(newDiseaseHistory, dto.getDiseaseId());
    return diseaseHistoryMapper.toDto(saveDiseaseHistory(newDiseaseHistory));
  }

  public DiseaseHistoryReadDTO updateDiseaseHistory(
      String patientId, String id, DiseaseHistoryCreateUpdateDTO dto) {
    DiseaseHistory updatedDiseaseHistory =
        diseaseHistoryRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DISEASE_HISTORY_NOT_FOUND));
    diseaseHistoryMapper.updateEntity(dto, updatedDiseaseHistory);
    setPatient(updatedDiseaseHistory, patientId);
    setDisease(updatedDiseaseHistory, dto.getDiseaseId());
    return diseaseHistoryMapper.toDto(saveDiseaseHistory(updatedDiseaseHistory));
  }

  public DiseaseHistoryReadDTO getDiseaseHistory(String patientId, String id) {
    DiseaseHistory existingDiseaseHistory =
        diseaseHistoryRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DISEASE_HISTORY_NOT_FOUND));
    setPatient(existingDiseaseHistory, patientId);
    return diseaseHistoryMapper.toDto(existingDiseaseHistory);
  }

  public List<DiseaseHistoryReadDTO> getAllDiseaseHistories(String patientId) {
    patientRepository.existsById(patientId);
    return diseaseHistoryRepository.findAll().stream().map(diseaseHistoryMapper::toDto).toList();
  }

  public List<DiseaseHistoryReadDTO> getAllDiseaseHistoriesByPatient(String patientId) {
    var patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new DataNotFoundException(PATIENT_NOT_FOUND));
    return diseaseHistoryRepository.findAllByPatient(patient).stream()
        .map(diseaseHistoryMapper::toDto)
        .toList();
  }

  public void deleteDiseaseHistory(String patientId, String id) {
    DiseaseHistory existingDiseaseHistory =
        diseaseHistoryRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DISEASE_HISTORY_NOT_FOUND));
    setPatient(existingDiseaseHistory, patientId);
    diseaseHistoryRepository.deleteById(id);
  }

  private void setPatient(DiseaseHistory diseaseHistory, String patientId) {
    var patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new DataNotFoundException(PATIENT_NOT_FOUND));
    if (diseaseHistory.getPatient() == null) {
      diseaseHistory.setPatient(patient);
    } else if (!patient.getDiseaseHistories().contains(diseaseHistory)) {
      throw new DataConflictException("Данная история болезни принадлежит другому пациенту");
    }
  }

  private void setDisease(DiseaseHistory diseaseHistory, String diseaseId) {
    diseaseHistory.setDisease(
        diseaseRepository
            .findById(diseaseId)
            .orElseThrow(() -> new DataNotFoundException(DISEASE_NOT_FOUND)));
  }
}
