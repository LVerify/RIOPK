package by.hospital.exception;

public class StatusTransitionNotAllowedException extends RuntimeException {
  public StatusTransitionNotAllowedException(String message) {
    super(message);
  }
}
