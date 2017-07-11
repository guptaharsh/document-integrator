package com.servo.session;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.newgen.niplj.fileformat.Tif6;
import com.newgen.omni.jts.security.EncodeImage;
import com.servo.generateXML.SRVGenerateXML;
import com.servo.util.SRVUtil;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SRVImageAnnotation", urlPatterns = {"/SRVImageAnnotation"})
public class SRVImageAnnotation extends HttpServlet
{
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
	{
            
            System.out.println("INSIDE --------------- SRVImageAnnotation ---------------");
            SRVUtil.printOut("INSIDE --------------- SRVImageAnnotation ---------------");
//        String strImageData = request.getPathInfo();
//		strImageData = strImageData.substring(1);
		
		
//		String strOption = request.getParameter("Option"); 
//		String strDocId = request.getParameter("DocId"); 
//		String strPageNo = request.getParameter("PageNo"); 

		try
		{ 
			saveAnnotation(request,response);	 
		}
		catch (Exception e)
		{
			//WFCallBroker.generateLog("wdexception", "\r\n imageannotation Error 1 = " + e.getMessage() +"  \r\n");
                        System.out.println("[SRVImageAnnotation]    service() , ERROR : "+SRVUtil.getExcpStackTrace(e));
                        SRVUtil.printOut("[SRVImageAnnotation]    service() , ERROR ");
                        SRVUtil.printErr("[SRVImageAnnotation]    service() , ERROR : "+SRVUtil.getExcpStackTrace(e));
                }

		finally
		{
//			parameterTable.clear();
//			parameterTable = null;
		}
	}


	private void saveAnnotation(HttpServletRequest request, HttpServletResponse response) throws IOException,Exception
	{       
        //take the object of  session bean
		HttpSession session = request.getSession(false);
		
		
		ResourceBundle genRSB = ResourceBundle.getBundle("SRVApsOd_en_IN");
                
//		String pid = (String)parameterTable.get("pid");
//		String wid = (String)parameterTable.get("wid");  
		String strEncoding = ("UTF-8");
		
		request.setCharacterEncoding(strEncoding);
		response.setContentType("text/html;charset=" + strEncoding);
		
		String docIndex = request.getParameter("DocId");
		String pageNo =	 request.getParameter("PageNo"); 
		String userDBId = request.getParameter("UserDBId"); 
		String userName = request.getParameter("UserName"); 
		
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(),strEncoding));
		StringBuffer sbData = new StringBuffer();
                String dataString = in.readLine();
