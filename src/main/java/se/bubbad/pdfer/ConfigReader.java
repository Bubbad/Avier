package se.bubbad.pdfer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Johan on 2016-04-19.
 */
public class ConfigReader {

    public static Config readConfFile(String confFileName) {
        try {
            InputStream in = new FileInputStream(confFileName);
            Properties prop = new Properties();
            prop.load(in);


            String receiverName = prop.getProperty("name");
            String receiverPlusgiro = prop.getProperty("plusgiro");
            String receiverBankgiro = prop.getProperty("bankgiro");
            return new Config(receiverPlusgiro, receiverBankgiro, receiverName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
