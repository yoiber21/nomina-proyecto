package org.example.nominatercercorte.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.example.nominatercercorte.model.PayRollPayment;
import org.example.nominatercercorte.model.UserEntity;
import org.example.nominatercercorte.repository.IUserEmployeeRepository;
import org.example.nominatercercorte.repository.PayRollPaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PayrollService {

  private final EmailService emailService;
  private final PayRollPaymentRepository payRollPaymentRepository;
  private final IUserEmployeeRepository userEmployeeRepository;

  public PayrollService(EmailService emailService, PayRollPaymentRepository payRollPaymentRepository, IUserEmployeeRepository userEmployeeRepository) {
    this.emailService = emailService;
    this.payRollPaymentRepository = payRollPaymentRepository;
    this.userEmployeeRepository = userEmployeeRepository;
  }

  public void executePayroll() throws IOException {
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
    return PayRollPayment.builder()
        .user(employee)
        .amountPaid(1000.0)
        .paymentPeriod("Monthly")
        .paymentStatus("Paid")
        .paymentDate(Date.from(new Date().toInstant()))
        .build();
  }
}
