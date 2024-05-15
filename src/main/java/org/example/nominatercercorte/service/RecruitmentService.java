package org.example.nominatercercorte.service;

import org.example.nominatercercorte.model.UserEntity;
import org.example.nominatercercorte.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentService {

    private final RecruitmentRepository userRepository;

    public RecruitmentService(RecruitmentRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity user) {

        if (!userRepository.existsById(id)) {

        }
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
