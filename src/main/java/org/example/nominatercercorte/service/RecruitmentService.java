package org.example.nominatercercorte.service;

import org.example.nominatercercorte.exception.NotFoundException;
import org.example.nominatercercorte.model.Contract;
import org.example.nominatercercorte.model.UserEntity;
import org.example.nominatercercorte.repository.IUserEmployeeRepository;
import org.example.nominatercercorte.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentService {

  private final RecruitmentRepository contractRepository;
  private final IUserEmployeeRepository userEmployeeRepository;

  public RecruitmentService(RecruitmentRepository contractRepository, IUserEmployeeRepository userEmployeeRepository) {
    this.contractRepository = contractRepository;
    this.userEmployeeRepository = userEmployeeRepository;
  }

  public List<Contract> getAllContracts() {
    return contractRepository.findAll();
  }

  public Optional<Contract> getContractById(Long id) {
    return contractRepository.findById(id);
  }

  public Contract createContract(Contract contract) {
    UserEntity user = userEmployeeRepository.findById(contract.getUser().getId())
        .orElseThrow(() -> new NotFoundException("User not found with id: " + contract.getUser().getId()));
    contract.setUser(user);
    return contractRepository.save(contract);
  }

  public Contract updateContract(Long id, Contract contract) {

    contractRepository.existsById(id);
    contract.setId(id);
    return contractRepository.save(contract);
  }

  public void deleteContract(Long id) {
    contractRepository.deleteById(id);
  }
}
