package by.hospital.doctor.controller;

import by.hospital.doctor.dto.DepartmentCreateUpdateDTO;
import by.hospital.doctor.dto.DepartmentReadDTO;
import by.hospital.doctor.service.DepartmentService;
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
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentController {
  private DepartmentService departmentService;

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
              schema = @Schema(implementation = DepartmentReadDTO.class)))
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
  public ResponseEntity<DepartmentReadDTO> getDepartment(@PathVariable String id) {
    return new ResponseEntity<>(departmentService.getDepartment(id), HttpStatus.OK);
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
              schema = @Schema(implementation = DepartmentReadDTO.class)))
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
  public ResponseEntity<DepartmentReadDTO> createDepartment(
      @RequestBody @Valid DepartmentCreateUpdateDTO dto) {
    return new ResponseEntity<>(departmentService.createDepartment(dto), HttpStatus.CREATED);
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
              schema = @Schema(implementation = DepartmentReadDTO.class)))
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
  public ResponseEntity<DepartmentReadDTO> updateDepartment(
      @PathVariable String id, @RequestBody @Valid DepartmentCreateUpdateDTO dto) {
    return new ResponseEntity<>(departmentService.updateDepartment(id, dto), HttpStatus.OK);
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
              schema = @Schema(implementation = DepartmentReadDTO.class)))
  public ResponseEntity<List<DepartmentReadDTO>> getAllDepartments() {
    return ResponseEntity.ok(departmentService.getAllDepartments());
  }
}
