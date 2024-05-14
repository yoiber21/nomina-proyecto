package org.example.nominatercercorte.service;

import org.example.nominatercercorte.dto.AuthResponseDTO;
import org.example.nominatercercorte.dto.RegisterRequestDTO;
import org.example.nominatercercorte.dto.UserEmployeeResponseDTO;

public interface IUserEmployeeService {

  UserEmployeeResponseDTO getUserById(Long id);

  UserEmployeeResponseDTO createUser(RegisterRequestDTO userEmployeeResponseDTO);

  UserEmployeeResponseDTO updateUser(UserEmployeeResponseDTO userEmployeeResponseDTO, String id);

  void deleteUser(Long id);
}
