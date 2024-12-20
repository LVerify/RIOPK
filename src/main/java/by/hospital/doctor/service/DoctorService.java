package by.hospital.doctor.service;

import static by.hospital.doctor.service.DepartmentService.DEPARTMENT_NOT_FOUND;
import static by.hospital.doctor.service.SpecialityService.SPECIALITY_NOT_FOUND;

import by.hospital.doctor.dto.DoctorCreateDTO;
import by.hospital.doctor.dto.DoctorReadDTO;
import by.hospital.doctor.dto.DoctorUpdateDTO;
import by.hospital.doctor.entity.Doctor;
import by.hospital.doctor.repository.DepartmentRepository;
import by.hospital.doctor.repository.DoctorRepository;
import by.hospital.doctor.repository.SpecialityRepository;
import by.hospital.doctor.service.mapper.DoctorMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import by.hospital.user.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorService {

  public static final String DOCTOR_NOT_FOUND = "Сотрудник не найден";

  private UserService userService;
  private DoctorMapper doctorMapper;
  private DoctorRepository doctorRepository;
  private DepartmentRepository departmentRepository;
  private SpecialityRepository specialityRepository;

  public Doctor saveDoctor(Doctor doctor) {
    return doctorRepository.save(doctor);
  }

  public DoctorReadDTO createDoctor(DoctorCreateDTO dto) {
    if (doctorRepository.existsByUser(userService.getCurrentUser()))
      throw new DataAlreadyExistsException("На данного пользователя уже зарегистрирован сотрудник");
    Doctor newDoctor = doctorMapper.toEntity(dto);
    setDepartmentAndSpeciality(newDoctor, dto);
    return doctorMapper.toDto(saveDoctor(newDoctor));
  }

  public DoctorReadDTO updateDoctor(String id, DoctorUpdateDTO dto) {
    Doctor updatedDoctor =
        doctorRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DOCTOR_NOT_FOUND));
    doctorMapper.updateEntity(dto, updatedDoctor);
    return doctorMapper.toDto(saveDoctor(updatedDoctor));
  }

  public DoctorReadDTO getDoctor(String id) {
    Doctor existingDoctor =
        doctorRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DOCTOR_NOT_FOUND));
    return doctorMapper.toDto(existingDoctor);
  }

  public List<DoctorReadDTO> getAllDoctors() {
    return doctorRepository.findAll().stream().map(doctorMapper::toDto).toList();
  }

  private void setDepartmentAndSpeciality(Doctor doctor, DoctorCreateDTO dto) {
    doctor.setDepartment(
        departmentRepository
            .findById(dto.getDepartmentId())
            .orElseThrow(() -> new DataNotFoundException(DEPARTMENT_NOT_FOUND)));
    doctor.setSpeciality(
        specialityRepository
            .findById(dto.getSpecialityId())
            .orElseThrow(() -> new DataNotFoundException(SPECIALITY_NOT_FOUND)));
  }
}
