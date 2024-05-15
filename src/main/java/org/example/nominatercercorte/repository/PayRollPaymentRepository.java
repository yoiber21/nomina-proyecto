package org.example.nominatercercorte.repository;

import org.example.nominatercercorte.model.PayRollPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRollPaymentRepository extends JpaRepository<PayRollPayment, Long> {
    PayRollPayment findPayRollPaymentById(Long id);
}
