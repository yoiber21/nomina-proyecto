package org.example.nominatercercorte.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEmployeeResponseDTO {
  private String username;
  private String name;
  private String lastName;
  private String email;
  private String phone;
  private String address;
  private String rol;
}
