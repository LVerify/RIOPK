package by.hospital.accounting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "text")
    private String manual;

    private Double dose;

    @Column(name = "is_drug")
    private boolean isDrug;

    private String form;

    @Enumerated(EnumType.STRING)
    private MeasurementUnit measurementUnit;
}

