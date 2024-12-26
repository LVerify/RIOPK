package by.hospital.diseasehistory.dto;

import by.hospital.diseasehistory.entity.PrescriptionStatus;
import by.hospital.doctor.dto.DoctorReadDTO;
import by.hospital.medicine.dto.MedicineReadDTO;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PrescriptionReadDTO {
  private String id;
  private DoctorReadDTO doctor;
  private MedicineReadDTO medicine;
  private Double medicineAmount;
  private String description;
  private PrescriptionStatus status;
  private LocalDate prescriptionDate;
}
