package by.hospital.medicine.entity;

import by.hospital.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicines")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Medicine extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "text", nullable = false)
  private String manual;

  @Column(nullable = false)
  private Double dose;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MeasurementUnit measurementUnit;

  @Column(nullable = false)
  private Boolean isDrug;

  @Column(nullable = false)
  private String form;
}
