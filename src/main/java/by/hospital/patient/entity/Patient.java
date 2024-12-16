package by.hospital.patient.entity;

import by.hospital.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patients")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends BaseEntity {

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  private String patronymicName;

  @Column(nullable = false)
  private LocalDate dateOfBirth;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  @Column(nullable = false)
  private String mobilePhone;

  @Column(nullable = false, columnDefinition = "text")
  private String address;
}
