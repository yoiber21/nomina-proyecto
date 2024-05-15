package org.example.nominatercercorte.repository;

import org.apache.catalina.User;
import org.example.nominatercercorte.model.Contract;
import org.example.nominatercercorte.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Contract, Long> {
  Contract findContractByUserId(Long user_id);
}
