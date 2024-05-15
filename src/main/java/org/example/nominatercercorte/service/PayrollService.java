package org.example.nominatercercorte.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.example.nominatercercorte.exception.NotFoundException;
import org.example.nominatercercorte.model.Contract;
import org.example.nominatercercorte.model.PayRollPayment;
import org.example.nominatercercorte.model.UserEntity;
import org.example.nominatercercorte.repository.IUserEmployeeRepository;
import org.example.nominatercercorte.repository.PayRollPaymentRepository;
import org.example.nominatercercorte.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;

@Service
public class PayrollService {

  private final EmailService emailService;
  private final PayRollPaymentRepository payRollPaymentRepository;
  private final IUserEmployeeRepository userEmployeeRepository;
  private final RecruitmentRepository recruitmentRepository;

  public PayrollService(EmailService emailService, PayRollPaymentRepository payRollPaymentRepository, IUserEmployeeRepository userEmployeeRepository, RecruitmentRepository recruitmentRepository) {
    this.emailService = emailService;
    this.payRollPaymentRepository = payRollPaymentRepository;
    this.userEmployeeRepository = userEmployeeRepository;
    this.recruitmentRepository = recruitmentRepository;
  }

  public void executePayroll() {
    // Get all employees
    List<UserEntity> employees = userEmployeeRepository.findAll();

    for (UserEntity employee : employees) {
      PayRollPayment payment = calculatePayroll(employee);
      assert payment != null;
      payRollPaymentRepository.save(payment);

      // Send email notification to the employee
      String subject = "Payment Notification";
      String text = "Dear " + employee.getFirstName() + ",\n\nYour payroll payment has been processed."
          + " Please find the payment slip " +
          "attached.\n\nBest regards,\nYour Company";
      emailService.sendPayrollNotification(employee.getEmail(), subject, text);
    }
  }

  private PayRollPayment calculatePayroll(UserEntity employee) {
    Contract contract = recruitmentRepository.findById(employee.getId())
        .orElseThrow(() -> new NotFoundException("Contract not found for user id: " + employee.getId()));

    return PayRollPayment.builder()
        .user(employee)
        .amountPaid(contract.getBaseSalary())
        .paymentPeriod("Monthly")
        .paymentStatus("Paid")
        .paymentDate(Date.from(new Date().toInstant()))
        .build();
  }
}
