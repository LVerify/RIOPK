package by.hospital.doctor.entity;

import by.hospital.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "departments")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  //лист палат для этого отделения

  @OneToMany(mappedBy = "department")
  @JsonBackReference
  private List<Doctor> staff;
}

//палата
/*
* id
* строка с номером палаты
*
*
* */