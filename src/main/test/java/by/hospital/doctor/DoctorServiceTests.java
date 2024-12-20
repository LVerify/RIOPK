package by.hospital.doctor;

import by.hospital.doctor.dto.DoctorCreateDTO;
import by.hospital.doctor.dto.DoctorReadDTO;
import by.hospital.doctor.dto.DoctorUpdateDTO;
import by.hospital.doctor.entity.Department;
import by.hospital.doctor.entity.Doctor;
import by.hospital.doctor.entity.Speciality;
import by.hospital.doctor.repository.DepartmentRepository;
import by.hospital.doctor.repository.DoctorRepository;
import by.hospital.doctor.repository.SpecialityRepository;
import by.hospital.doctor.service.DoctorService;
import by.hospital.doctor.service.mapper.DoctorMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import by.hospital.user.entity.User;
import by.hospital.user.service.UserService;
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
class DoctorServiceTests {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private UserService userService;

    @Mock
    private DoctorMapper doctorMapper;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private SpecialityRepository specialityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDoctor_Success() {
        DoctorCreateDTO dto = new DoctorCreateDTO();
        Doctor doctor = new Doctor();
        DoctorReadDTO readDTO = new DoctorReadDTO();
        User currentUser = new User();

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(doctorRepository.existsByUser(currentUser)).thenReturn(false);
        when(doctorMapper.toEntity(dto)).thenReturn(doctor);
        when(departmentRepository.findById(anyString())).thenReturn(Optional.of(new Department()));
        when(specialityRepository.findById(anyString())).thenReturn(Optional.of(new Speciality()));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(doctorMapper.toDto(doctor)).thenReturn(readDTO);

        DoctorReadDTO result = doctorService.createDoctor(dto);

        assertNotNull(result);
        verify(doctorRepository).save(doctor);
        verify(doctorMapper).toDto(doctor);
    }

    @Test
    void testCreateDoctor_AlreadyExists() {
        DoctorCreateDTO dto = new DoctorCreateDTO();
        User currentUser = new User();

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(doctorRepository.existsByUser(currentUser)).thenReturn(true);

        assertThrows(
                DataAlreadyExistsException.class,
                () -> doctorService.createDoctor(dto)
        );
        verify(doctorRepository, never()).save(any());
    }

    @Test
    void testUpdateDoctor_Success() {
        String id = "doctorId";
        DoctorUpdateDTO dto = new DoctorUpdateDTO();
        Doctor doctor = new Doctor();
        DoctorReadDTO readDTO = new DoctorReadDTO();

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(doctorMapper.toDto(doctor)).thenReturn(readDTO);

        DoctorReadDTO result = doctorService.updateDoctor(id, dto);

        assertNotNull(result);
        verify(doctorMapper).updateEntity(dto, doctor);
        verify(doctorRepository).save(doctor);
    }

    @Test
    void testUpdateDoctor_NotFound() {
        String id = "doctorId";
        DoctorUpdateDTO dto = new DoctorUpdateDTO();

        when(doctorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                DataNotFoundException.class,
                () -> doctorService.updateDoctor(id, dto)
        );
        verify(doctorRepository, never()).save(any());
    }

    @Test
    void testGetDoctor_Success() {
        String id = "doctorId";
        Doctor doctor = new Doctor();
        DoctorReadDTO readDTO = new DoctorReadDTO();

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDto(doctor)).thenReturn(readDTO);

        DoctorReadDTO result = doctorService.getDoctor(id);

        assertNotNull(result);
        verify(doctorRepository).findById(id);
        verify(doctorMapper).toDto(doctor);
    }

    @Test
    void testGetDoctor_NotFound() {
        String id = "doctorId";

        when(doctorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                DataNotFoundException.class,
                () -> doctorService.getDoctor(id)
        );
    }

    @Test
    void testGetAllDoctors_Success() {
        List<Doctor> doctors = List.of(new Doctor(), new Doctor());
        List<DoctorReadDTO> readDTOs = List.of(new DoctorReadDTO(), new DoctorReadDTO());

        when(doctorRepository.findAll()).thenReturn(doctors);
        when(doctorMapper.toDto(any(Doctor.class))).thenReturn(new DoctorReadDTO());

        List<DoctorReadDTO> result = doctorService.getAllDoctors();

        assertEquals(readDTOs.size(), result.size());
        verify(doctorRepository).findAll();
        verify(doctorMapper, times(doctors.size())).toDto(any(Doctor.class));
    }

    @Test
    void testSetDepartmentAndSpeciality_Success() {
        DoctorCreateDTO dto = new DoctorCreateDTO();
        dto.setDepartmentId("deptId");
        dto.setSpecialityId("specId");

        when(departmentRepository.findById("deptId")).thenReturn(Optional.of(new Department()));
        when(specialityRepository.findById("specId")).thenReturn(Optional.of(new Speciality()));

        doctorService.createDoctor(dto);

        verify(departmentRepository).findById("deptId");
        verify(specialityRepository).findById("specId");
    }

    @Test
    void testSetDepartmentAndSpeciality_DepartmentNotFound() {
        DoctorCreateDTO dto = new DoctorCreateDTO();
        dto.setDepartmentId("deptId");
        dto.setSpecialityId("specId");

        when(departmentRepository.findById("deptId")).thenReturn(Optional.empty());

        assertThrows(
                DataNotFoundException.class,
                () -> doctorService.createDoctor(dto)
        );
        verify(departmentRepository).findById("deptId");
        verify(specialityRepository, never()).findById(any());
    }

}
