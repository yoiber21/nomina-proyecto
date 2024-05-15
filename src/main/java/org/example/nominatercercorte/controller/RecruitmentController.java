package org.example.nominatercercorte.controller;

import org.example.nominatercercorte.jwt.JwtService;
import org.example.nominatercercorte.model.Contract;
import org.example.nominatercercorte.service.RecruitmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recruitment")
public class RecruitmentController {

  private final RecruitmentService RecruitmentService;
  private final JwtService jwtService;

  public RecruitmentController(RecruitmentService RecruitmentService, JwtService jwtService) {
    this.RecruitmentService = RecruitmentService;
    this.jwtService = jwtService;
  }

  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  @GetMapping
  public ResponseEntity<List<Contract>> getAllContracts(@RequestHeader("Authorization")
                                                        String token) {
    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    List<Contract> users = RecruitmentService.getAllContracts();
    return ResponseEntity.ok(users);
  }

  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<Contract> getcontractById(@PathVariable Long id,
                                                  @RequestHeader("Authorization")
                                                  String token) {
    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    return RecruitmentService.getContractById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  @PostMapping
  public ResponseEntity<Contract> createContract(@RequestBody Contract contract,
                                                 @RequestHeader("Authorization")
                                                 String token) {
    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    Contract createdContract = RecruitmentService.createContract(contract);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdContract);
  }

  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  @PutMapping("/{id}")
  public ResponseEntity<Contract> updateContract(@PathVariable Long id, @RequestBody Contract contract,
                                                 @RequestHeader("Authorization")
                                                 String token) {
    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    Contract updatedContract = RecruitmentService.updateContract(id, contract);
    return ResponseEntity.ok(updatedContract);
  }


  @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteContract(@PathVariable Long id,
                                             @RequestHeader("Authorization")
                                             String token) {
    String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
    jwtService.getUsernameFromToken(jwtToken);
    RecruitmentService.deleteContract(id);
    return ResponseEntity.noContent().build();
  }
}
