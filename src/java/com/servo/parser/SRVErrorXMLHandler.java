

package com.servo.parser;



import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SRVErrorXMLHandler extends DefaultHandler{
    
    
    private String tagValue = null;   
    //HashMap<String,String> dataMap = null;
    HashMap<String,String> dataMap = new HashMap<String, String>();
    private String strStatus = null;
    private String strError = null;
    
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
       //if (qName.equalsIgnoreCase("NGOGetIDFromName_Output")) {
            strStatus = ""; 
            strError = "";
       //}       
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
            
            
            if (qName.equalsIgnoreCase("Status")) {
                 strStatus = tagValue;
            }
            else if (qName.equalsIgnoreCase("Error")) {
                 strError = tagValue;
            }
            
            else if (qName.equalsIgnoreCase("Field")) {
                 dataMap.put(strStatus , strStatus + "#" +strError);
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
