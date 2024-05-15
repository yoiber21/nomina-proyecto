package org.example.nominatercercorte.controller;

import jakarta.validation.Valid;
import java.util.Map;
import org.example.nominatercercorte.dto.RegisterRequestDTO;
import org.example.nominatercercorte.dto.RegisterResponseDTO;
import org.example.nominatercercorte.dto.UserEmployeeResponseDTO;
import org.example.nominatercercorte.exception.NotFoundException;
import org.example.nominatercercorte.jwt.JwtService;
import org.example.nominatercercorte.model.UserEntity;
import org.example.nominatercercorte.repository.IUserEmployeeRepository;
import org.example.nominatercercorte.service.IUserEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-employee")
public class UserEmployeeController {

  private final IUserEmployeeService userEmployeeService;
  private final IUserEmployeeRepository userEmployeeRepository;
  private final JwtService jwtService;

  public UserEmployeeController(IUserEmployeeService userEmployeeService, IUserEmployeeRepository userEmployeeRepository, JwtService jwtService) {
    this.userEmployeeService = userEmployeeService;
    this.userEmployeeRepository = userEmployeeRepository;
    this.jwtService = jwtService;
  }

  @PostMapping("/create")
  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  public ResponseEntity<RegisterResponseDTO> createUserEmployee(@Valid @RequestBody UserEmployeeResponseDTO userEmployeeDTO,
                                                                @RequestHeader("Authorization")
                                                                String token) {

    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    RegisterResponseDTO user = userEmployeeService.createUser(userEmployeeDTO);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Map<String, Object>> updateUserEmployee(
      @RequestBody UserEmployeeResponseDTO userEmployeeDTO,
      @RequestHeader("Authorization")
      String token,
      @PathVariable String id) {


    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    Map<String, Object> updatedFields = userEmployeeService.updateUser(userEmployeeDTO, id);
    return new ResponseEntity<>(updatedFields, HttpStatus.CREATED);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  public ResponseEntity<Map<String, Object>> deleteUserEmployee(
      @RequestHeader("Authorization")
      String token,
      @PathVariable String id) {

    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    userEmployeeService.deleteUser(Long.valueOf(id));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<UserEmployeeResponseDTO> getUserById(@RequestHeader("Authorization")
                                                             String token,
                                                             @PathVariable String id) {
    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    UserEmployeeResponseDTO user = userEmployeeService.getUserById(Long.valueOf(id));
    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}
