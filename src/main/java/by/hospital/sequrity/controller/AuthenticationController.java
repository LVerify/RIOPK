package by.hospital.sequrity.controller;

import by.hospital.exception.handler.ErrorResponse;
import by.hospital.sequrity.dto.JwtAuthenticationResponse;
import by.hospital.sequrity.dto.SignInRequest;
import by.hospital.sequrity.dto.SignUpRequest;
import by.hospital.sequrity.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
  private AuthenticationService authenticationService;

  @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Регистрация нового пользователя",
      description =
          "Эндпоинт для регистрации нового пользователя. При успешной регистрации возвращает JWT-токен для авторизации.")
  @ApiResponse(
      responseCode = "201",
      description = "Пользователь успешно зарегистрирован.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = JwtAuthenticationResponse.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Нарушена структура объекта или данные объекта не валидны.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  @ApiResponse(
      responseCode = "409",
      description = "Пользователь уже существует.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ErrorResponse.class)))
  public ResponseEntity<JwtAuthenticationResponse> signUp(
      @RequestBody @Valid SignUpRequest request) {
    JwtAuthenticationResponse response = authenticationService.signUp(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/sign-in")
  @Operation(
      summary = "Авторизация пользователя",
      description =
          "Эндпоинт для входа пользователя с использованием предоставленных учетных данных. Возвращает JWT токен для авторизации.")
  @ApiResponse(
      responseCode = "200",
      description = "Пользователь успешно авторизован.",
      content =
          @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = JwtAuthenticationResponse.class)))
  @ApiResponse(
      responseCode = "403",
      description = "Нарушена структура объекта или данные объекта не валидны.",
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
  public ResponseEntity<JwtAuthenticationResponse> signIn(
      @RequestBody @Valid SignInRequest request) {
    JwtAuthenticationResponse response = authenticationService.signIn(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
