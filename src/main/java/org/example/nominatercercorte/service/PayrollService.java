package org.example.nominatercercorte.service;

import com.itextpdf.text.DocumentException;
import java.io.File;
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
  private final PdfService pdfService;

  public PayrollService(EmailService emailService,
                        PayRollPaymentRepository payRollPaymentRepository,
                        IUserEmployeeRepository userEmployeeRepository,
                        RecruitmentRepository recruitmentRepository,
                        PdfService pdfService) {
    this.emailService = emailService;
    this.payRollPaymentRepository = payRollPaymentRepository;
    this.userEmployeeRepository = userEmployeeRepository;
    this.recruitmentRepository = recruitmentRepository;
    this.pdfService = pdfService;
  }

  public void executePayroll() throws DocumentException, IOException {
    // Get all employees
    List<UserEntity> employees = userEmployeeRepository.findAll();

    for (UserEntity employee : employees) {
      PayRollPayment payment = calculatePayroll(employee);
      assert payment != null;
      payRollPaymentRepository.save(payment);

      // Generate PDF
      String pdfPath = "Payroll_" + employee.getId() + ".pdf";
      pdfService.generatePayrollPdf(payment);

      // Send email notification to the employee
      String subject = "Payment Notification";
      String text = "Dear " + employee.getFirstName() + ",\n\nYour payroll payment has been processed."
          + " Please find the payment slip " +
          "attached.\n\nBest regards,\nYour Company";
      emailService.sendPayrollNotification(employee.getEmail(), subject, text, pdfPath);

      File pdfFile = new File(pdfPath);
      if (pdfFile.delete()) {
        System.out.println("Deleted the file: " + pdfFile.getName());
      } else {
        System.out.println("Failed to delete the file.");
      }
    }
  }

  private PayRollPayment calculatePayroll(UserEntity employee) {
    Contract contract = recruitmentRepository.findContractByUserId(employee.getId());
    if (contract == null) {
      throw new NotFoundException("Contract not found for user id: " + employee.getId());
    }

    return PayRollPayment.builder()
        .user(employee)
        .amountPaid(contract.getBaseSalary())
        .paymentPeriod("Monthly")
        .paymentStatus("Paid")
        .paymentDate(Date.from(new Date().toInstant()))
        .build();
  }
}