//                WFCallBroker.generateLog("ImageStamp", "\r\n\n\n docIndex = " + docIndex + "\t pageNo = " +pageNo);
                System.out.println("[SRVimageAnnotation]    saveAnnotation() ---------- > DocIndex = "+docIndex+" , PageNo = "+pageNo);
                SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() ---------- > DocIndex = "+docIndex+" , PageNo = "+pageNo);
		
		
		//-------------------------------------------New Code for Stamping -----------------------------------------------------------// 
		// annoVector contains all stamps data, one stamp data in one element which is of Properties type
		Vector annoVector = new Vector();					//Added for stamping-by Sachin Sinha
		Properties annoProp = null;							//Added for stamping-by Sachin Sinha for modifying the anno buffer
															//for stamps and creating multi page tif file with respect to the stamps
															//applied on a particular page of the document.
		boolean createPropFlag = false;
		try
		{
			while(dataString != null){
				dataString = dataString.trim();
				if(dataString.length() == 0)
				{
					dataString = in.readLine();
					continue;
				}
				if(dataString.length() > 0 && !(dataString.indexOf("Stamp") == -1) && dataString.indexOf("=") == -1) //Added for stamping-by Sachin Sinha
				{
					if(annoProp != null)
						annoVector.add(annoProp);
					
					annoProp = new Properties();
					annoProp.setProperty("Annotationname", dataString.trim());
					createPropFlag = true;
				}

				if(dataString.length() > 0 && createPropFlag && dataString.indexOf("[") == -1 && dataString.indexOf("TotalGroups") == -1 && dataString.indexOf("TotalGroups") == -1 && !dataString.startsWith("Name") && dataString.indexOf("LoginUserRights") == -1 && dataString.indexOf("AnnotationGroupIndex") == -1  && dataString.indexOf("TotalAnnotations") == -1  && dataString.indexOf("NoOfStamp") == -1) 
				{
					// populating properties object for creating document's pages' tif file and storing stamps data in annoprop object
					int equalIndex = dataString.indexOf("=");
					String key = dataString.substring(0, equalIndex);
					String value = dataString.substring(equalIndex+1);
					annoProp.setProperty(key, value);
					if(dataString.startsWith("DocName"))
						createPropFlag = false;
				}
				sbData.append(dataString + "\n");
				dataString = in.readLine();
			}	    	  
		}
		catch(Exception ex)
		{
//			WFCallBroker.generateLog("wdexception", "\r\n line 179 = " + ex.getMessage());
                        System.out.println("[SRVimageAnnotation]    saveAnnotation() , ERROR : "+SRVUtil.getExcpStackTrace(ex));
                        SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() , ERROR : ");
                        SRVUtil.printErr("[SRVimageAnnotation]    saveAnnotation() , ERROR : "+SRVUtil.getExcpStackTrace(ex));
		}
		finally
		{
			try
			{
				if(in != null)
				{
					in.close();
				}
				in = null;
			}
			catch(Exception e)
			{
                            System.out.println("[SRVimageAnnotation]    saveAnnotation() , ERROR : "+SRVUtil.getExcpStackTrace(e));
                            SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() , ERROR : ");
                            SRVUtil.printErr("[SRVimageAnnotation]    saveAnnotation() , ERROR : "+SRVUtil.getExcpStackTrace(e));
			}
		}
                
		if(annoProp != null)
			annoVector.add(annoProp); // to add last annoProp
                   
		//-------------------------------------------New Code for Stamping END -----------------------------------------------------------//	
		dataString = null;
		String strAnnotBuffer = sbData.toString();
//                WFCallBroker.generateLog("ImageStamp", "\r\n strAnnotBuffer = " + strAnnotBuffer);
                System.out.println("[SRVimageAnnotation]    saveAnnotation() ---------- > AnnotBuffer = "+strAnnotBuffer);
                SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() ---------- > AnnotBuffer = "+strAnnotBuffer);
                
		sbData = null;
		String strStringToFind	=	"TotalGroups=";
		int nPos1		=	strAnnotBuffer.indexOf(strStringToFind);
		nPos1		   += 	strStringToFind.length();
		strStringToFind	= "[Group";	
		int nPos2 = strAnnotBuffer.indexOf(strStringToFind);
		
		StringBuffer annotGroupList = new StringBuffer();
		File docPageStampFile = null;
		File docStampFile = null;
		File oldDocStampFile =null;

		//create a temporary tiff file to fetch previously saved stamps from database                 
		String strFolderPath = "stamp"+File.separator+session.getId()+File.separator+docIndex+"_database";
		String docPageStampFileName = "D" + docIndex + "P" + pageNo + "DB" + userDBId + ".tif"; // name of multipage stamp file
