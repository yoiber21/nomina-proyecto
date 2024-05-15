package org.example.nominatercercorte.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.example.nominatercercorte.dto.RegisterResponseDTO;
import org.example.nominatercercorte.dto.UserEmployeeResponseDTO;
import org.example.nominatercercorte.exception.NotFoundException;
import org.example.nominatercercorte.model.UserEntity;
import org.example.nominatercercorte.repository.IUserEmployeeRepository;
import org.example.nominatercercorte.repository.IUserRepository;
import org.example.nominatercercorte.util.Rol;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEmployeeServiceImpl implements IUserEmployeeService {

  private final IUserEmployeeRepository userEmployeeRepository;
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserEmployeeServiceImpl(IUserEmployeeRepository userEmployeeRepository, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userEmployeeRepository = userEmployeeRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserEmployeeResponseDTO getUserById(Long id) {
    UserEntity employee = userEmployeeRepository.findUserEntityById(id);
    if (!userEmployeeRepository.existsById(id)) {
      throw new NotFoundException("The user id doesn't exist");
    }
    return UserEmployeeResponseDTO.builder()
        .username(employee.getUsername())
        .name(employee.getFirstName())
        .lastName(employee.getLastName())
        .email(employee.getEmail())
        .phone(employee.getPhone())
        .address(employee.getAddress())
        .rol("EMPLEADO")
        .build();
  }

  @Override
  public RegisterResponseDTO createUser(UserEmployeeResponseDTO userEmployeeResponseDTO) {
    AuthServiceImpl.validateRegisters(userEmployeeResponseDTO, userRepository);
    userEmployeeRepository.save(UserEntity.builder()
        .username(userEmployeeResponseDTO.getUsername())
        .password(passwordEncoder.encode(userEmployeeResponseDTO.getPassword()))
        .firstName(userEmployeeResponseDTO.getName())
        .lastName(userEmployeeResponseDTO.getLastName())
        .email(userEmployeeResponseDTO.getEmail())
        .phone(userEmployeeResponseDTO.getPhone())
        .address(userEmployeeResponseDTO.getAddress())
        .createdAt(Date.from(java.time.Instant.now()))
        .rol(userEmployeeResponseDTO.getRol() != null ? Rol.valueOf(userEmployeeResponseDTO.getRol()) : Rol.USER)
        .build());

    UserEmployeeResponseDTO user = UserEmployeeResponseDTO.builder()
        .username(userEmployeeResponseDTO.getUsername())
        .name(userEmployeeResponseDTO.getName())
        .lastName(userEmployeeResponseDTO.getLastName())
        .rol("USER".equals(userEmployeeResponseDTO.getRol()) ? "GESTION ADMINISTRATIVA" : "EMPLEADO")
        .email(userEmployeeResponseDTO.getEmail())
        .phone(userEmployeeResponseDTO.getPhone())
        .address(userEmployeeResponseDTO.getAddress())
        .build();

    return RegisterResponseDTO.builder()
        .message("User created successfully")
        .data(user)
        .build();
  }

  @Override
  public Map<String, Object> updateUser(UserEmployeeResponseDTO userEmployeeResponseDTO, String id) {

    UserEntity existingEmployee = userEmployeeRepository.findById(Long.valueOf(id))
        .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));

    Map<String, Object> updatedFields = new HashMap<>();

    if (userEmployeeResponseDTO.getName() != null && !userEmployeeResponseDTO.getName().isEmpty()) {
      existingEmployee.setFirstName(userEmployeeResponseDTO.getName());
      updatedFields.put("first_name", userEmployeeResponseDTO.getName());
    }
    if (userEmployeeResponseDTO.getLastName() != null && !userEmployeeResponseDTO.getLastName().isEmpty()) {
      existingEmployee.setLastName(userEmployeeResponseDTO.getLastName());
      updatedFields.put("last_name", userEmployeeResponseDTO.getLastName());
    }
    if (userEmployeeResponseDTO.getEmail() != null && !userEmployeeResponseDTO.getEmail().isEmpty()) {
      existingEmployee.setEmail(userEmployeeResponseDTO.getEmail());
      updatedFields.put("email", userEmployeeResponseDTO.getEmail());
    }
    if (userEmployeeResponseDTO.getPhone() != null && !userEmployeeResponseDTO.getPhone().isEmpty()) {
      existingEmployee.setPhone(userEmployeeResponseDTO.getPhone());
      updatedFields.put("phone", userEmployeeResponseDTO.getPhone());
    }
    if (userEmployeeResponseDTO.getAddress() != null && !userEmployeeResponseDTO.getAddress().isEmpty()) {
      existingEmployee.setAddress(userEmployeeResponseDTO.getAddress());
      updatedFields.put("address", userEmployeeResponseDTO.getAddress());
    }
    if (userEmployeeResponseDTO.getRol() != null && !userEmployeeResponseDTO.getRol().isEmpty()) {
      existingEmployee.setRol(Rol.valueOf(userEmployeeResponseDTO.getRol()));
      updatedFields.put("rol", userEmployeeResponseDTO.getRol());
    }

    existingEmployee.setUpdatedAt(Date.from(java.time.Instant.now()));

    userEmployeeRepository.save(existingEmployee);

    return updatedFields;
  }

  @Override
  public void deleteUser(Long id) {
    if (!userEmployeeRepository.existsById(id)) {
      throw new NotFoundException("The user id doesn't exist");
    }
    userEmployeeRepository.deleteById(id);

  }
}
