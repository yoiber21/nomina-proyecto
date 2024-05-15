package org.example.nominatercercorte.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UserEmployeeResponseDTO {

  @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must be alphanumeric")
  private String username;

  @NotBlank(message = "Password cannot be empty")
  @Size(min = 6, max = 60, message = "Password must be between 6 and 60 characters")
  private String password;

  @Pattern(regexp = "^[a-zA-Z]*$", message = "Name must only contain letters")
  @JsonProperty("first_name")
  private String name;

  @Pattern(regexp = "^[a-zA-Z]*$", message = "Last Name must only contain letters")
  @JsonProperty("last_name")
  private String lastName;

  @Email(message = "Email should be valid")
  private String email;

  @Pattern(regexp = "^\\+57\\d{10}$", message = "Phone number must be a valid Colombian number")
  private String phone;
  private String address;

  @Pattern(regexp = "^[a-zA-Z]*$", message = "Rol must only contain letters")
  private String rol;

  @JsonProperty("created_at")
  private Date createdAt;

  @JsonProperty("updated_at")
  private Date updatedAt;
}
