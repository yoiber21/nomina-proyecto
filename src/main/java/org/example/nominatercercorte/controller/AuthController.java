package org.example.nominatercercorte.controller;

import jakarta.validation.Valid;
import org.example.nominatercercorte.dto.LoginRequestDTO;
import org.example.nominatercercorte.dto.RegisterRequestDTO;
import org.example.nominatercercorte.service.AuthServiceImpl;
import org.example.nominatercercorte.service.IAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  IAuthService service;

  public AuthController(AuthServiceImpl service){
    this.service = service;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDto){
    return ResponseEntity.ok(service.login(loginRequestDto));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDto){
    return ResponseEntity.ok(service.register(registerRequestDto));
  }
}
