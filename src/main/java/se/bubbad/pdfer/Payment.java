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

    private final String spaceBetweenDigits = "   ";

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

    private String getWholeKronorFromPayment() {
        return payment.split("\\.")[0];
    }

    public String getPaymentReceiverName() {
        return paymentReceiverName;
    }

    public String getPaymentReceiverAccount() {
        return paymentReceiverAccount;
    }


    public String getMessage() {
        return message;
    }

    private String getNumberString(String numbers) {
        String reversedNumbers = new StringBuilder(numbers).reverse().toString();
        String[] individualNumbers = reversedNumbers.split("");

        String kronorReversed = "";
        for (int i = 1; i < individualNumbers.length; i++) {
            String number = individualNumbers[i];
            String spaces = spaceBetweenDigits;

            if(i == 4 || i == 7) {
                spaces += "   ";
            }
            kronorReversed += spaces + number;
        }

        return new StringBuilder(kronorReversed).reverse().toString();
    }
}