//                WFCallBroker.generateLog("ImageStamp", "\r\n strFolderPath = " + strFolderPath);
//		WFCallBroker.generateLog("ImageStamp", "\r\n docPageStampFileName = " + docPageStampFileName);
		System.out.println("[SRVimageAnnotation]    saveAnnotation() ---------- > FolderPath = "+strFolderPath+" , DocPageStampFileName = "+docPageStampFileName);
                SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() ---------- > FolderPath = "+strFolderPath+" , DocPageStampFileName = "+docPageStampFileName); 
		
		oldDocStampFile  = new File(strFolderPath);
		if(!oldDocStampFile.isDirectory())
			oldDocStampFile.mkdirs();
                File tempFolder=oldDocStampFile;
		oldDocStampFile  = new File(strFolderPath+File.separator+docPageStampFileName);	
               if(!oldDocStampFile.exists())
			getStampFile(userDBId,docIndex,pageNo,oldDocStampFile,strEncoding);
               
		int fileNo = 0;
		String strGrpAnnoBuffer = null;
                
		
        if(nPos2 > 0)
		{
			// nNoOfAnnot represents no. of annotation groups
			String strNoOfAnnot = strAnnotBuffer.substring(nPos1, nPos2);
			int nNoOfAnnot = 0;
			try
			{
				nNoOfAnnot	= Integer.parseInt(strNoOfAnnot.trim());
			}
			catch(Exception e)
			{
				throw new Exception(e.getMessage());
			}

			int allStampCounter = 0;
			
			String docStampFileName = "D" + docIndex + "_P" + pageNo + "_DB_" + userDBId + ".tif";
			docStampFile  = new File(strFolderPath);
			if(!docStampFile.isDirectory())
				docStampFile.mkdirs();

			docStampFile  = new File(strFolderPath+File.separator+docStampFileName);
			 if(docStampFile.exists())
				docStampFile.delete(); 
			
			int k=1;
			for(int i=1; i<= nNoOfAnnot; i++)
			{
				annotGroupList.append("<AnnotationGroup>");
				// First get the position for Group string 
				strStringToFind = "[Group" + i + "]";
				nPos1	= strAnnotBuffer.indexOf(strStringToFind);
							
				strStringToFind	= "[Group" + (i+1) + "]";
				nPos2	= strAnnotBuffer.indexOf(strStringToFind);

				// Get the annotation buffer 
				String strTempAnot;
				if(nPos2 > 0)
				{
					strTempAnot		= strAnnotBuffer.substring(nPos1,nPos2);
					strAnnotBuffer  = strAnnotBuffer.substring(nPos2);
				}
				else
					strTempAnot	= strAnnotBuffer;
				
				//Get the Group name of the annotation
				strStringToFind	= "Name=";
				nPos1	 = strTempAnot.indexOf(strStringToFind);
				nPos1	+= strStringToFind.length();
				nPos2	 = strTempAnot.indexOf("LoginUserRights=");
				String strAnnotName	=  strTempAnot.substring(nPos1,nPos2);
				strStringToFind = "AnnotationGroupIndex=";
				nPos1	= strTempAnot.indexOf(strStringToFind);
				nPos1	+= strStringToFind.length();
				strStringToFind	= "[" + strAnnotName.trim() + "AnnotationHeader]";
				nPos2	= strTempAnot.indexOf(strStringToFind);
				String strAnnotIndex = strTempAnot.substring(nPos1,nPos2);
				String accesstype="I";
				
				if(!strAnnotIndex.trim().equals("0"))// if annotation exist for this particular group
				{
					String retXml = SRVGenerateXML.getGetAnnotationGroupPropertyXml(
									SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
									userDBId,
									null,null ,strAnnotIndex.trim());

					HashMap outXmlMap = SRVUtil.getXMLMap(retXml);
					accesstype = (String)outXmlMap.get("AccessType");
					// fetching AnnotationBuffer which is used to retrieve fileId of particular stamp
					strGrpAnnoBuffer = (String)outXmlMap.get("AnnotationBuffer");
				} 

				annotGroupList.append("<AccessType>"+accesstype+"</AccessType>");
				nPos2 += strStringToFind.length();
				
				strTempAnot	=	strTempAnot.substring(nPos2);
				if(strTempAnot.indexOf("\r\n")==-1)
					strTempAnot=strTempAnot.replaceAll("\n","\r\n");

				annoProp = null;
				
				//Changed By Ankur Jain for stamping
				if((annoVector.size() > 0) && (strTempAnot.indexOf("StampFileSource") != -1))
				{
					// Code written by krishan for image stamp (WCL_5.0.1.001.056)
					strStringToFind	= "NoOfStamp=";
					nPos1	= strTempAnot.indexOf(strStringToFind);
					String strTemp = strTempAnot.substring(nPos1);
					nPos1	= strStringToFind.length();
					strStringToFind	= "[" + strAnnotName.trim();
					nPos2	= strTemp.indexOf(strStringToFind);
					String noOfStampInGroup = strTemp.substring(nPos1,nPos2);
					int noOfStampInCurrGroup = Integer.parseInt(noOfStampInGroup.trim());
					String strTempAnot1 =strTempAnot;
					String strTempAnot2 = "";
					
					for(int j=0; j<noOfStampInCurrGroup; j++)
					{	
						strTempAnot1 = strTempAnot;
						annoProp = (Properties) annoVector.elementAt(allStampCounter);
						allStampCounter++;
						String StampFileSource = (String) annoProp.getProperty("StampFileSource");
						File inputTifFile = null;
						String fileId = annoProp.getProperty("FileName");
                                                

						boolean dynamicflag = false;
						String annotName = (String) annoProp.getProperty("Annotationname");
						strStringToFind	= annotName;
						nPos1	= strTempAnot.indexOf(strStringToFind);
//						WFCallBroker.generateLog("ImageStamp", "\r\n StampFileSource = " + StampFileSource);
                                                System.out.println("[SRVimageAnnotation]    saveAnnotation() ---------- > StampFileSource = "+StampFileSource);
                                                SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() ---------- > StampFileSource = "+StampFileSource); 
												
						if(StampFileSource !=null)
						{
							if(StampFileSource.trim().equalsIgnoreCase("MASTER"))
							{       
								String fileName="";
								String dynamicFile = annoProp.getProperty("DocName");
								String sStampIniPath = "";
								String strApplicationPath = getServletContext().getRealPath("/");
								if(!strApplicationPath.endsWith(File.separator))
									strApplicationPath += File.separator;
//								WFCallBroker.generateLog("ImageStamp", "\r\n dynamicFile = " + dynamicFile);
                                                                System.out.println("[SRVimageAnnotation]    saveAnnotation() ---------- > DynamicFile = "+dynamicFile);
                                                                SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() ---------- > DynamicFile = "+dynamicFile); 
								
								if(dynamicFile == null || dynamicFile.equals("") || dynamicFile.equalsIgnoreCase("null"))
								{
									sStampIniPath = strApplicationPath + "webtop" + File.separator + "applet" + File.separator + "stamps" + File.separator + userName.toUpperCase();

									File f = new File(sStampIniPath);
									if(!f.exists())
										sStampIniPath = strApplicationPath + "webtop" + File.separator + "applet" + File.separator+"stamps";
									
									inputTifFile = getTifFile(fileId,  sStampIniPath);
//                                                                        WFCallBroker.generateLog("ImageStamp", "\r\n sStampIniPath = " + sStampIniPath);  
                                                                        System.out.println("[SRVimageAnnotation]    saveAnnotation() ---------- > sStampIniPath = "+sStampIniPath);
                                                                        SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() ---------- > sStampIniPath = "+sStampIniPath); 
								}
								else
								{   
									fileName = dynamicFile;
									inputTifFile = new File(strFolderPath, fileName);
                                                                        
									dynamicflag=true;
								}
							}
							else
							{
								try
								{
                                                                   
                                                                     
                                                                    inputTifFile = new File(strFolderPath, fileId + ".tif");
                                                                 
                                                                    
                                                                         
                                                                           
									Tif6.retrieveTif6Page(oldDocStampFile.getAbsolutePath(), Integer.parseInt(fileId), inputTifFile.getAbsolutePath());
                                                                        
                                                                        
									dynamicflag=true;
								}
								catch(IOException ioExp)
								{
                                                                    ;
								}
								catch(IllegalAccessException ilgExp)
								{
                                                                	;
								}
							}

							try
							{
								if(!docStampFile.exists()) // new multi page tif file is getting created, data of first stamp is added
								{
                                                                         
									docStampFile.createNewFile();
									writeTifDataInNewFile(docStampFile, inputTifFile);
								}
								else
                                                                        
									Tif6.appendTif6IntoTif6(inputTifFile.getPath(), docStampFile.getPath());
                                                                       
							}
							catch(Exception e)
							{
								docStampFile.delete();
								e.printStackTrace();
							}

							if(inputTifFile.exists() && dynamicflag)
								inputTifFile.delete();

							int indexOfFileName = strTempAnot1.indexOf("FileName=",nPos1);
							int indexOfStampSource = strTempAnot1.indexOf("StampFileSource=",nPos1);
							int indexAfterStampSource = indexOfStampSource + "StampFileSource=MASTER".length();

							String prefix = strTempAnot1.substring(0, indexOfFileName);
							String suffix = strTempAnot1.substring(indexAfterStampSource);
							strTempAnot2 = prefix + "FileName=" + (k) + "\r\n" + "StampFileSource=STREAM" + "\n";
							strTempAnot1 = suffix;
							strTempAnot = strTempAnot2 + strTempAnot1;
							k++;
						}
					}
				}

				annotGroupList.append("<AnnotGroupIndex>" + strAnnotIndex.trim() + "</AnnotGroupIndex>");
				annotGroupList.append("<AnnotGroupName>" + strAnnotName.trim() + "</AnnotGroupName>");
				annotGroupList.append("<AnnotationType>A</AnnotationType>");
				annotGroupList.append("<PageNo>" + pageNo + "</PageNo>");
				//annotGroupList.append("<CreationDateTime>" + WFUtility.getCurrentDateTime() + "</CreationDateTime>");
				annotGroupList.append("<CreationDateTime>" + new Date() + "</CreationDateTime>");
				annotGroupList.append("<AnnotationBuffer>" + strTempAnot + "</AnnotationBuffer>");
				annotGroupList.append("</AnnotationGroup>");
			}
		}
		else
		{
			annotGroupList.append("<PageNo>"+pageNo+"</PageNo>");
			annotGroupList.append("<LimitCount>100</LimitCount>");
			annotGroupList.append("<VersionNo>0.01</VersionNo>");
		}
		
