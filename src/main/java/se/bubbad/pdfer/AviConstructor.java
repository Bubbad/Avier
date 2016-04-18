package se.bubbad.pdfer;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.ArrayList;


public class AviConstructor
{
    private static String confFile      = "avier.conf";
    private static String blanketFile   = "blankett.pdf";
    private static String outputFile    = "ifylld.pdf";
    private static String announcesFile = "avier.txt";

    public static void main( String[] args ) throws IOException, DocumentException {

        PaymentReader announceReader = new PaymentReader(confFile);
        ArrayList<Payment> announces = announceReader.readPaymentAnnounces(announcesFile);

        PdfHandler pdfHandler = new PdfHandler(blanketFile, outputFile);

        Payment announce = announces.get(0);

        pdfHandler.manipulatePdf("Message", announce.getMessage() + " " + announce.getName() + ". Skickat " + announce.getDate());
        pdfHandler.manipulatePdf("Kronor", announce.getPaymentKronor());
        pdfHandler.manipulatePdf("Cents", announce.getPaymentCents());
        pdfHandler.manipulatePdf("ReceiverName", announce.getPaymentReceiverName());
        pdfHandler.manipulatePdf("ReceiverAccount", announce.getPaymentReceiverAccount());

        pdfHandler.close();
    }
}