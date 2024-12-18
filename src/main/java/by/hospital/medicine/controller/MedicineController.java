package by.hospital.medicine.controller;

import by.hospital.exception.handler.ErrorResponse;
import by.hospital.medicine.dto.MedicineCreateUpdateDTO;
import by.hospital.medicine.dto.MedicineReadDTO;
import by.hospital.medicine.service.MedicineService;
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
@RequestMapping("/api/medicines")
@AllArgsConstructor
public class MedicineController {

  private MedicineService medicineService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("{id}")
  @Operation(
      summary = "Получить информацию об отделении",
      description =
          "Этот эндпоинт позволяет получить информацию об отделении по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Отделение успешно получено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = MedicineReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Отделение не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<MedicineReadDTO> getMedicine(@PathVariable String id) {
    return new ResponseEntity<>(medicineService.getMedicine(id), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping
  @Operation(
      summary = "Создать отделение больницы",
      description = "Этот эндпоинт позволяет создать новое отделение больницы.")
  @ApiResponse(
      responseCode = "201",
      description = "Отделение успешно добавлено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = MedicineReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "409",
      description = "Отделение с таким названием уже существует.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<MedicineReadDTO> createMedicine(
      @RequestBody @Valid MedicineCreateUpdateDTO dto) {
    return new ResponseEntity<>(medicineService.createMedicine(dto), HttpStatus.CREATED);
  }

  @PreAuthorize("isAuthenticated()")
  @PutMapping("/{id}")
  @Operation(
      summary = "Обновить информацию об отделении",
      description =
          "Этот эндпоинт позволяет обновить информацию об отделении по его уникальному идентификатору.")
  @ApiResponse(
      responseCode = "200",
      description = "Отделение успешно обновлено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = MedicineReadDTO.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Неверный формат запроса или ошибка валидации.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Отделение не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<MedicineReadDTO> updateMedicine(
      @PathVariable String id, @RequestBody @Valid MedicineCreateUpdateDTO dto) {
    return new ResponseEntity<>(medicineService.updateMedicine(id, dto), HttpStatus.OK);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  @Operation(
      summary = "Получить список всех отделений",
      description = "Этот эндпоинт позволяет получить информацию о всех отделениях.")
  @ApiResponse(
      responseCode = "200",
      description = "Успех.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = MedicineReadDTO.class)))
  public ResponseEntity<List<MedicineReadDTO>> getAllMedicines() {
    return ResponseEntity.ok(medicineService.getAllMedicines());
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/{id}")
  @Operation(
      summary = "Удалить отделение",
      description = "Этот эндпоинт позволяет удалить отделение.")
  @ApiResponse(
      responseCode = "204",
      description = "Отделение успешно удалено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = MedicineReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Отделение не найдено.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Void> deleteSpeciality(@PathVariable String id) {
    medicineService.deleteMedicine(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