//        WFCallBroker.generateLog("ImageStamp", "\r\n annotGroupList = " + annotGroupList.toString()); 
            System.out.println("[SRVimageAnnotation]    saveAnnotation() ---------- > AnnotGroupList = "+annotGroupList.toString());
            SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() ---------- > AnnotGroupList = "+annotGroupList.toString()); 
		
		annoVector = null;
		String retXml = SRVGenerateXML.setAnnotations(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
												userDBId,
												docIndex,
												pageNo,
												annotGroupList.toString().trim());

		annotGroupList = null;
		HashMap outXmlMap = SRVUtil.getXMLMap(retXml);
		
		if(!((String)outXmlMap.get("Status")).equals("0"))
		{
			docStampFile.delete();
			//throw new WFException(xmlResponse.getVal("Status"),"","","",xmlResponse.getVal("Error"),"Set Image Annotaitons");
                        System.out.println("[SRVImageAnnotation]    saveAnnotation() , ERROR ---------- > Status = "+(String)outXmlMap.get("Status")
                                +" , ERROR MSG = "+(String)outXmlMap.get("Error"));
                        SRVUtil.printOut("[SRVImageAnnotation]    saveAnnotation() , ERROR ---------- > Status = "+(String)outXmlMap.get("Status")
                                +" , ERROR MSG = "+(String)outXmlMap.get("Error"));
                        SRVUtil.printErr("[SRVImageAnnotation]    saveAnnotation() , ERROR ---------- > Status = "+(String)outXmlMap.get("Status")
                                +" , ERROR MSG = "+(String)outXmlMap.get("Error"));
		}
		
		if(docStampFile != null || (oldDocStampFile!= null && oldDocStampFile.exists()))
		{
                      
			String stampData = "";
			byte[] stampFileData = null;

			if(docStampFile != null)
			{
                                 
				stampFileData = getStampData(docStampFile);
                                 
				if(!("8859_1".equalsIgnoreCase(strEncoding)))
                                {
                                    
					stampData = (EncodeImage.encodeImageData(stampFileData)).toString();
                                      
                                }
				else{
					stampData = new String(stampFileData, "8859_1") ;
                                        
                                }
                                
			}
			else
				stampData = null;
                                
			retXml = SRVGenerateXML.getSetDocPageBinDataXml(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
											userDBId,
											pageNo, docIndex, "0", stampData);
		
                }
                  
                 
		if(docStampFile != null && docStampFile.exists())
                    
			 docStampFile.delete();
                

		if(oldDocStampFile!= null && oldDocStampFile.exists())
			 oldDocStampFile.delete();
                
                if(tempFolder!= null && tempFolder.exists())
			 tempFolder.delete();
                
		outXmlMap = null;
	}


	/********************************************************************
	Function name      : getHashtable
	Description        : 
	Return type        : void 
	Argument 1         : Hashtable parameterTable
	Argument 2         : String nameValueNToken
	Argument 3         : String strToken
	********************************************************************/
	private void getHashtable(Hashtable parameterTable, String nameValueNToken,String strToken)
	{
		StringTokenizer nameValueTokenFull = new StringTokenizer(nameValueNToken,strToken);
		StringTokenizer nameValueToken = null;
		String nameValuePair = null;
		while(nameValueTokenFull.hasMoreElements())
		{
			nameValuePair = nameValueTokenFull.nextToken();
			nameValueToken = new StringTokenizer(nameValuePair,"=");
			parameterTable.put(nameValueToken.nextToken(),nameValueToken.nextToken()); 
		}
		nameValueTokenFull = null;
		nameValueToken = null;
	}

	
	/********************************************************************
	Function name      : getTifFileName
	Description        : getting the tif file name from stamp.ini for
						 creating multi page tif file
	Return type        : String 
	Argument 1         : String fileId
	Argument 2         : String iniFilePath
	********************************************************************/

	private File getTifFile(String fileId, String sFilePath) throws IOException
	{        
		File iniFile = new File(sFilePath + File.separator + "stamp.ini");
               
		FileReader fr = null;
		BufferedReader br = null;
		String fileName = null;
                String imagepath=null;
		try
		{
			fr = new FileReader(iniFile);
			br = new BufferedReader(fr);
                        
			String temp = null;
			while((temp = br.readLine()) != null)
			{   
				temp = temp.trim();
                              
				if(temp.startsWith("[BmpStamp"))
				{       
					String fileIndex = temp.substring(temp.indexOf("Stamp")+5, temp.length() -1);
                                        
					if(fileIndex.equals(fileId))
					{       
                                  
						br.readLine(); 
                                               
						fileName = br.readLine().trim();
                                                 
						fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
						break;
					}
				}
			}
			
            
			sFilePath=sFilePath.substring(0,sFilePath.lastIndexOf("\\"))+File.separator+"stamps";
                        
			File inputTifFile = new File(sFilePath,fileName); 
//                       WFCallBroker.generateLog("ImageStamp", "\r\n inputTifFile = " + inputTifFile.getAbsolutePath());
//                       WFCallBroker.generateLog("ImageStamp", "\r\n inputTifFile1 = " + sFilePath);  
                       
                       System.out.println("[SRVimageAnnotation]    saveAnnotation() ---------- > InputTifFile = "+inputTifFile.getAbsolutePath()+" , InputTifFile1 = "+sFilePath);
                       SRVUtil.printOut("[SRVimageAnnotation]    saveAnnotation() ---------- > InputTifFile = "+inputTifFile.getAbsolutePath()+" , InputTifFile1 = "+sFilePath);
                       
                       
			return inputTifFile;
		}
		catch(IOException ioEx)
		{
			throw ioEx;
		}
		finally
		{
			try
			{
				if(br != null)
				{
					br.close();
				}
				if(fr != null)
				{
					fr.close();
				}
			}
			catch(IOException ioEx1)
			{
				throw ioEx1;
			}
		}
	}


	/********************************************************************
	Function name      : writeTifDataInNewFile
	Description        : creating the first page of multipage tif file
						 using the file stated in stamp.ini
	Return type        : boolean 
	Argument 1         : File docPageTifFile
	Argument 2         : File srcStampFile
	********************************************************************/

	private boolean writeTifDataInNewFile(File docPageTifFile, File srcStampFile) throws IOException
	{
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try
		{
			fis = new FileInputStream(srcStampFile);
			byte[] bFile = new byte[fis.available()];
			fis.read(bFile);
			fos = new FileOutputStream(docPageTifFile);
			fos.write(bFile);
			fos.flush();
			return true;
		}
		catch(IOException ioEx)
		{
			throw ioEx;
		}
		finally
		{
			try
			{
				if(fis != null)
				{
					fis.close();
				}

				if(fos != null)
				{
					fos.flush();
					fos.close();
				}
			}
			catch(IOException ioEx1)
			{
				throw ioEx1;
			}
		}
	}

	
	/********************************************************************
	Function name      : getStampData
	Description        : getting the binary data of multi page tif, 
						 needed for creating the input XML
						 of setDocPageBinData	
	Return type        : byte[] 
	Argument 1         : File stampTifFile
	********************************************************************/

	private byte[] getStampData(File stampTifFile) throws IOException
	{       
		FileInputStream fis = new FileInputStream(stampTifFile);
		byte[] bFile = new byte[fis.available()];
		try
		{
                        
			fis.read(bFile);
                         
			return bFile;
		}
		catch(IOException ioEx)
		{
                    
			throw ioEx;
		}
		finally
		{
			try
			{
                              
				if(fis != null)
					fis.close();
			}
			catch(IOException ioEx1)
			{
                              
				throw ioEx1;
			}
		}
	}
        private void getStampFile(String userDBId , String docIndex,String pageNo, File f, String strEncoding) throws IOException, Exception
	{
		String retXml = new String(new byte[0], "8859_1");
		retXml = SRVGenerateXML.getGetDocPageBinDataXml(SRVUtil.getSRVApplicationConfig().getDBConfig().getDBName(),
								userDBId,pageNo,"0",docIndex);
		System.out.println("retXml :"+retXml);
		HashMap outXmlMap = SRVUtil.getXMLMap(retXml);
		int idTag = retXml.indexOf("<AnnotationData>", 0);
		
		if(!((String)outXmlMap.get("Status")).equals("0"))
		{
                        System.out.println("[SRVImageAnnotation]    saveAnnotation() , ERROR ---------- > Status = "+(String)outXmlMap.get("Status")
                                +" , ERROR MSG = "+(String)outXmlMap.get("Error"));
                        SRVUtil.printOut("[SRVImageAnnotation]    saveAnnotation() , ERROR ---------- > Status = "+(String)outXmlMap.get("Status")
                                +" , ERROR MSG = "+(String)outXmlMap.get("Error"));
                        SRVUtil.printErr("[SRVImageAnnotation]    saveAnnotation() , ERROR ---------- > Status = "+(String)outXmlMap.get("Status")
                                +" , ERROR MSG = "+(String)outXmlMap.get("Error"));
                        throw new Exception();
		}
		
		String imageDataEncoded = (String)outXmlMap.get("AnnotationData");
                System.out.println("imageDataEncoded : "+imageDataEncoded);
		byte[] bArr=null;

		if(imageDataEncoded.length() > 0 && !("8859_1".equalsIgnoreCase(strEncoding)))
		{
			bArr = EncodeImage.decodeImageData(imageDataEncoded);
		}
		else
		{
			bArr = imageDataEncoded.getBytes("8859_1") ;
		}

		FileOutputStream fs =  new FileOutputStream(f);
		fs.write(bArr);
		fs.close();
	}
}
 