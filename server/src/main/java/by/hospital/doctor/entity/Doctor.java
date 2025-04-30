package by.hospital.doctor.entity;

import by.hospital.BaseEntity;
import by.hospital.user.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "doctors")
public class Doctor extends BaseEntity {

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = true)
  private String patronymicName;

  @ManyToOne
  @JoinColumn(name = "department_id", nullable = false)
  @JsonManagedReference
  private Department department;

  @ManyToOne
  @JoinColumn(name = "speciality_id", nullable = false)
  @JsonManagedReference
  private Speciality speciality;

  @Column(nullable = false)
  private String mobilePhone;
}
