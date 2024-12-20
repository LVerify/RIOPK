package by.hospital.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserUpdateDTO {
  @NotEmpty private String username;
}
