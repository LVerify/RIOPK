package by.hospital.diseasehistory.entity.statusconstraint;

import by.hospital.diseasehistory.entity.PrescriptionStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class AllowedStatusValidator
    implements ConstraintValidator<AllowedStatuses, PrescriptionStatus> {
  private PrescriptionStatus[] allowedStatuses;

  @Override
  public void initialize(AllowedStatuses constraintAnnotation) {
    allowedStatuses = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(PrescriptionStatus value, ConstraintValidatorContext context) {
    return value != null && Arrays.asList(allowedStatuses).contains(value);
  }
}
