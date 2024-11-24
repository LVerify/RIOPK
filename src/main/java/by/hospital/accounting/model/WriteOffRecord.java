package by.hospital.accounting.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "write_off_records")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteOffRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private Integer quantityWrittenOff;
    private String reason;
    private Date writeOffDate;
}
