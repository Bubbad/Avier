package se.bubbad.pdfer;

/**
 * Created by Johan on 2016-04-18.
 */
public class Payment {
    private String name;
    private String payment;
    private String paymentReceiverName;
    private String paymentReceiverAccount;
    private String date;

    public Payment(String name, String payment, String paymentReceiverName, String paymentReceiverAccount, String date) {
        this.name = name;
        this.payment = payment;
        this.paymentReceiverName = paymentReceiverName;
        this.paymentReceiverAccount = paymentReceiverAccount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getPayment() {
        return payment;
    }

    public String getPaymentWithSquares(String spaceBetweenDigits) {
        return getNumberString(payment, spaceBetweenDigits);
    }

    public String getPaymentReceiverName() {
        return paymentReceiverName;
    }

    public String getPaymentReceiverAccount(String spaceBetweenDigits ) {
        return getNumberString(paymentReceiverAccount, spaceBetweenDigits);
    }


    private String getNumberString(String numbers, String spaceBetweenDigits ) {
        String[] individualNumbers = numbers.split("");
        String kronor = "";

        for (int i = 1; i < individualNumbers.length; i++) {
            String number = individualNumbers[i];
            if(number.equals(".")) {
                continue;
            }

            String spaces = spaceBetweenDigits;
            kronor += spaces + number;
        }

        return kronor;
    }
}
