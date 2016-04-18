package se.bubbad.pdfer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Johan on 2016-04-18.
 */
public class PdfHandler {

    private PdfReader reader;
    private PdfStamper stamper;
    private AcroFields fields;

    public PdfHandler(String src, String dest) {
        try {
            reader = new PdfReader(src);
            stamper = new PdfStamper(reader, new FileOutputStream(dest));
            fields = stamper.getAcroFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void manipulatePdf(String fieldName, String value) throws DocumentException, IOException {
        fields.setField(fieldName, value);
    }

    public void close() {
        try {
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
