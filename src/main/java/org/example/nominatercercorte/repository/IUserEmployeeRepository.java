package org.example.nominatercercorte.repository;

import java.util.List;
import org.example.nominatercercorte.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserEmployeeRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findUserEntityById(Long id);

  UserEntity findUserEntityByUsername(String username);

  List<UserEntity> findAll();
}
