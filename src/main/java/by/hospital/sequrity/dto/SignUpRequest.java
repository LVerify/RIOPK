package by.hospital.sequrity.dto;

import by.hospital.user.entity.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpRequest {
  @NotEmpty private String username;
  @NotEmpty private String password;
  @NotNull private Role role;
}
