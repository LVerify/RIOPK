package by.hospital.diseasehistory.controller;

import by.hospital.diseasehistory.dto.DiseaseHistoryCreateUpdateDTO;
import by.hospital.diseasehistory.dto.DiseaseHistoryReadDTO;
import by.hospital.diseasehistory.service.DiseaseHistoryService;
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
@RequestMapping("/api/patients/{patientId}/disease-histories")
@AllArgsConstructor
public class DiseaseHistoryController {
  private DiseaseHistoryService diseaseHistoryService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("{id}")
  @Operation(
      summary = "Получить информацию об истории болезни",
      description =
          "Этот эндпоинт позволяет получить информацию об истории болезни по её уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "История болезни успешно получена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseHistoryReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "История болезни не найдена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DiseaseHistoryReadDTO> getDiseaseHistory(
      @PathVariable String patientId, @PathVariable String id) {
    return new ResponseEntity<>(
        diseaseHistoryService.getDiseaseHistory(patientId, id), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  @Operation(
      summary = "Добавить историю болезни пациенту",
      description = "Этот эндпоинт позволяет добавить новую историю болезни пациенту.")
  @ApiResponse(
      responseCode = "201",
      description = "История Болезни успешно добавлен.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseHistoryReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DiseaseHistoryReadDTO> createDiseaseHistory(
      @PathVariable String patientId, @RequestBody @Valid DiseaseHistoryCreateUpdateDTO dto) {
    return new ResponseEntity<>(
        diseaseHistoryService.createDiseaseHistory(patientId, dto), HttpStatus.CREATED);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить информацию об истории болезни",
      description =
          "Этот эндпоинт позволяет обновить информацию об истории болезни по её уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "История болезни успешно обновлена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseHistoryReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "История болезни не найдена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "409",
      description = "История болезни не найдена у данного пациента.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DiseaseHistoryReadDTO> updateDiseaseHistory(
      @PathVariable String patientId,
      @PathVariable String id,
      @RequestBody @Valid DiseaseHistoryCreateUpdateDTO dto) {
    return new ResponseEntity<>(
        diseaseHistoryService.updateDiseaseHistory(patientId, id, dto), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  @Operation(
      summary = "Получить список всех историй болезней",
      description = "Этот эндпоинт позволяет получить информацию о всех историях болезней.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseHistoryReadDTO.class)))
  public ResponseEntity<List<DiseaseHistoryReadDTO>> getAllDiseaseHistories(
      @PathVariable String patientId) {
    return ResponseEntity.ok(diseaseHistoryService.getAllDiseaseHistories(patientId));
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/by-patient")
  @Operation(
      summary = "Получить список всех историй болезней",
      description = "Этот эндпоинт позволяет получить информацию о всех историях болезней.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseHistoryReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Пациент не найден.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<List<DiseaseHistoryReadDTO>> getAllDiseaseHistoriesByPatient(
      @PathVariable String patientId) {
    return ResponseEntity.ok(diseaseHistoryService.getAllDiseaseHistoriesByPatient(patientId));
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Удалить историю болезни",
      description = "Этот эндпоинт позволяет удалить историю болезни.")
  @ApiResponse(
      responseCode = "204",
      description = "История болезни успешно удалёна.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseHistoryReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "История болезни не найдена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Void> deleteDiseaseHistory(
      @PathVariable String patientId, @PathVariable String id) {
    diseaseHistoryService.deleteDiseaseHistory(patientId, id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
