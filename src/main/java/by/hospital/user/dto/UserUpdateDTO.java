package by.hospital.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateDTO {
  @NotEmpty private String username;
  @Email @NotNull String email;
  @NotEmpty private String firstName;
  @NotNull private String middleName;
  @NotEmpty private String lastName;
}
