package se.bubbad.pdfer;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class AviConstructor
{
    private static String confFile      = "avier.conf";
    private static String blanketFile   = "template.pdf";
    private static String outputFile    = "ifylld.pdf";
    private static String announcesFile = "avier.txt";

    private static int paymentsPerPage = 3;

    public static void main( String[] args ) throws Exception {
        Config config = ConfigReader.readConfFile(confFile);

        PaymentReader announceReader = new PaymentReader(config);
        ArrayList<Payment> announces = announceReader.readPaymentAnnounces(announcesFile);

        PdfHandler pdfHandler = new PdfHandler(blanketFile, outputFile);


        for (int i = 0; i < announces.size(); i++) {
            Payment payment = announces.get(i);

            int paymentIndex = (i % paymentsPerPage) + 1;

            pdfHandler.manipulatePdf("Rent" + paymentIndex, payment.getPayment());
            pdfHandler.manipulatePdf("ReceiverName", payment.getPaymentReceiverName());
            pdfHandler.manipulatePdf("ReceiverAccount", payment.getPaymentReceiverAccount());

            if(((i + 1) % 3 ) == 0) {
                pdfHandler.close();

                printPdf(config);

                pdfHandler = new PdfHandler(blanketFile, outputFile);
            }
        }


        pdfHandler.close();

        if(announces.size() % paymentsPerPage != 0) {
            printPdf(config);
        }
    }

    private static void printPdf(Config config) throws PrintException, IOException {
        FileInputStream fis = new FileInputStream(outputFile);
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc pdfDoc = new SimpleDoc(fis, flavor, null);

        PrintService printService[]= PrintServiceLookup.lookupPrintServices(flavor, pras);
        int printerIndex = getPrinterIndex(printService, config.printer);

        if(printerIndex == -1) {
            System.out.println("Couldnt find printer.");
            System.exit(0);
        }

        DocPrintJob printJob = printService[printerIndex].createPrintJob();
        printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
        fis.close();
    }

    private static int getPrinterIndex(PrintService[] printService, String printer) {
        for (int i = 0; i < printService.length; i++) {
            if(printService[i].getName().contains(printer)) {
                return i;
            }
        }

        return -1;
    }
}