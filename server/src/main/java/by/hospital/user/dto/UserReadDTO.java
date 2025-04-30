package by.hospital.user.dto;

import by.hospital.user.entity.Role;
import lombok.Data;

@Data
public class UserReadDTO {
  private String id;
  private String username;
  private Role role;
}
