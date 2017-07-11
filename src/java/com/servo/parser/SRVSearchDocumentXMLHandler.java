

package com.servo.parser;



import java.util.HashMap;
import java.util.LinkedHashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class SRVSearchDocumentXMLHandler extends DefaultHandler{
    
    
    private String tagValue = null;   
    //HashMap<String,String> dataMap = null;
    LinkedHashMap<String,String> dataMap = new LinkedHashMap<String, String>();
    private String strOwnerName = null;
    private String strDocumentName = null;
    private String strDocumentType = null;
    private String strDocumentIndex = null;
    private String strParentFolderIndex = null;
    private String strNoOfPages = null;
    private String strDocumentSize = null;
    private String strCreationDateTime = null;//RevisedDateTime
    private String strDataDefName = null;
    private String strIsIndex = null;
    private String strCreatedByAppName = null;
    private String strComment = null;
    private boolean docParsing = false;
    private boolean bNoOfPages = false;
    
    private String strNoOfRecordsFetched = null;
    private String strTotalNoOfRecords = null;
    
    private String strPreviousFolderIndex = null;
    private String strFiledDateTime = null;
    
    
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
       if (qName.equalsIgnoreCase("Document")) {
            strDocumentName = ""; 
            strDocumentIndex = "";
            strDataDefName = "";
            strDocumentType = "";
            strParentFolderIndex = "";
            strNoOfPages = "";
            strDocumentSize = "";
            strOwnerName = "";
            docParsing = true;
            bNoOfPages = true;
            
            strPreviousFolderIndex = "";
            strFiledDateTime = "";
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
            
            if (docParsing)
            {
                if (qName.equalsIgnoreCase("DocumentName")) {
                    strDocumentName = tagValue;
                    //dataMap.put("DocuemntName",strDocumentName , strDocumentIndex + "#" +strDataDefName);
                }
                else if (qName.equalsIgnoreCase("DocumentIndex")) {
                    strDocumentIndex = tagValue;
                }
                else if (qName.equalsIgnoreCase("DocumentType")) {
                    strDocumentType = tagValue;
                }
                else if (qName.equalsIgnoreCase("DataDefName")) {
                    strDataDefName = tagValue;
                }
                else if (qName.equalsIgnoreCase("ISIndex")) {
                    strIsIndex = tagValue;
                }
                else if (qName.equalsIgnoreCase("Comment")) {
                    strComment = tagValue;
                }
                else if (qName.equalsIgnoreCase("CreatedByAppName")) {
                    strCreatedByAppName = tagValue;
                }
                else if (qName.equalsIgnoreCase("ParentFolderIndex")) {
                    strParentFolderIndex = tagValue;
                }
                else if (qName.equalsIgnoreCase("NoOfPages") && bNoOfPages) {
                    System.out.println("In noofpages");
                    strNoOfPages = tagValue;
                    System.out.println("strNoOfPages>>>"+strNoOfPages);
                    bNoOfPages = false;
                }
                else if (qName.equalsIgnoreCase("DocumentSize")) {
                    strDocumentSize = tagValue;
                }
                //else if (qName.equalsIgnoreCase("CreationDateTime")) {
                else if (qName.equalsIgnoreCase("RevisedDateTime")) {
                    strCreationDateTime = tagValue;
                }
                else if (qName.equalsIgnoreCase("Owner")) {
                    strOwnerName = tagValue;
                }
                else if (qName.equalsIgnoreCase("FiledDateTime")) {
                    strFiledDateTime = tagValue;
                }
                else if (qName.equalsIgnoreCase("Document")) {
                    System.out.println("[SRVSearchDocumentXMLHandler] Document tag --- > "+
                            strDocumentName +" $ "+ strDocumentIndex  + " $ " +strIsIndex + "$" +strParentFolderIndex
                            + "$" +strCreationDateTime );
                    
                    dataMap.put(strDocumentName+ "." + strCreatedByAppName , strDocumentIndex + "$" +strDataDefName + "$" +strIsIndex + "$" +strCreatedByAppName + "$" +strComment + "$" +strDocumentType + "$" +strParentFolderIndex
                            + "$" +strNoOfPages + "$" +strDocumentSize + "$" +strCreationDateTime + "$" +strOwnerName);
                    docParsing = false;
                    
                }
                
            }
            if(qName.equalsIgnoreCase("NoOfRecordsFetched"))
            {
                strNoOfRecordsFetched = tagValue;
                dataMap.put("NoOfRecordsFetched" , strNoOfRecordsFetched );
            }
            if(qName.equalsIgnoreCase("TotalNoOfRecords"))
            {
                strTotalNoOfRecords = tagValue;
                dataMap.put("TotalNoOfRecords" , strTotalNoOfRecords );
            }
            if(qName.equalsIgnoreCase("NGOGetDocumentListExt_Output"))
            {
                strPreviousFolderIndex = strDocumentIndex;
                dataMap.put("PreviousFolderIndex" , strPreviousFolderIndex );
                dataMap.put("FiledDateTime" , strFiledDateTime );
                System.out.println("Last folder index is --------- > "+strPreviousFolderIndex);
                System.out.println("Last Filed Date Time is --------- > "+strFiledDateTime);
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
