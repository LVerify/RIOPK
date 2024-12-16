package by.hospital;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {
  @Id private String id = NanoIdUtils.randomNanoId();
}
