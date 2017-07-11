
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import org.xml.sax.SAXException;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author SRV003
 */
public class SRVApsOd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SAXException, IOException {
        Properties prop = new Properties();

        //write property file
        try {
            //set the properties value
            //prop.setProperty("name", "nitesh");
           // prop.setProperty("mid", "kumar");
            //prop.setProperty("last", "sharma");

            //save properties to project root folder
            //prop.store(new FileOutputStream("SRVApsOd.properties"), null);
            ResourceBundle genRSB = ResourceBundle.getBundle("SRVApsOd_en_IN");
            String pnFileMessage = genRSB.getString("PN_FILE_NOT_FOUND");
            System.out.println("pnFileMessage >> "+pnFileMessage);
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
