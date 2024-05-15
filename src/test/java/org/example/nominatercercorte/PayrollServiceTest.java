package org.example.nominatercercorte;
import org.example.nominatercercorte.service.PayrollService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PayrollServiceTest {

  @Autowired
  private PayrollService payrollService;

  @Test
  public void testExecutePayroll() throws Exception {
    payrollService.executePayroll();
  }
}
