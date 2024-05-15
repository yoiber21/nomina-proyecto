package org.example.nominatercercorte.service;

import java.util.Map;
import org.example.nominatercercorte.dto.RegisterRequestDTO;
import org.example.nominatercercorte.dto.RegisterResponseDTO;
import org.example.nominatercercorte.dto.UserEmployeeResponseDTO;

public interface IUserEmployeeService {

  UserEmployeeResponseDTO getUserById(Long id);

  RegisterResponseDTO createUser(UserEmployeeResponseDTO userEmployeeResponseDTO);

  Map<String, Object> updateUser(UserEmployeeResponseDTO userEmployeeResponseDTO, String id);

  void deleteUser(Long id);
}
