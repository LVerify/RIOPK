package by.hospital.doctor.controller;

import by.hospital.doctor.dto.SpecialityCreateUpdateDTO;
import by.hospital.doctor.dto.SpecialityReadDTO;
import by.hospital.doctor.service.SpecialityService;
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
@RequestMapping("api/specialities")
@AllArgsConstructor
public class SpecialityController {
  private SpecialityService specialityService;

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
              schema = @Schema(implementation = SpecialityReadDTO.class)))
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
  public ResponseEntity<SpecialityReadDTO> getSpeciality(@PathVariable String id) {
    return new ResponseEntity<>(specialityService.getSpeciality(id), HttpStatus.OK);
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
              schema = @Schema(implementation = SpecialityReadDTO.class)))
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
  public ResponseEntity<SpecialityReadDTO> createSpeciality(
      @RequestBody @Valid SpecialityCreateUpdateDTO dto) {
    return new ResponseEntity<>(specialityService.createSpeciality(dto), HttpStatus.CREATED);
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
              schema = @Schema(implementation = SpecialityReadDTO.class)))
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
  public ResponseEntity<SpecialityReadDTO> updateSpeciality(
      @PathVariable String id, @RequestBody @Valid SpecialityCreateUpdateDTO dto) {
    return new ResponseEntity<>(specialityService.updateSpeciality(id, dto), HttpStatus.OK);
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
              schema = @Schema(implementation = SpecialityReadDTO.class)))
  public ResponseEntity<List<SpecialityReadDTO>> getAllSpecialities() {
    return ResponseEntity.ok(specialityService.getAllSpecialities());
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
              schema = @Schema(implementation = SpecialityReadDTO.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Специальность не найдена.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<Void> deleteSpeciality(@PathVariable String id) {
    specialityService.deleteSpeciality(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
