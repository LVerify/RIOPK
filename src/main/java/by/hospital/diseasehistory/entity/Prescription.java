package by.hospital.diseasehistory.entity;

import by.hospital.BaseEntity;
import by.hospital.doctor.entity.Doctor;
import by.hospital.medicine.entity.Medicine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "prescriptions")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Prescription extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "disease_history_id", nullable = false)
  @JsonBackReference
  private DiseaseHistory diseaseHistory;

  @ManyToOne
  @JoinColumn(name = "medicine_id", nullable = false)
  private Medicine medicine;

  @Column(nullable = false)
  private double medicineAmount;

  @ManyToOne
  @JoinColumn(name = "doctor_id", nullable = false)
  private Doctor doctor;

  @JoinColumn(nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PrescriptionStatus status;

  @Column(nullable = false)
  @CreatedDate
  private LocalDate prescriptionDate;
}
