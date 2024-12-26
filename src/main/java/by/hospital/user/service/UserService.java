package by.hospital.user.service;

import by.hospital.exception.DataAlreadyExistsException;
import by.hospital.exception.DataNotFoundException;
import by.hospital.user.dto.UserReadDTO;
import by.hospital.user.dto.UserUpdateDTO;
import by.hospital.user.entity.User;
import by.hospital.user.repository.UserRepository;
import by.hospital.user.service.mapper.UserMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private static final String NOT_FOUND = "Пользователь не найден";

  private UserRepository userRepository;
  private UserMapper userMapper;

  public User save(User user) {
    return userRepository.save(user);
  }

  public User createUser(User user) throws DataAlreadyExistsException, DataNotFoundException {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new DataAlreadyExistsException("Пользователь с таким именем уже существует");
    }

    return save(user);
  }

  public User getByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new DataNotFoundException(NOT_FOUND));
  }

  public UserReadDTO updateUser(String id, UserUpdateDTO dto) {
    User updatedUser =
        userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NOT_FOUND));
    userMapper.updateEntity(dto, updatedUser);
    return userMapper.toDto(save(updatedUser));
  }

  public UserReadDTO getUserById(String id) {
    var user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException(NOT_FOUND));
    return userMapper.toDto(user);
  }

  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  public User getCurrentUser() {
    // Получение имени пользователя из контекста Spring Security
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return getByUsername(username);
  }

  public List<UserReadDTO> getAllUsers() {
    return userRepository.findAll().stream().map(userMapper::toDto).toList();
  }
}
