package org.example.nominatercercorte.service;

import org.example.nominatercercorte.model.Contract;
import org.example.nominatercercorte.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecruitmentService {

  private final RecruitmentRepository contractRepository;

  public RecruitmentService(RecruitmentRepository contractRepository) {
    this.contractRepository = contractRepository;
  }

  public List<Contract> getAllContracts() {
    return contractRepository.findAll();
  }

  public Optional<Contract> getContractById(Long id) {
    return contractRepository.findById(id);
  }

  public Contract createContract(Contract contract) {
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
