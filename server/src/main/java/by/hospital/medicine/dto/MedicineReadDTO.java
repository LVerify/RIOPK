package by.hospital.medicine.dto;

import by.hospital.medicine.entity.MeasurementUnit;
import lombok.Data;

@Data
public class MedicineReadDTO {
  private String id;
  private String name;
  private String manual;
  private Double dose;
  private MeasurementUnit measurementUnit;
  private Boolean isDrug;
  private String form;
}
