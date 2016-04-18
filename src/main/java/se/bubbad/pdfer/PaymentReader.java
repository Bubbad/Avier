package se.bubbad.pdfer;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Johan on 2016-04-18.
 */
public class PaymentReader {

    private String receiverName;
    private String receiverAccount;
    private String message = "Hyra ";
    private String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public PaymentReader(String confFileName) {
        readConfFile(confFileName);
    }

    private void readConfFile(String confFileName) {
        try {
            InputStream in = new FileInputStream(confFileName);
            Properties prop = new Properties();
            prop.load(in);

            receiverName = prop.getProperty("name");
            receiverAccount = prop.getProperty("account");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Payment> readPaymentAnnounces(String announceFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(announceFile));
        ArrayList<Payment> announces = new ArrayList<Payment>();

        String nextLine = reader.readLine();
        while(nextLine != null) {
            if(nextLine.startsWith("#") || nextLine.split(" ").length < 2) {
                nextLine = reader.readLine();
                continue;
            }

            int paymentStartIndex = nextLine.lastIndexOf(" ");
            String name = nextLine.substring(0, paymentStartIndex);
            String payment = nextLine.substring(paymentStartIndex);

            Payment announce = new Payment(name, payment, receiverName, receiverAccount, message, date);
            announces.add(announce);
            nextLine = reader.readLine();
        }

        return announces;
    }



}
