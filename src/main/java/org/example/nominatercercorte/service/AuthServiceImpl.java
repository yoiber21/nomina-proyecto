package org.example.nominatercercorte.service;

import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.nominatercercorte.dto.AuthResponseDTO;
import org.example.nominatercercorte.dto.LoginRequestDTO;
import org.example.nominatercercorte.dto.RegisterRequestDTO;
import org.example.nominatercercorte.dto.UserEmployeeResponseDTO;
import org.example.nominatercercorte.exception.AlreadyExistsException;
import org.example.nominatercercorte.exception.BadAuthenticationException;
import org.example.nominatercercorte.jwt.JwtService;
import org.example.nominatercercorte.model.UserEntity;
import org.example.nominatercercorte.repository.IUserRepository;
import org.example.nominatercercorte.util.Rol;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

  private final IUserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  public AuthResponseDTO login(LoginRequestDTO userDto) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
    } catch (BadCredentialsException e) {
      throw new BadAuthenticationException("Credenciales incorrectas.");
    } catch (AuthenticationException e) {
      throw new BadAuthenticationException("Authentication failed.");
    }

    UserEntity user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));
    String token = jwtService.getToken(user);
    return AuthResponseDTO.builder()
        .token(token)
        .username(user.getUsername())
        .email(user.getEmail())
        .build();
  }


  @Override
  public AuthResponseDTO register(RegisterRequestDTO userToRegisterDto) {
    validateRegisters(userToRegisterDto, userRepository);

    UserEntity user = UserEntity.builder()
        .username(userToRegisterDto.getUsername())
        .password(passwordEncoder.encode(userToRegisterDto.getPassword()))
        .firstName(userToRegisterDto.getFirstName())
        .lastName(userToRegisterDto.getLastName())
        .rol(Rol.USER)
        .email(userToRegisterDto.getEmail())
        .phone(userToRegisterDto.getPhone())
        .address(userToRegisterDto.getAddress())
        .createdAt(Date.from(java.time.Instant.now()))
        .build();


    userRepository.save(user);

    return AuthResponseDTO.builder()
        .token(jwtService.getToken(user))
        .username(user.getUsername())
        .email(user.getEmail())
        .build();
  }

  static void validateRegisters(RegisterRequestDTO userToRegisterDto,
                                IUserRepository userRepository) {
    getExisitingUser(userRepository,
        userToRegisterDto.getUsername(),
        userToRegisterDto.getEmail(),
        userToRegisterDto.getPhone()
    );
  }


  static void validateRegisters(UserEmployeeResponseDTO userEmployeeResponseDTO,
                                IUserRepository userRepository) {
    getExisitingUser(userRepository, userEmployeeResponseDTO.getUsername(),
        userEmployeeResponseDTO.getEmail(),
        userEmployeeResponseDTO.getPhone()
    );
  }

  private static void getExisitingUser(IUserRepository userRepository,
                                       String username,
                                       String email,
                                       String phone) {
    Optional<UserEntity> existingUser = userRepository.findByUsername(username);
    Optional<UserEntity> existingEmail = userRepository.findByEmail(email);
    Optional<UserEntity> existingPhone = userRepository.findByPhone(phone);
    if (existingUser.isPresent()) {
      throw new AlreadyExistsException("El nombre de usuario ya está en uso");
    }
    if (existingEmail.isPresent()) {
      throw new AlreadyExistsException("El correo electronico ya está en uso");
    }
    if (existingPhone.isPresent()) {
      throw new AlreadyExistsException("El número de teléfono ya está en uso");
    }
  }
}
