package by.hospital.doctor.service;

import by.hospital.doctor.dto.DepartmentCreateUpdateDTO;
import by.hospital.doctor.dto.DepartmentReadDTO;
import by.hospital.doctor.entity.Department;
import by.hospital.doctor.repository.DepartmentRepository;
import by.hospital.doctor.service.mapper.DepartmentMapper;
import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentService {
  private static final String DEPARTMENT_NOT_FOUND = "Отделение не найдено";

  private DepartmentMapper departmentMapper;
  private DepartmentRepository departmentRepository;

  public Department saveDepartment(Department department) {
    return departmentRepository.save(department);
  }

  public DepartmentReadDTO createDepartment(DepartmentCreateUpdateDTO dto) {
    if (departmentRepository.existsByName(dto.getName()))
      throw new DataAlreadyExistsException("Такое отделение уже существует");
    return departmentMapper.entityToDto(saveDepartment(departmentMapper.dtoToEntity(dto)));
  }

  public DepartmentReadDTO updateDepartment(String id, DepartmentCreateUpdateDTO dto) {
    Department updatedDepartment =
        departmentRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DEPARTMENT_NOT_FOUND));
    departmentMapper.updateEntity(dto, updatedDepartment);
    return departmentMapper.entityToDto(saveDepartment(updatedDepartment));
  }

  public DepartmentReadDTO getDepartment(String id) {
    Department existingDepartment =
        departmentRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException(DEPARTMENT_NOT_FOUND));
    return departmentMapper.entityToDto(existingDepartment);
  }

  public List<DepartmentReadDTO> getAllDepartments() {
    return departmentRepository.findAll().stream().map(departmentMapper::entityToDto).toList();
  }

  public void deleteDepartment(String id) {
    if (!departmentRepository.existsById(id)) throw new DataNotFoundException(DEPARTMENT_NOT_FOUND);
    departmentRepository.deleteById(id);
  }
}
