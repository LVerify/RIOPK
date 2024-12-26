package by.hospital.diseasehistory.service;

import static by.hospital.diseasehistory.service.DiseaseHistoryService.DISEASE_HISTORY_NOT_FOUND;
import static by.hospital.diseasehistory.service.PatientService.PATIENT_NOT_FOUND;
import static by.hospital.medicine.service.MedicineService.MEDICINE_NOT_FOUND;

import by.hospital.diseasehistory.dto.PrescriptionCreateDTO;
import by.hospital.diseasehistory.dto.PrescriptionReadDTO;
import by.hospital.diseasehistory.dto.PrescriptionUpdateDTO;
import by.hospital.diseasehistory.entity.DiseaseHistory;
import by.hospital.diseasehistory.entity.Patient;
import by.hospital.diseasehistory.entity.Prescription;
import by.hospital.diseasehistory.entity.PrescriptionStatus;
import by.hospital.diseasehistory.repository.DiseaseHistoryRepository;
import by.hospital.diseasehistory.repository.PatientRepository;
import by.hospital.diseasehistory.repository.PrescriptionRepository;
import by.hospital.diseasehistory.service.mapper.PrescriptionMapper;
import by.hospital.exception.DataConflictException;
import by.hospital.exception.DataNotFoundException;
import by.hospital.exception.StatusTransitionNotAllowedException;
import by.hospital.medicine.repository.MedicineRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrescriptionService {
  public static final String PRESCRIPTION_NOT_FOUND = "Назначение не найдено";

  private PrescriptionMapper prescriptionMapper;
  private PrescriptionRepository prescriptionRepository;
  private DiseaseHistoryRepository diseaseHistoryRepository;
  private PatientRepository patientRepository;
  private MedicineRepository medicineRepository;

  public Prescription savePrescription(Prescription prescription) {
    return prescriptionRepository.save(prescription);
  }

  public PrescriptionReadDTO createPrescription(
      String patientId, String diseaseHistoryId, PrescriptionCreateDTO dto) {

    var prescription = prescriptionMapper.toEntity(dto);
    setDiseaseHistory(prescription, patientId, diseaseHistoryId);
    setMedicine(prescription, dto.getMedicineId());
    return prescriptionMapper.toDto(savePrescription(prescriptionMapper.toEntity(dto)));
  }

  public PrescriptionReadDTO updatePrescription(
      String patientId, String diseaseHistoryId, String id, PrescriptionUpdateDTO dto) {

    var updatedPrescription = getPrescriptionById(id);
    checkStatusTransition(updatedPrescription.getStatus(), dto.getStatus());
    setDiseaseHistory(updatedPrescription, patientId, diseaseHistoryId);
    setMedicine(updatedPrescription, dto.getMedicineId());
    prescriptionMapper.updateEntity(dto, updatedPrescription);
    return prescriptionMapper.toDto(savePrescription(updatedPrescription));
  }

  public PrescriptionReadDTO getPrescription(String patientId, String diseaseHistoryId, String id) {

    var existingPrescription = getPrescriptionById(id);
    setDiseaseHistory(existingPrescription, patientId, diseaseHistoryId);
    return prescriptionMapper.toDto(existingPrescription);
  }

  public List<PrescriptionReadDTO> getAllPrescriptionsByDiseaseHistory(
      String patientId, String diseaseHistoryId) {

    var existingHistory = getDiseaseHistoryIfExists(patientId, diseaseHistoryId);
    return existingHistory.getPrescriptions().stream().map(prescriptionMapper::toDto).toList();
  }

  public List<PrescriptionReadDTO> getAllPrescriptions(String patientId, String diseaseHistoryId) {
    patientRepository.existsById(patientId);
    diseaseHistoryRepository.existsById(diseaseHistoryId);
    return prescriptionRepository.findAll().stream().map(prescriptionMapper::toDto).toList();
  }

  public PrescriptionReadDTO deletePrescription(
      String patientId, String diseaseHistoryId, String id) {
    var existingPrescription = getPrescriptionById(id);
    setDiseaseHistory(existingPrescription, patientId, diseaseHistoryId);
    existingPrescription.setStatus(PrescriptionStatus.CANCELLED);
    existingPrescription = savePrescription(existingPrescription);
    return prescriptionMapper.toDto(existingPrescription);
  }

  private Prescription getPrescriptionById(String prescriptionId) {
    return prescriptionRepository
        .findById(prescriptionId)
        .orElseThrow(() -> new DataNotFoundException(PRESCRIPTION_NOT_FOUND));
  }

  private void setDiseaseHistory(
      Prescription prescription, String patientId, String diseaseHistoryId) {
    var diseaseHistory = getDiseaseHistoryIfExists(patientId, diseaseHistoryId);
    if (prescription.getDiseaseHistory() == null) {
      prescription.setDiseaseHistory(diseaseHistory);
    } else if (!diseaseHistory.getPrescriptions().contains(prescription)) {
      throw new DataConflictException("Назначение не принадлежит данной истории болезней");
    }
  }

  private Patient getPatientIfExists(String patientId) {
    return patientRepository
        .findById(patientId)
        .orElseThrow(() -> new DataNotFoundException(PATIENT_NOT_FOUND));
  }

  private void setMedicine(Prescription prescription, String medicineId) {

    var medicine =
        medicineRepository
            .findById(medicineId)
            .orElseThrow(() -> new DataNotFoundException(MEDICINE_NOT_FOUND));
    prescription.setMedicine(medicine);
  }

  private DiseaseHistory getDiseaseHistoryIfExists(String patientId, String diseaseHistoryId) {
    var patient = getPatientIfExists(patientId);
    var diseaseHistory =
        diseaseHistoryRepository
            .findById(diseaseHistoryId)
            .orElseThrow(() -> new DataNotFoundException(DISEASE_HISTORY_NOT_FOUND));
    if (!patient.getDiseaseHistories().contains(diseaseHistory)) {
      throw new DataConflictException("История болезней не принадлежит данному пациенту");
    }
    return diseaseHistory;
  }

  private void checkStatusTransition(PrescriptionStatus oldStatus, PrescriptionStatus newStatus) {
    if (!oldStatus.canTransitionTo(newStatus)) {
      throw new StatusTransitionNotAllowedException(
          String.format(
              "Переход от статуса '%s' к статусу '%s' невозможен.", oldStatus, newStatus));
    }
  }
}
