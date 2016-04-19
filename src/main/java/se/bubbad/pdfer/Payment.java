package se.bubbad.pdfer;

/**
 * Created by Johan on 2016-04-18.
 */
public class Payment {
    private String name;
    private String payment;
    private String paymentReceiverName;
    private String paymentReceiverAccount;
    private String message;
    private String date;

    private final String spaceBetweenDigits = "  ";

    public Payment(String name, String payment, String paymentReceiverName, String paymentReceiverAccount, String message, String date) {
        this.name = name;
        this.payment = payment;
        this.paymentReceiverName = paymentReceiverName;
        this.paymentReceiverAccount = paymentReceiverAccount;
        this.message = message;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }


    public String getPaymentCents() {
        return getNumberString(getCentsFromPayment());
    }

    private String getCentsFromPayment() {
        String[] cents = payment.split("\\.");
        if(cents.length < 2) {
            return "00";
        } else {
            return cents[1];
        }
    }

    public String getPaymentKronor() {
        return getNumberString(getWholeKronorFromPayment());
    }

    public String getPayment() {
        return getNumberString(payment);
    }

    private String getWholeKronorFromPayment() {
        return payment.split("\\.")[0];
    }

    public String getPaymentReceiverName() {
        return paymentReceiverName;
    }

    public String getPaymentReceiverAccount() {
        return getNumberString(paymentReceiverAccount);
    }


    public String getMessage() {
        return message;
    }

    private String getNumberString(String numbers) {
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
