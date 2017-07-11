/*
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


@WebListener()
public class SRVDocumentPath implements ServletContextListener {

    String DocumentFolder;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        try {

            SAXParserFactory parserFact = SAXParserFactory.newInstance();
            SAXParser parser = parserFact.newSAXParser();
            
            DefaultHandler dHandler = new DefaultHandler() {

                
                boolean documentFolder;

                public void startElement(String uri, String localName,
                        String element_name, Attributes attributes) throws SAXException {
                    
                    if (element_name.equals("documentfolder")) {
                        documentFolder = true;
                    }
                    

                }

                public void characters(char[] ch, int start, int len)
                        throws SAXException {
                    String str = new String(ch, start, len);
                    
                    if (documentFolder) {
                        DocumentFolder = str;
                        documentFolder = false;
                    }

                }
            };

            parser.parse("E:\\ApsOd\\src\\java\\Url.xml", dHandler);
            
//  CODE for splitting on delimeter ('slashes')
            
            String documentPath = DocumentFolder;
            String[] arrObjectIndex;
 
  
  String delimiter = "\\";
  
  arrObjectIndex = documentPath.split(delimiter);
  // print substrings
  for(int i =0; i < arrObjectIndex.length ; i++)
    System.out.println(arrObjectIndex[i]);

            
//  CODE ENDS here            
            
            
            
            
            
            sce.getServletContext().setInitParameter("DOCUMENT_FOLDER", DocumentFolder);
            //System.out.println("pachan kun" + sce.getServletContext().getInitParameter("APS-URL")+ sce.getServletContext().getInitParameter("OD-WIDTH")+ sce.getServletContext().getInitParameter("APS-WIDTH")+ sce.getServletContext().getInitParameter("APS_WINDOW_DISPLAY"));
            System.out.println("DOCUMENT URI ----------- > "+sce.getServletContext().getInitParameter("DOCUMENT_FOLDER"));
        } catch (Exception e) {
            System.out.println("XML File hasn't any elements");
            e.printStackTrace();
        }




    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
*/