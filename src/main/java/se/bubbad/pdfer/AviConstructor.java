package se.bubbad.pdfer;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;


public class AviConstructor
{
    private static String confFile      = "avier.conf";
    private static String blanketFile   = "template.pdf";
    private static String outputFile    = "ifylld.pdf";
    private static String announcesFile = "avier.txt";

    private static int paymentsPerPage = 3;

    public static void main( String[] args ) throws IOException, DocumentException {

        PaymentReader announceReader = new PaymentReader(confFile);
        ArrayList<Payment> announces = announceReader.readPaymentAnnounces(announcesFile);

        PdfHandler pdfHandler = new PdfHandler(blanketFile, outputFile);


        for (int i = 0; i < announces.size(); i++) {
            Payment payment = announces.get(i);

            int paymentIndex = (i % paymentsPerPage) + 1;

            pdfHandler.manipulatePdf("Rent" + paymentIndex, payment.getPayment());
            pdfHandler.manipulatePdf("ReceiverName", payment.getPaymentReceiverName());
            pdfHandler.manipulatePdf("ReceiverAccount", payment.getPaymentReceiverAccount());

        }


        pdfHandler.close();
    }
}