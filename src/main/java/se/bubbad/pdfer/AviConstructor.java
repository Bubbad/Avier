package se.bubbad.pdfer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Main class for the project. Loads a GUI with the option to
 * generate a PDF of all the payments in a certain payments file.
 */
public class AviConstructor
{
    private static String confFile      = "config.txt";
    private static String plusgiroForm   = "templatepg.pdf";
    private static String plusgiroTempFile   = "tmp_pg.pdf";

    private static String bankgiroForm   = "templatebg.pdf";
    private static String bankgiroTempFile   = "tmp_bg.pdf";
    private static String announcesFile = "avier.txt";

    private static String outputFile = "Skriv-ut-mig.pdf";

    private static int paymentsPerPage = 3;

    private static JFrame frame;


    public static void main( String[] args ) throws Exception {
        createFrame();
    }

    /**
     * Creates the Frame
     */
    public static void createFrame() {
        frame = new JFrame("Avier");
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        JButton button = new JButton();
        button.setText("Create pdf");

        final JTextArea textArea = new JTextArea();
        textArea.setSize(250,250);
        textArea.append("Click create PDF button to generate a new pdf\n");
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));

        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    generatePdf();
                    textArea.append("Complete!\n");
                } catch (IOException e1) {
                    textArea.append("Error with a file. Please close " + outputFile + " if its open.\n");

                    e1.printStackTrace();
                } catch (DocumentException e1) {
                    textArea.append("Something went wrong with a PDF\n");
                    e1.printStackTrace();
                }
            }
        });

        panel.add(textArea);
        panel.add(button);

        panel.updateUI();

    }

    /**
     * Creates PDFs and merges them into one final, which contains
     * all plus- and bankgiro payments.
     * @throws IOException
     * @throws DocumentException
     */
    public static void generatePdf() throws IOException, DocumentException {
        Config config = ConfigReader.readConfFile(confFile);

        PaymentReader announceReader = new PaymentReader(config);
        ArrayList<Payment> plusgiros = announceReader.readPaymentAnnounces(announcesFile, true);
        ArrayList<Payment> bankgiros = announceReader.readPaymentAnnounces(announcesFile, false);

        ArrayList<File>  pdfs = new ArrayList<>();

        addPlusgiros(plusgiros, pdfs);
        addBankgiros(bankgiros, pdfs);

        concatenatePdfs(pdfs, new File(outputFile));
    }

    /**
     * Adds {@code bankgiros} as bankgiros.
     * @param bankgiros A list of payments to be sent as bankgiro.
     * @param pdfs The current list of PDFs created
     * @throws DocumentException
     * @throws IOException
     */
    private static void addBankgiros(ArrayList<Payment> bankgiros, ArrayList<File> pdfs) throws DocumentException, IOException {
        PdfHandler pdfHandler = new PdfHandler(bankgiroForm, bankgiroTempFile);

        for (int i = 0; i < bankgiros.size(); i++) {
            Payment payment = bankgiros.get(i);
            int paymentIndex = (i % paymentsPerPage) + 1;

            pdfHandler.manipulatePdf("Month", getMonth());
            pdfHandler.manipulatePdf("Rent" + paymentIndex + "Square", payment.getPaymentWithSquares("  "));
            pdfHandler.manipulatePdf("Rent" + paymentIndex, payment.getPayment());
            pdfHandler.manipulatePdf("ReceiverName", payment.getPaymentReceiverName());
            pdfHandler.manipulatePdf("ReceiverAccount", payment.getPaymentReceiverAccount("  "));

            if(((i + 1) % paymentsPerPage ) == 0) {
                pdfHandler.close();

                pdfs.add(new File(bankgiroTempFile));

                if(i != bankgiros.size() - 1) {
                    bankgiroTempFile = i + bankgiroTempFile;
                    pdfHandler = new PdfHandler(bankgiroForm, bankgiroTempFile);
                }
            }
        }
        pdfHandler.close();

        if(bankgiros.size() % paymentsPerPage != 0) {
            pdfs.add(new File(bankgiroTempFile));
        }
    }

    /**
     * Adds {@code plusgiros} as bankgiros.
     * @param plusgiros A list of payments to be sent as bankgiro.
     * @param pdfs The current list of PDFs created
     * @throws DocumentException
     * @throws IOException
     */
    private static void addPlusgiros(ArrayList<Payment> plusgiros, ArrayList<File> pdfs) throws DocumentException, IOException {
        PdfHandler pdfHandler = new PdfHandler(plusgiroForm, plusgiroTempFile);

        for (int i = 0; i < plusgiros.size(); i++) {
            Payment payment = plusgiros.get(i);
            int paymentIndex = (i % paymentsPerPage) + 1;

            pdfHandler.manipulatePdf("Month", getMonth());
            pdfHandler.manipulatePdf("Rent" + paymentIndex, payment.getPaymentWithSquares("    "));
            pdfHandler.manipulatePdf("ReceiverName", payment.getPaymentReceiverName());
            pdfHandler.manipulatePdf("ReceiverAccount", payment.getPaymentReceiverAccount(""));

            if(((i + 1) % paymentsPerPage ) == 0) {
                pdfHandler.close();

                pdfs.add(new File(plusgiroTempFile));

                if(i != plusgiros.size() - 1) {
                    plusgiroTempFile = i + plusgiroTempFile;
                    pdfHandler = new PdfHandler(plusgiroForm, plusgiroTempFile);
                }
            }
        }
        pdfHandler.close();

        if(plusgiros.size() % paymentsPerPage != 0) {
            pdfs.add(new File(plusgiroTempFile));
        }
    }

    /**
     * Merges all pds in {@code listOfPdfFiles} into one and saves it in to {@code outputFile}
     * After the merge, all pdfs in {@code listOfPdfFiles} will be removed.
     * @param listOfPdfFiles List of all created pfs
     * @param outputFile The final pdf
     * @throws IOException
     * @throws DocumentException
     */
    public static void concatenatePdfs(List<File> listOfPdfFiles, File outputFile) throws IOException, DocumentException {
        Document document = new Document();
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        PdfCopy copy = new PdfSmartCopy(document, outputStream);
        document.open();
        for (File inFile : listOfPdfFiles) {
            PdfReader reader = new PdfReader(inFile.getAbsolutePath());
            copy.addDocument(reader);
            reader.close();
        }
        document.close();

        for (File inFile : listOfPdfFiles) {
            inFile.delete();
        }
    }

    /**
     * Returns the next month as a String in Swedish
     * @return
     */
    public static String getMonth() {
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        int month = cal.get(Calendar.MONTH);

        String[] monthNames = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};
        return monthNames[month];
    }

}