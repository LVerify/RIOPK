package by.hospital.diseasehistory.controller;

import by.hospital.diseasehistory.dto.PatientCreateUpdateDTO;
import by.hospital.diseasehistory.dto.PatientReadDTO;
import by.hospital.diseasehistory.service.PatientService;
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
@RequestMapping("/api/patients")
@AllArgsConstructor
public class PatientController {
  private PatientService patientService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("{id}")
  @Operation(
      summary = "Получить информацию о пациенте",
      description =
          "Этот эндпоинт позволяет получить информацию о пациенте по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Пациент успешно получен.",
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
      description = "Пациент не найден.",
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
      summary = "Добавить пациента больницы",
      description = "Этот эндпоинт позволяет добавить нового пациента больницы.")
  @ApiResponse(
      responseCode = "201",
      description = "Пациент успешно добавлен.",
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
      description = "Пациент с таким названием уже существует.",
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
      summary = "Обновить информацию об пациенте",
      description =
          "Этот эндпоинт позволяет обновить информацию об пациенте по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Пациент успешно обновлён.",
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
      description = "Пациент не найден.",
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
      summary = "Получить список всех пациентов",
      description = "Этот эндпоинт позволяет получить информацию о всех пациентах.")
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
  @Operation(summary = "Удалить пациент", description = "Этот эндпоинт позволяет удалить пациента.")
  @ApiResponse(
      responseCode = "204",
      description = "Пациент успешно удалён.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PatientReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Пациент не найден.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Void> deletePatient(@PathVariable String id) {
    patientService.deletePatient(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
