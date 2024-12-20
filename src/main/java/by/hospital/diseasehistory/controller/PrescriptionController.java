package by.hospital.diseasehistory.controller;

import by.hospital.diseasehistory.dto.PrescriptionCreateDTO;
import by.hospital.diseasehistory.dto.PrescriptionReadDTO;
import by.hospital.diseasehistory.dto.PrescriptionUpdateDTO;
import by.hospital.diseasehistory.service.PrescriptionService;
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
@RequestMapping("/api/patients/{patientId}/disease-histories/{diseaseHistoryId}/prescriptions")
@AllArgsConstructor
public class PrescriptionController {

  private PrescriptionService prescriptionService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("{id}")
  @Operation(
      summary = "Получить информацию о назначении",
      description =
          "Этот эндпоинт позволяет получить информацию о назначении по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Назначение успешно получено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrescriptionReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Назначение не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<PrescriptionReadDTO> getPrescription(
      @PathVariable String patientId,
      @PathVariable String diseaseHistoryId,
      @PathVariable String id) {
    return new ResponseEntity<>(
        prescriptionService.getPrescription(patientId, diseaseHistoryId, id), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  @Operation(
      summary = "Добавить назначение в историю болезни",
      description = "Этот эндпоинт позволяет добавить новое назначение в историю болезни пациента.")
  @ApiResponse(
      responseCode = "201",
      description = "Назначение успешно добавлено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrescriptionReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<PrescriptionReadDTO> createPrescription(
      @PathVariable String patientId,
      @PathVariable String diseaseHistoryId,
      @RequestBody @Valid PrescriptionCreateDTO dto) {
    return new ResponseEntity<>(
        prescriptionService.createPrescription(patientId, diseaseHistoryId, dto),
        HttpStatus.CREATED);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить информацию о назначении",
      description =
          "Этот эндпоинт позволяет обновить информацию о назначении по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Назначение успешно обновлено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrescriptionReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Назначение не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "409",
      description = "Назначение не найдено у данной истории болезней.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<PrescriptionReadDTO> updatePrescription(
      @PathVariable String patientId,
      @PathVariable String diseaseHistoryId,
      @PathVariable String id,
      @RequestBody @Valid PrescriptionUpdateDTO dto) {
    return new ResponseEntity<>(
        prescriptionService.updatePrescription(patientId, diseaseHistoryId, id, dto),
        HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  @Operation(
      summary = "Получить список всех назначений",
      description = "Этот эндпоинт позволяет получить информацию о всех назначениях.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrescriptionReadDTO.class)))
  public ResponseEntity<List<PrescriptionReadDTO>> getAllPrescriptions(
      @PathVariable String patientId, @PathVariable String diseaseHistoryId) {
    return ResponseEntity.ok(prescriptionService.getAllPrescriptions(patientId, diseaseHistoryId));
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/by-disease-history")
  @Operation(
      summary = "Получить список всех назначений в истории болезни",
      description =
          "Этот эндпоинт позволяет получить информацию о всех назначениях конкретной истории болезни.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrescriptionReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Пациент/история болезни не найден/найдена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<List<PrescriptionReadDTO>> getAllPrescriptionsByDiseaseHistory(
      @PathVariable String patientId, @PathVariable String diseaseHistoryId) {
    return ResponseEntity.ok(
        prescriptionService.getAllPrescriptionsByDiseaseHistory(patientId, diseaseHistoryId));
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Удалить назначение",
      description = "Этот эндпоинт позволяет удалить назначение (сменить статус на 'отменено').")
  @ApiResponse(
      responseCode = "200",
      description = "Назначение успешно удалено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PrescriptionReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Назначение не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<PrescriptionReadDTO> deletePrescription(
      @PathVariable String patientId,
      @PathVariable String diseaseHistoryId,
      @PathVariable String id) {

    return new ResponseEntity<>(
        prescriptionService.deletePrescription(patientId, diseaseHistoryId, id), HttpStatus.OK);
  }
}
