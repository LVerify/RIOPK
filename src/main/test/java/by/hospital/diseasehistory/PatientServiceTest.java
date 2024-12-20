package by.hospital.diseasehistory;

import by.hospital.diseasehistory.dto.PatientCreateUpdateDTO;
import by.hospital.diseasehistory.dto.PatientReadDTO;
import by.hospital.diseasehistory.entity.Patient;
import by.hospital.diseasehistory.repository.PatientRepository;
import by.hospital.diseasehistory.service.PatientService;
import by.hospital.diseasehistory.service.mapper.PatientMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
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
class PatientServiceTest {

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePatient_Success() {
        // Arrange
        Patient patient = new Patient();
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient result = patientService.savePatient(patient);

        // Assert
        assertEquals(patient, result);
        verify(patientRepository).save(patient);
    }

    @Test
    void testCreatePatient_Success() {
        // Arrange
        PatientCreateUpdateDTO dto = new PatientCreateUpdateDTO();
        Patient patient = new Patient();
        PatientReadDTO patientReadDTO = new PatientReadDTO();

        when(patientRepository.existsByDateOfBirthAndMobilePhone(dto.getDateOfBirth(), dto.getMobilePhone()))
                .thenReturn(false);
        when(patientMapper.toEntity(dto)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toDto(patient)).thenReturn(patientReadDTO);

        // Act
        PatientReadDTO result = patientService.createPatient(dto);

        // Assert
        assertEquals(patientReadDTO, result);
        verify(patientRepository).existsByDateOfBirthAndMobilePhone(dto.getDateOfBirth(), dto.getMobilePhone());
        verify(patientMapper).toEntity(dto);
        verify(patientRepository).save(patient);
        verify(patientMapper).toDto(patient);
    }

    @Test
    void testCreatePatient_AlreadyExists() {
        // Arrange
        PatientCreateUpdateDTO dto = new PatientCreateUpdateDTO();

        when(patientRepository.existsByDateOfBirthAndMobilePhone(dto.getDateOfBirth(), dto.getMobilePhone()))
                .thenReturn(true);

        // Act & Assert
        assertThrows(DataAlreadyExistsException.class, () -> patientService.createPatient(dto));
        verify(patientRepository).existsByDateOfBirthAndMobilePhone(dto.getDateOfBirth(), dto.getMobilePhone());
        verifyNoInteractions(patientMapper);
    }

    @Test
    void testUpdatePatient_Success() {
        // Arrange
        String id = "patientId";
        PatientCreateUpdateDTO dto = new PatientCreateUpdateDTO();
        Patient existingPatient = new Patient();
        PatientReadDTO patientReadDTO = new PatientReadDTO();

        when(patientRepository.findById(id)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(existingPatient)).thenReturn(existingPatient);
        when(patientMapper.toDto(existingPatient)).thenReturn(patientReadDTO);

        // Act
        PatientReadDTO result = patientService.updatePatient(id, dto);

        // Assert
        assertEquals(patientReadDTO, result);
        verify(patientRepository).findById(id);
        verify(patientMapper).updateEntity(dto, existingPatient);
        verify(patientRepository).save(existingPatient);
        verify(patientMapper).toDto(existingPatient);
    }

    @Test
    void testUpdatePatient_NotFound() {
        // Arrange
        String id = "nonExistentId";
        PatientCreateUpdateDTO dto = new PatientCreateUpdateDTO();

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> patientService.updatePatient(id, dto));
        verify(patientRepository).findById(id);
        verifyNoInteractions(patientMapper);
    }

    @Test
    void testGetPatient_Success() {
        // Arrange
        String id = "patientId";
        Patient patient = new Patient();
        PatientReadDTO patientReadDTO = new PatientReadDTO();

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(patientMapper.toDto(patient)).thenReturn(patientReadDTO);

        // Act
        PatientReadDTO result = patientService.getPatient(id);

        // Assert
        assertEquals(patientReadDTO, result);
        verify(patientRepository).findById(id);
        verify(patientMapper).toDto(patient);
    }

    @Test
    void testGetPatient_NotFound() {
        // Arrange
        String id = "nonExistentId";

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> patientService.getPatient(id));
        verify(patientRepository).findById(id);
        verifyNoInteractions(patientMapper);
    }

    @Test
    void testGetAllPatients_Success() {
        // Arrange
        List<Patient> patients = List.of(new Patient(), new Patient());
        List<PatientReadDTO> patientReadDTOs = List.of(new PatientReadDTO(), new PatientReadDTO());

        when(patientRepository.findAll()).thenReturn(patients);
        when(patientMapper.toDto(any(Patient.class)))
                .thenAnswer(invocation -> new PatientReadDTO()); // Пример, если маппер возвращает новую DTO

        // Act
        List<PatientReadDTO> result = patientService.getAllPatients();

        // Assert
        assertEquals(patientReadDTOs.size(), result.size());
        verify(patientRepository).findAll();
        verify(patientMapper, times(patients.size())).toDto(any(Patient.class));
    }

    @Test
    void testDeletePatient_Success() {
        // Arrange
        String id = "patientId";

        when(patientRepository.existsById(id)).thenReturn(true);

        // Act
        patientService.deletePatient(id);

        // Assert
        verify(patientRepository).existsById(id);
        verify(patientRepository).deleteById(id);
    }

    @Test
    void testDeletePatient_NotFound() {
        // Arrange
        String id = "nonExistentId";

        when(patientRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(DataNotFoundException.class, () -> patientService.deletePatient(id));
        verify(patientRepository).existsById(id);
        verifyNoInteractions(patientMapper);
    }
}
