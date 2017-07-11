/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servo.dms;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;
import com.newgen.niplj.fileformat.Tif6;
import com.servo.session.SRVCallBroker;
import com.servo.util.SRVUtil;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class SRVDMSImpl implements SRVDMSInterface{
    
    public String addDocument(String strInputXml) throws Exception, JPISException
	{	
            System.out.println("[SRVDMSImpl] (addDocument) Ret XML --- > "+strInputXml);
            SRVUtil.printOut("[SRVDMSImpl] (addDocument) Ret XML --- > "+strInputXml);
            
                StringBuffer localInputXml = new StringBuffer(strInputXml);
                HashMap xmlMap = SRVUtil.getXMLMap(strInputXml);
                
		String strFileName = (String)xmlMap.get("FilePath");
                System.out.println("[SRVDMSImpl] (addDocument) FILE Path is =========== "+strFileName);
                SRVUtil.printOut("[SRVDMSImpl] (addDocument) FILE Path is =========== "+strFileName);
		File inputFile=new File(strFileName);
                System.out.println("[SRVDMSImpl] (addDocument) inputFile exists ? === "+inputFile.exists());
                SRVUtil.printOut("[SRVDMSImpl] (addDocument) inputFile exists ? === "+inputFile.exists());
		String strCabinetName=(String)xmlMap.get("CabinetName");

		JPISIsIndex IsIndex = new JPISIsIndex();
		char cDocumentType = 'N';
		int nDocumentSize = 0;
		int nNoOfPages = 1;
		String strOutputXml="";

		String strFoldVolId = (String)xmlMap.get("VolumeIndex");
		String fileExt = "";

		try
		{
			fileExt = (String)xmlMap.get("CreatedByAppName");
			if(fileExt.equals(""))
			{
                                System.out.println("[SRVDMSImpl] (addDocument) fileExt is 'blank'");
                                SRVUtil.printOut("[SRVDMSImpl] (addDocument) fileExt is 'blank'");
				int index = strFileName.lastIndexOf(".");
				if(index != -1)
					fileExt = strFileName.substring(index+1);
			}

			if((fileExt.equalsIgnoreCase("tif")) ||	(fileExt.equalsIgnoreCase("tiff")) || (fileExt.equalsIgnoreCase("bmp")) || (fileExt.equalsIgnoreCase("jpeg")) || (fileExt.equalsIgnoreCase("jpg")) || (fileExt.equalsIgnoreCase("jif")) || (fileExt.equalsIgnoreCase("gif")))
			{
                            System.out.println("[SRVDMSImpl] (addDocument) fileExt is ------- > "+fileExt);
                            SRVUtil.printOut("[SRVDMSImpl] (addDocument) fileExt is ------- > "+fileExt);
				cDocumentType = 'I';
			}
			else
			{
                            System.out.println("[SRVDMSImpl] (addDocument) fileExt is ------- > "+fileExt);
                            SRVUtil.printOut("[SRVDMSImpl] (addDocument) fileExt is ------- > "+fileExt);
				cDocumentType = 'N';
			}

			long byte_size = inputFile.length();
			if(byte_size > 0 && ((fileExt.equalsIgnoreCase("tif")) || (fileExt.equalsIgnoreCase("tiff"))))
			{
				try
				{
					nNoOfPages = Tif6.getPageCount(strFileName);
					System.gc();
				}
				catch(Exception ipex)
				{
					ipex.printStackTrace();
					nNoOfPages = 0;
				}
			}

                     
			JPDBRecoverDocData oRecoverDocData = new JPDBRecoverDocData();
			oRecoverDocData.m_cDocumentType = cDocumentType;
			oRecoverDocData.m_nDocumentSize = (int)byte_size;
			oRecoverDocData.m_sVolumeId = Short.parseShort(strFoldVolId);
                        
                        
                        int JtsPort = Integer.parseInt(SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort());
                        short shortJtsport = (short) (JtsPort);

                        String JtsIp = SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp();
                        
                        System.out.println("[SRVDMSImpl] (addDocument) DOC SIZE ---- > "+byte_size);
                        SRVUtil.printOut("[SRVDMSImpl] (addDocument) DOC SIZE ---- > "+byte_size);
                        
			//if the contents of file is null
			if(byte_size != 0)  //// Add To Image Server If Imported Only
			{
				try
				{
                                    
					CPISDocumentTxn.AddDocument_MT(null, JtsIp, shortJtsport, SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(), Short.parseShort(strFoldVolId), strFileName, oRecoverDocData,"", IsIndex);
				}
				catch(JPISException ex)
				{
					inputFile.delete();
					throw new JPISException() ;
                                                //new JPISException() ;
				}
				catch(Exception ex1)
				{
					inputFile.delete();
					ex1.printStackTrace();
					throw ex1;
				}
			}
			else
			{	
				throw new Exception ();
			}

			nDocumentSize = oRecoverDocData.m_nDocumentSize;
			String strDocImageIndex = String.valueOf(IsIndex.m_nDocIndex)+"#"+String.valueOf(IsIndex.m_sVolumeId)+"#";
			localInputXml.append(SRVUtil.writeTag("ISIndex", strDocImageIndex));
			localInputXml.append(SRVUtil.writeTag("NoOfPages", nNoOfPages+""));
			localInputXml.append(SRVUtil.writeTag("DocumentType", cDocumentType+""));
			localInputXml.append(SRVUtil.writeTag("DocumentSize", nDocumentSize+""));
			strOutputXml = SRVCallBroker.makeCall("CPISDocumentTxn.AddDocument_MT", localInputXml.toString());
                        
                        System.out.println("[SRVDMSImpl] (addDocument) OUTPUT XML ------------- > "+strOutputXml);
                        SRVUtil.printOut("[SRVDMSImpl] (addDocument) OUTPUT XML ------------- > "+strOutputXml);
			inputFile.delete();
		}
		catch(Exception e)
		{
			StringBuffer inputXml = new StringBuffer(100); 
			inputXml .append(SRVUtil.startTag("AddDocument_Output"));
			inputXml.append(SRVUtil.writeTag("EngineName",strCabinetName));
			inputXml.append(SRVUtil.startTag("Exception"));
                        /*@ToDo
                         * Status
                        */
			inputXml.append(SRVUtil.writeTag("Status","-1"));
			inputXml.append(SRVUtil.writeTag("Error",e.getMessage()));
			inputXml.append(SRVUtil.endTag("Exception"));
			inputXml.append(SRVUtil.endTag("AddDocument_Output"));
			strOutputXml = inputXml.toString();
		}
		return strOutputXml;
	}

}
