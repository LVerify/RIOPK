package by.hospital.diseasehistory.entity;

import by.hospital.BaseEntity;
import by.hospital.disease.entity.Disease;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "disease_histories")
public class DiseaseHistory extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "patient_id", nullable = false)
  @JsonBackReference
  private Patient patient;

  @OneToMany(mappedBy = "diseaseHistory", orphanRemoval = true, cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Prescription> prescriptions;

  @ManyToOne
  @JoinColumn(name = "disease_id", nullable = false)
  private Disease disease;

  @Column(nullable = false)
  private String conclusion;
}
