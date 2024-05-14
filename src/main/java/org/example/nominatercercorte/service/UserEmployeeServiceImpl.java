package org.example.nominatercercorte.service;

import org.example.nominatercercorte.dto.RegisterRequestDTO;
import org.example.nominatercercorte.dto.UserEmployeeResponseDTO;
import org.example.nominatercercorte.exception.NotFoundException;
import org.example.nominatercercorte.model.UserEntity;
import org.example.nominatercercorte.repository.IUserEmployeeRepository;
import org.example.nominatercercorte.repository.IUserRepository;
import org.example.nominatercercorte.util.Rol;
import org.springframework.stereotype.Service;

@Service
public class UserEmployeeServiceImpl implements IUserEmployeeService {

  private final IUserEmployeeRepository userEmployeeRepository;
  private final IUserRepository userRepository;
  AuthServiceImpl authService;

  public UserEmployeeServiceImpl(IUserEmployeeRepository userEmployeeRepository, IUserRepository userRepository) {
    this.userEmployeeRepository = userEmployeeRepository;
    this.userRepository = userRepository;
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
        .rol(employee.getRol().toString())
        .build();
  }

  @Override
  public UserEmployeeResponseDTO createUser(RegisterRequestDTO userEmployeeResponseDTO) {
    AuthServiceImpl.validateRegisters(userEmployeeResponseDTO, userRepository);
    authService.register(userEmployeeResponseDTO);

    return UserEmployeeResponseDTO.builder()
        .username(userEmployeeResponseDTO.getUsername())
        .name(userEmployeeResponseDTO.getFirstName())
        .lastName(userEmployeeResponseDTO.getLastName())
        .rol("Empleado")
        .email(userEmployeeResponseDTO.getEmail())
        .phone(userEmployeeResponseDTO.getPhone())
        .address(userEmployeeResponseDTO.getAddress())
        .build();
  }

  @Override
  public UserEmployeeResponseDTO updateUser(UserEmployeeResponseDTO userEmployeeResponseDTO, String id) {

    UserEntity existingEmployee = userEmployeeRepository.findById(Long.valueOf(id))
        .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));

    if (userEmployeeResponseDTO.getName() != null && !userEmployeeResponseDTO.getName().isEmpty()) {
      existingEmployee.setFirstName(userEmployeeResponseDTO.getName());
    }
    if (userEmployeeResponseDTO.getLastName() != null && !userEmployeeResponseDTO.getLastName().isEmpty()) {
      existingEmployee.setLastName(userEmployeeResponseDTO.getLastName());
    }
    if (userEmployeeResponseDTO.getEmail() != null && !userEmployeeResponseDTO.getEmail().isEmpty()) {
      existingEmployee.setEmail(userEmployeeResponseDTO.getEmail());
    }
    if (userEmployeeResponseDTO.getPhone() != null && !userEmployeeResponseDTO.getPhone().isEmpty()) {
      existingEmployee.setPhone(userEmployeeResponseDTO.getPhone());
    }
    if (userEmployeeResponseDTO.getAddress() != null && !userEmployeeResponseDTO.getAddress().isEmpty()) {
      existingEmployee.setAddress(userEmployeeResponseDTO.getAddress());
    }
    if (userEmployeeResponseDTO.getRol() != null && !userEmployeeResponseDTO.getRol().isEmpty()) {
      existingEmployee.setRol(Rol.valueOf(userEmployeeResponseDTO.getRol()));
    }

    existingEmployee = userEmployeeRepository.save(existingEmployee);

    return UserEmployeeResponseDTO.builder()
        .username(existingEmployee.getUsername())
        .name(existingEmployee.getFirstName())
        .lastName(existingEmployee.getLastName())
        .email(existingEmployee.getEmail())
        .phone(existingEmployee.getPhone())
        .address(existingEmployee.getAddress())
        .rol(existingEmployee.getRol().toString())
        .build();
  }

  @Override
  public void deleteUser(Long id) {
    if (!userEmployeeRepository.existsById(id)) {
      throw new NotFoundException("The user id doesn't exist");
    }
    userEmployeeRepository.deleteById(id);

  }
}
