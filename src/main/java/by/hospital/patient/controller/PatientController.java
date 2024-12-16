package by.hospital.patient.controller;

import by.hospital.exception.handler.ErrorResponse;
import by.hospital.patient.dto.PatientCreateUpdateDTO;
import by.hospital.patient.dto.PatientReadDTO;
import by.hospital.patient.service.PatientService;
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
@RequestMapping("/api/patients")
@AllArgsConstructor
public class PatientController {
  private PatientService patientService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("{id}")
  @Operation(
      summary = "Получить информацию об специальности",
      description =
          "Этот эндпоинт позволяет получить информацию об специальности по её уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Специальность успешно получена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PatientReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Специальность не найдена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<PatientReadDTO> getPatient(@PathVariable String id) {
    return new ResponseEntity<>(patientService.getPatient(id), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  @Operation(
      summary = "Создать специальность больницы",
      description = "Этот эндпоинт позволяет создать новую специальность больницы.")
  @ApiResponse(
      responseCode = "201",
      description = "Специальность успешно добавлена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PatientReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "409",
      description = "Специальность с таким названием уже существует.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<PatientReadDTO> createPatient(
      @RequestBody @Valid PatientCreateUpdateDTO dto) {
    return new ResponseEntity<>(patientService.createPatient(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить информацию об специальности",
      description =
          "Этот эндпоинт позволяет обновить информацию об специальности по её уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Специальность успешно обновлена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PatientReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Специальность не найдена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<PatientReadDTO> updatePatient(
      @PathVariable String id, @RequestBody @Valid PatientCreateUpdateDTO dto) {
    return new ResponseEntity<>(patientService.updatePatient(id, dto), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  @Operation(
      summary = "Получить список всех специальностей",
      description = "Этот эндпоинт позволяет получить информацию о всех специальностях.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PatientReadDTO.class)))
  public ResponseEntity<List<PatientReadDTO>> getAllPatients() {
    return ResponseEntity.ok(patientService.getAllPatients());
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Удалить специальность",
      description = "Этот эндпоинт позволяет удалить специальность.")
  @ApiResponse(
      responseCode = "204",
      description = "Специальность успешно удалена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PatientReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Специальность не найдена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Void> deletePatient(@PathVariable String id) {
    patientService.deletePatient(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
