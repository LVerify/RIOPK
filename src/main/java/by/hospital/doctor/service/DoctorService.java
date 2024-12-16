package by.hospital.doctor.service;

import by.hospital.doctor.dto.DoctorCreateDTO;
import by.hospital.doctor.dto.DoctorReadDTO;
import by.hospital.doctor.dto.DoctorUpdateDTO;
import by.hospital.doctor.entity.Doctor;
import by.hospital.doctor.repository.DoctorRepository;
import by.hospital.doctor.service.mapper.DoctorMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import by.hospital.user.repository.UserRepository;
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
  private UserRepository userRepository;

  public Doctor saveDoctor(Doctor doctor) {
    return doctorRepository.save(doctor);
  }

  public DoctorReadDTO createDoctor(DoctorCreateDTO dto) {
    SecurityContext context = SecurityContextHolder.getContext();
    if (doctorRepository.existsByUser_Name(context.getAuthentication().getName()))
      throw new DataAlreadyExistsException("На данного пользователя уже зарегистрирован сотрудник");
    return doctorMapper.entityToDto(saveDoctor(doctorMapper.dtoToEntity(dto)));
  }

  public DoctorReadDTO updateDoctor(String id, DoctorUpdateDTO dto) {
    Doctor updatedDoctor =
        doctorRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DOCTOR_NOT_FOUND));
    doctorMapper.updateEntity(dto, updatedDoctor);
    return doctorMapper.entityToDto(saveDoctor(updatedDoctor));
  }

  public DoctorReadDTO getDoctor(String id) {
    Doctor existingDoctor =
        doctorRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DOCTOR_NOT_FOUND));
    return doctorMapper.entityToDto(existingDoctor);
  }

  public List<DoctorReadDTO> getAllDoctors() {
    return doctorRepository.findAll().stream().map(doctorMapper::entityToDto).toList();
  }

  public void deleteDoctor(String id) {
    if (!doctorRepository.existsById(id)) throw new DataNotFoundException(DOCTOR_NOT_FOUND);
    doctorRepository.deleteById(id);
  }
}
