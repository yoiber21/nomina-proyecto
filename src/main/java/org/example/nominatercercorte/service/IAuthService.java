package org.example.nominatercercorte.service;


import org.example.nominatercercorte.dto.AuthResponseDTO;
import org.example.nominatercercorte.dto.LoginRequestDTO;
import org.example.nominatercercorte.dto.RegisterRequestDTO;

public interface IAuthService {
  AuthResponseDTO login(LoginRequestDTO userDto);

  AuthResponseDTO register(RegisterRequestDTO userToRegisterDto);

}
