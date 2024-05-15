package org.example.nominatercercorte.config;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import org.example.nominatercercorte.service.PayrollService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PayrollScheduler {

  private final PayrollService payrollService;

  public PayrollScheduler(PayrollService payrollService) {
    this.payrollService = payrollService;
  }

  @Scheduled(cron = "0 0 18 * * ?", zone = "America/Bogota")
  public void executePayroll() throws DocumentException, IOException {
    payrollService.executePayroll();
  }
}