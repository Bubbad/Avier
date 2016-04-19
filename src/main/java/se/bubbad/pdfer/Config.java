package se.bubbad.pdfer;

/**
 * Created by Johan on 2016-04-19.
 */
public class Config {

    public String receiverName;
    public String receiverPlusgiro;
    public String receiverBankgiro;

    public Config(String receiverPlusgiro, String receiverBankgiro, String receiverName) {
        this.receiverPlusgiro = receiverPlusgiro;
        this.receiverBankgiro = receiverBankgiro;
        this.receiverName = receiverName;
    }

}
