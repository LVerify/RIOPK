package by.hospital.exception;

public class DataAlreadyExistsException extends RuntimeException {
  public DataAlreadyExistsException(String message) {
    super(message);
  }
}
