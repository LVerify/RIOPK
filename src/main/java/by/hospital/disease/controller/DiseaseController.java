package by.hospital.disease.controller;

import by.hospital.disease.dto.DiseaseCreateUpdateDTO;
import by.hospital.disease.dto.DiseaseReadDTO;
import by.hospital.disease.service.DiseaseService;
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
@RequestMapping("/api/diseases")
@AllArgsConstructor
public class DiseaseController {
  private DiseaseService diseaseService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("{id}")
  @Operation(
      summary = "Получить информацию об заболевании",
      description =
          "Этот эндпоинт позволяет получить информацию об заболевании по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Заболевание успешно получено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Заболевание не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DiseaseReadDTO> getDisease(@PathVariable String id) {
    return new ResponseEntity<>(diseaseService.getDisease(id), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  @Operation(
      summary = "Создать заболевание больницы",
      description = "Этот эндпоинт позволяет создать новое заболевание больницы.")
  @ApiResponse(
      responseCode = "201",
      description = "Заболевание успешно добавлено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "409",
      description = "Заболевание с таким названием уже существует.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DiseaseReadDTO> createDisease(
      @RequestBody @Valid DiseaseCreateUpdateDTO dto) {
    return new ResponseEntity<>(diseaseService.createDisease(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить информацию об заболевании",
      description =
          "Этот эндпоинт позволяет обновить информацию об заболевании по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Заболевание успешно обновлено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Заболевание не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<DiseaseReadDTO> updateDisease(
      @PathVariable String id, @RequestBody @Valid DiseaseCreateUpdateDTO dto) {
    return new ResponseEntity<>(diseaseService.updateDisease(id, dto), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  @Operation(
      summary = "Получить список всех заболеваний",
      description = "Этот эндпоинт позволяет получить информацию о всех заболеваниях.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseReadDTO.class)))
  public ResponseEntity<List<DiseaseReadDTO>> getAllDiseases() {
    return ResponseEntity.ok(diseaseService.getAllDiseases());
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Удалить заболевание",
      description = "Этот эндпоинт позволяет удалить заболевание.")
  @ApiResponse(
      responseCode = "204",
      description = "Заболевание успешно удалено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = DiseaseReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Заболевание не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Void> deleteSpeciality(@PathVariable String id) {
    diseaseService.deleteDisease(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
