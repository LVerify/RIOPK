package by.hospital.doctor.entity;

import by.hospital.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "specialities")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Speciality extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "speciality")
  @JsonBackReference
  private List<Doctor> staff;
}
