package by.hospital.diseasehistory.entity;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public enum PrescriptionStatus {
  CREATED,
  APPROVED,
  IN_STOCK_CHECK,
  IN_STOCK,
  OUT_OF_STOCK,
  ORDERED,
  DELIVERED,
  RECEIVED_BY_NURSE,
  CANCELLED,
  COMPLETED;

  private static final Map<PrescriptionStatus, Set<PrescriptionStatus>> transitions =
      new EnumMap<>(PrescriptionStatus.class);

  static {
    transitions.put(CREATED, Set.of(CREATED, APPROVED, CANCELLED));
    transitions.put(APPROVED, Set.of(APPROVED, CREATED, IN_STOCK_CHECK, CANCELLED));
    transitions.put(IN_STOCK_CHECK, Set.of(IN_STOCK_CHECK, IN_STOCK, OUT_OF_STOCK, CANCELLED));
    transitions.put(OUT_OF_STOCK, Set.of(OUT_OF_STOCK, ORDERED, CANCELLED));
    transitions.put(ORDERED, Set.of(ORDERED, DELIVERED, CANCELLED));
    transitions.put(DELIVERED, Set.of(DELIVERED, RECEIVED_BY_NURSE, CANCELLED));
    transitions.put(IN_STOCK, Set.of(IN_STOCK, RECEIVED_BY_NURSE, CANCELLED));
    transitions.put(RECEIVED_BY_NURSE, Set.of(RECEIVED_BY_NURSE, COMPLETED, CANCELLED));
  }

  public boolean canTransitionTo(PrescriptionStatus newStatus) {
    return transitions.getOrDefault(this, Set.of()).contains(newStatus);
  }
}
