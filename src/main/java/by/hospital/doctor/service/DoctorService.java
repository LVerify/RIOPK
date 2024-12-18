package by.hospital.doctor.service;

import by.hospital.doctor.dto.DoctorCreateDTO;
import by.hospital.doctor.dto.DoctorReadDTO;
import by.hospital.doctor.dto.DoctorUpdateDTO;
import by.hospital.doctor.entity.Doctor;
import by.hospital.doctor.repository.DoctorRepository;
import by.hospital.doctor.service.mapper.DoctorMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorService {

  private static final String DOCTOR_NOT_FOUND = "Сотрудник не найден";

  private DoctorMapper doctorMapper;
  private DoctorRepository doctorRepository;

  public Doctor saveDoctor(Doctor doctor) {
    return doctorRepository.save(doctor);
  }

  public DoctorReadDTO createDoctor(DoctorCreateDTO dto) {
    SecurityContext context = SecurityContextHolder.getContext();
    if (doctorRepository.existsByUser_Name(context.getAuthentication().getName()))
      throw new DataAlreadyExistsException("На данного пользователя уже зарегистрирован сотрудник");
    return doctorMapper.toDto(saveDoctor(doctorMapper.toEntity(dto)));
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
}
