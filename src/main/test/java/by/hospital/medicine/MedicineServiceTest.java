package by.hospital.medicine;

import by.hospital.exception.DataNotFoundException;
import by.hospital.medicine.dto.MedicineCreateUpdateDTO;
import by.hospital.medicine.dto.MedicineReadDTO;
import by.hospital.medicine.entity.Medicine;
import by.hospital.medicine.repository.MedicineRepository;
import by.hospital.medicine.service.MedicineService;
import by.hospital.medicine.service.mapper.MedicineMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock
    private MedicineMapper medicineMapper;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineService medicineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMedicine_Success() {
        // Arrange
        Medicine medicine = new Medicine();
        when(medicineRepository.save(medicine)).thenReturn(medicine);

        // Act
        Medicine result = medicineService.saveMedicine(medicine);

        // Assert
        assertEquals(medicine, result);
        verify(medicineRepository).save(medicine);
    }

    @Test
    void testCreateMedicine_Success() {
        // Arrange
        MedicineCreateUpdateDTO dto = new MedicineCreateUpdateDTO();
        Medicine medicine = new Medicine();
        MedicineReadDTO medicineReadDTO = new MedicineReadDTO();

        when(medicineMapper.toEntity(dto)).thenReturn(medicine);
        when(medicineRepository.save(medicine)).thenReturn(medicine);
        when(medicineMapper.toDto(medicine)).thenReturn(medicineReadDTO);

        // Act
        MedicineReadDTO result = medicineService.createMedicine(dto);

        // Assert
        assertEquals(medicineReadDTO, result);
        verify(medicineMapper).toEntity(dto);
        verify(medicineRepository).save(medicine);
        verify(medicineMapper).toDto(medicine);
    }

    @Test
    void testUpdateMedicine_Success() {
        // Arrange
        String id = "medicineId";
        MedicineCreateUpdateDTO dto = new MedicineCreateUpdateDTO();
        Medicine existingMedicine = new Medicine();
        MedicineReadDTO medicineReadDTO = new MedicineReadDTO();

        when(medicineRepository.findById(id)).thenReturn(Optional.of(existingMedicine));
        when(medicineRepository.save(existingMedicine)).thenReturn(existingMedicine);
        when(medicineMapper.toDto(existingMedicine)).thenReturn(medicineReadDTO);

        // Act
        MedicineReadDTO result = medicineService.updateMedicine(id, dto);

        // Assert
        assertEquals(medicineReadDTO, result);
        verify(medicineRepository).findById(id);
        verify(medicineMapper).updateEntity(dto, existingMedicine);
        verify(medicineRepository).save(existingMedicine);
        verify(medicineMapper).toDto(existingMedicine);
    }

    @Test
    void testUpdateMedicine_NotFound() {
        // Arrange
        String id = "nonExistentId";
        MedicineCreateUpdateDTO dto = new MedicineCreateUpdateDTO();

        when(medicineRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> medicineService.updateMedicine(id, dto));
        verify(medicineRepository).findById(id);
        verifyNoInteractions(medicineMapper);
    }

    @Test
    void testGetMedicine_Success() {
        // Arrange
        String id = "medicineId";
        Medicine medicine = new Medicine();
        MedicineReadDTO medicineReadDTO = new MedicineReadDTO();

        when(medicineRepository.findById(id)).thenReturn(Optional.of(medicine));
        when(medicineMapper.toDto(medicine)).thenReturn(medicineReadDTO);

        // Act
        MedicineReadDTO result = medicineService.getMedicine(id);

        // Assert
        assertEquals(medicineReadDTO, result);
        verify(medicineRepository).findById(id);
        verify(medicineMapper).toDto(medicine);
    }

    @Test
    void testGetMedicine_NotFound() {
        // Arrange
        String id = "nonExistentId";

        when(medicineRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> medicineService.getMedicine(id));
        verify(medicineRepository).findById(id);
        verifyNoInteractions(medicineMapper);
    }

    @Test
    void testGetAllMedicines_Success() {
        // Arrange
        List<Medicine> medicines = List.of(new Medicine(), new Medicine()); // Список сущностей
        List<MedicineReadDTO> expectedDtos = List.of(new MedicineReadDTO(), new MedicineReadDTO()); // Список DTO

        when(medicineRepository.findAll()).thenReturn(medicines); // Мокаем вызов findAll()
        when(medicineMapper.toDto(any(Medicine.class)))
                .thenAnswer(invocation -> new MedicineReadDTO()); // Мокаем toDto()

        // Act
        List<MedicineReadDTO> result = medicineService.getAllMedicines();

        // Assert
        assertEquals(expectedDtos.size(), result.size()); // Сравниваем размеры списков
        verify(medicineRepository).findAll(); // Проверяем вызов findAll()
        verify(medicineMapper, times(medicines.size())).toDto(any(Medicine.class)); // Проверяем вызовы toDto()
    }


    @Test
    void testDeleteMedicine_Success() {
        // Arrange
        String id = "medicineId";

        when(medicineRepository.existsById(id)).thenReturn(true);

        // Act
        medicineService.deleteMedicine(id);

        // Assert
        verify(medicineRepository).existsById(id);
        verify(medicineRepository).deleteById(id);
    }

    @Test
    void testDeleteMedicine_NotFound() {
        // Arrange
        String id = "nonExistentId";

        when(medicineRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> medicineService.deleteMedicine(id));
        verify(medicineRepository).existsById(id);
        verifyNoInteractions(medicineMapper);
    }
}
