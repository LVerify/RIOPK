package by.hospital.exception.handler;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
  private ErrorCode errorCode;
  private String message;
  private LocalDateTime timestamp;
}
