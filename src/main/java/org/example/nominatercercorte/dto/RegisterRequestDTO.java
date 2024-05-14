package org.example.nominatercercorte.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

  @NotBlank(message = "Username cannot be empty")
  @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
  private String username;

  @NotBlank(message = "Password cannot be empty")
  @Size(min = 6, max = 60, message = "Password must be between 6 and 60 characters")
  private String password;

  @NotBlank(message = "First name cannot be empty")
  @JsonProperty("first_name")
  private String firstName;

  @NotBlank(message = "Last name cannot be empty")
  @JsonProperty("last_name")
  private String lastName;

  @NotBlank(message = "Email cannot be empty")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Phone cannot be empty")
  private String phone;

  @NotBlank(message = "Address cannot be empty")
  private String address;
  private Date createdAt;
  private Date updatedAt;
}
