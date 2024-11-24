package by.hospital.accounting.service;

import by.hospital.accounting.model.Medicine;
import by.hospital.accounting.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public List<Medicine> searchMedicines(String name) {
        return medicineRepository.findByNameContaining(name);
    }
}
