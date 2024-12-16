package by.hospital.user.controller;

import by.hospital.exception.handler.ErrorResponse;
import by.hospital.user.dto.UserReadDTO;
import by.hospital.user.dto.UserUpdateDTO;
import by.hospital.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить информацию о пользователе",
      description =
          "Этот эндпоинт позволяет обновить информацию о пользователе по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Пользователь успешно обновлен.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Пользователь не найден.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<UserReadDTO> updateUser(
      @PathVariable String id, @RequestBody @Valid UserUpdateDTO user) {
    return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/{id}")
  @Operation(
      summary = "Получить информацию о пользователе",
      description =
          "Этот эндпоинт позволяет получить информацию о пользователе по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Пользователь найден и возвращен.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Пользователь не найден.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<UserReadDTO> getUser(@PathVariable String id) {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  @Operation(
      summary = "Получить список всех пользователей",
      description = "Этот эндпоинт позволяет получить информацию о всех пользователях.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserReadDTO.class)))
  public ResponseEntity<List<UserReadDTO>> getAllUsers() {
    List<UserReadDTO> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }
}
