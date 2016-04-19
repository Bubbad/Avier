package se.bubbad.pdfer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Class responsible for loading the payments file.
 *
 * @author Johan Nilsson Hansen
 */

public class PaymentReader {

    private Config config;
    private String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public PaymentReader(Config config) {
        this.config = config;
    }

    /**
     * Reads all the payments in the {@code announceFile}.
     * @param announceFile The file containing all the payments
     * @param isPlusgiro True if the payment should be a plusgiro, false if its a bankgiro
     * @return A list of payments, either plus- or bankgiros.
     * @throws IOException
     */
    public ArrayList<Payment> readPaymentAnnounces(String announceFile, boolean isPlusgiro) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(announceFile));
        ArrayList<Payment> payments = new ArrayList<Payment>();

        String nextLine = reader.readLine();
        while(nextLine != null) {
            if(nextLine.startsWith("#") || nextLine.split(" ").length < 2) { // Remove comments and empty lines
                nextLine = reader.readLine();
                continue;
            }

            int paymentStartIndex = nextLine.lastIndexOf(" ");
            int nameStartIndex = nextLine.indexOf(" ");
            String type = nextLine.substring(0, nameStartIndex); //First word is assumed to be the type
            String name = nextLine.substring(nameStartIndex, paymentStartIndex); //Everything in between is assumed to be the name
            String payment = nextLine.substring(paymentStartIndex); //Last word is assumed to be the pay

            Payment announce;
            if(type.equals("pg") && isPlusgiro) {
                announce = new Payment(name, payment, config.receiverName, config.receiverPlusgiro, date);
                payments.add(announce);
            } else if (type.equals("bg") && !isPlusgiro){
                announce = new Payment(name, payment, config.receiverName, config.receiverBankgiro, date);
                payments.add(announce);
            }

            nextLine = reader.readLine();
        }

        return payments;
    }
}
