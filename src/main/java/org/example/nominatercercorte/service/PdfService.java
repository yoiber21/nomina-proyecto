package org.example.nominatercercorte.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import org.example.nominatercercorte.model.PayRollPayment;
import org.springframework.stereotype.Service;


@Service
public class PdfService {

  public void generatePayrollPdf(PayRollPayment payment) throws DocumentException, IOException {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("Payroll_" + payment.getUser().getId() + ".pdf"));
    document.open();

    Paragraph title = new Paragraph("Payroll Payment Details", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD));
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);

    PdfPTable table = new PdfPTable(2); // 2 columns.
    table.addCell("User");
    table.addCell(payment.getUser().getFirstName() + " " + payment.getUser().getLastName());
    table.addCell("Amount Paid");
    table.addCell(String.valueOf(payment.getAmountPaid()));
    table.addCell("Payment Period");
    table.addCell(payment.getPaymentPeriod());
    table.addCell("Payment Status");
    table.addCell(payment.getPaymentStatus());
    table.addCell("Payment Date");
    table.addCell(payment.getPaymentDate().toString());

    document.add(table);
    document.close();
  }

}


