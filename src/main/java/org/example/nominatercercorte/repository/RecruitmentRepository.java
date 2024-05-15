package org.example.nominatercercorte.repository;

import org.example.nominatercercorte.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<UserEntity, Long> {
}
