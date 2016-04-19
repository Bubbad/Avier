package se.bubbad.pdfer;

/**
 * Created by Johan on 2016-04-19.
 */
public class Config {

    public String receiverName;
    public String receiverAccount;
    public String printer;

    public Config(String printer, String receiverAccount, String receiverName) {
        this.printer = printer;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
    }

}
