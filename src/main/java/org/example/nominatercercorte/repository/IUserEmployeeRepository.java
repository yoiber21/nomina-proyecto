package org.example.nominatercercorte.repository;

import org.example.nominatercercorte.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserEmployeeRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findUserEntityById(Long id);

  UserEntity findUserEntityByUsername(String username);
}
