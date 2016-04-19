package se.bubbad.pdfer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Johan on 2016-04-18.
 */
public class PaymentReader {

    private Config config;
    private String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public PaymentReader(Config config) {
        this.config = config;
    }

    public ArrayList<Payment> readPaymentAnnounces(String announceFile, boolean isPlusgiro) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(announceFile));
        ArrayList<Payment> plusgiro = new ArrayList<Payment>();
        ArrayList<Payment> bankgiro = new ArrayList<Payment>();

        String nextLine = reader.readLine();
        while(nextLine != null) {
            if(nextLine.startsWith("#") || nextLine.split(" ").length < 2) {
                nextLine = reader.readLine();
                continue;
            }

            int paymentStartIndex = nextLine.lastIndexOf(" ");
            int nameStartIndex = nextLine.indexOf(" ");
            String type = nextLine.substring(0, nameStartIndex);
            String name = nextLine.substring(nameStartIndex, paymentStartIndex);
            String payment = nextLine.substring(paymentStartIndex);

            if(type.equals("pg") && isPlusgiro) {
                Payment announce = new Payment(name, payment, config.receiverName, config.receiverPlusgiro, date);
                plusgiro.add(announce);
            } else if (type.equals("bg") && !isPlusgiro){
                Payment announce = new Payment(name, payment, config.receiverName, config.receiverBankgiro, date);
                bankgiro.add(announce);
            }

            nextLine = reader.readLine();
        }

        return isPlusgiro ? plusgiro : bankgiro;
    }



}
