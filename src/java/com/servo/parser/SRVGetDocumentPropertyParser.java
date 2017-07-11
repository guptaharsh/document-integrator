

package com.servo.parser;



import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class SRVGetDocumentPropertyParser extends DefaultHandler{
    
    
    private String tagValue = null;   
    //HashMap<String,String> dataMap = null;
    HashMap<String,String> dataMap = new HashMap<String, String>();
    private String strDataDefName = null;
    private String strIndexName = null;
    private String strIndexValue = null;
    
    /**--------------------------------------------------------------------------
     *    Function Name             : startElement
     *    Author                    : 
     *    Date written (DD/MM/YYYY) : 12th Feb 2013
     *    Input Parameters          : String namespaceURI, String localName, String qName, Attributes atts
     *    Return Values             : None
     *    Description               : Receive notification of the start of an XML element.
     * --------------------------------------------------------------------------*/
    
    
    
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {        
       tagValue = new String();
       if (qName.equalsIgnoreCase("Field")) {
            
            //strDataDefName = "";
            strIndexName = "";
            strIndexValue = "";
            
       }       
       else if (qName.equalsIgnoreCase("DataDefName")) {
            
            strDataDefName = "";
//            strIndexName = "";
//            strIndexValue = "";
            
       }       
    }

    /**--------------------------------------------------------------------------
     *    Function Name             : endElement
     *    Author                    : 
     *    Date written (DD/MM/YYYY) : 12th Feb 2013
     *    Input Parameters          : String namespaceURI, String localName, String qName
     *    Return Values             : None
     *    Description               : Receive notification of the end of an element.
     * --------------------------------------------------------------------------*/
    @Override
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            //System.out.println(qName + " " +tagValue);
            
            
            if (qName.equalsIgnoreCase("IndexName")) {
                 strIndexName = tagValue;
                 //dataMap.put("DocuemntName",strDocumentName , strDocumentIndex + "#" +strDataDefName);
            }
            else if (qName.equalsIgnoreCase("IndexValue")) {
                 strIndexValue = tagValue;
            }
            else if (qName.equalsIgnoreCase("DataDefName")) {
                 strDataDefName = tagValue;
            }
            
            else if (qName.equalsIgnoreCase("Field")) {
                 dataMap.put(strIndexName , strIndexValue + "$" +strDataDefName );
            }
            
	}

    /**--------------------------------------------------------------------------
     *    Function Name             : characters
     *    Author                    : 
     *    Date written (DD/MM/YYYY) : 12th Feb 2013
     *    Input Parameters          : char buffer[], int offset, int length
     *    Return Values             : None
     *    Description               : Receive notification of character data inside an element.
     * --------------------------------------------------------------------------*/
    @Override
    public void characters(char buffer[], int offset, int length) throws SAXException {
        tagValue = new String(buffer, offset, length);
       // tempTagValue.append(buffer, offset, length);
    }

    
    /**--------------------------------------------------------------------------
     *    Function Name             : getXMLMap
     *    Author                    : 
     *    Date written (DD/MM/YYYY) : 12th Feb 2013
     *    Input Parameters          : None
     *    Return Values             : SRVProcess Object
     *    Description               : Return SRVProcess object populated after
     *                                reading GetProcessInfo Output XML.
     * --------------------------------------------------------------------------*/
    public HashMap getDataMap() {
        return dataMap;
    }
   }
