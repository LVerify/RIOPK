package by.hospital.diseasehistory.entity.statusconstraint;

import by.hospital.diseasehistory.entity.PrescriptionStatus;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowedStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedStatuses {
  PrescriptionStatus[] value();

  String message() default "Данная смена статуса недоступна";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
