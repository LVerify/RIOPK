package by.hospital.doctor.controller;

import by.hospital.doctor.dto.DoctorCreateDTO;
import by.hospital.doctor.dto.DoctorReadDTO;
import by.hospital.doctor.dto.DoctorUpdateDTO;
import by.hospital.doctor.service.DoctorService;
import by.hospital.exception.handler.ErrorResponse;
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
@RequestMapping("/api/doctors")
@AllArgsConstructor
public class DoctorController {
  private DoctorService doctorService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("{id}")
  @Operation(
      summary = "Получить информацию о сотруднике",
      description =
          "Этот эндпоинт позволяет получить информацию о сотруднике по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Сотрудник успешно получен.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DoctorReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Сотрудник не найден.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DoctorReadDTO> getDoctor(@PathVariable String id) {
    return new ResponseEntity<>(doctorService.getDoctor(id), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  @Operation(
      summary = "Создать сотрудника больницы",
      description =
          "Этот эндпоинт позволяет создать нового сотрудника больницы для существующей учётной записи.")
  @ApiResponse(
      responseCode = "201",
      description = "Сотрудник успешно добавлен.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DoctorReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "409",
      description = "Для данной учётной записи уже создан сотрудник.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DoctorReadDTO> createDoctor(@RequestBody @Valid DoctorCreateDTO dto) {
    return new ResponseEntity<>(doctorService.createDoctor(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить информацию о сотруднике",
      description =
          "Этот эндпоинт позволяет обновить информацию о сотруднике по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Сотрудник успешно обновлен.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DoctorReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Сотрудник не найден.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DoctorReadDTO> updateDoctor(
      @PathVariable String id, @RequestBody @Valid DoctorUpdateDTO dto) {
    return new ResponseEntity<>(doctorService.updateDoctor(id, dto), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  @Operation(
      summary = "Получить список всех сотрудников",
      description = "Этот эндпоинт позволяет получить информацию о всех сотрудниках.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DoctorReadDTO.class)))
  public ResponseEntity<List<DoctorReadDTO>> getAllDoctors() {
    return ResponseEntity.ok(doctorService.getAllDoctors());
  }
}
