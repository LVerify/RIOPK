package by.hospital.accounting.service;

import by.hospital.accounting.model.Prescription;
import by.hospital.accounting.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found"));
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription updatePrescription(Long id, Prescription updatedPrescription) {
        Prescription prescription = getPrescriptionById(id);
        prescription.setMedicineName(updatedPrescription.getMedicineName());
        prescription.setDosage(updatedPrescription.getDosage());
        prescription.setPatient(updatedPrescription.getPatient());
        return prescriptionRepository.save(prescription);
    }
}
