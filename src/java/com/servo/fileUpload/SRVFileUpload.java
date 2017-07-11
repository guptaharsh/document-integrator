
package com.servo.fileUpload;

import com.servo.util.SRVUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet(name = "SRVFileUpload", urlPatterns = {"/SRVFileUpload"})
public class SRVFileUpload extends HttpServlet {

    
   private boolean isMultipart;
   private String filePath , filePath_ORG;
   private int maxFileSize = 5 * 1024 * 1024;
   //private int maxFileSize = 249050;
   private int maxMemSize = 5 * 1024;
   private File file ;
   
   private long entryTime;

   public void init( ){
      // Get the file location where it would be stored.
//      filePath = 
//             //getServletContext().getInitParameter("file-upload"); 
//             getServletContext().getRealPath("/upload/"); 
      
//       System.out.println("FilePath -- > "+filePath);
//       String weblogicDomainPath = System.getProperty("weblogic.domainDir");
//       System.out.println("weblogicDomainPath ---------- > "+weblogicDomainPath);
       
//  Getting weblogic domainDir      
       entryTime = System.currentTimeMillis();
       //String domainDir = System.getenv("DOMAIN_HOME");
       entryTime = entryTime - System.currentTimeMillis();
       SRVUtil.printOut("[SRVFileUpload] (init) ------------- > Getting domainDir , Time Taken : "+entryTime);
//       filePath = domainDir + "/apsod_tmp" ;
//       
//       System.out.println("domainDir ------------- > "+domainDir);
   }
   public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, java.io.IOException {
      // Check that we have a file upload request
      System.out.println("<---------------- INSIDE Post ----------------->");
      
      String domainDir ="";
      
       System.out.println("[SRVFileUpload] (doPost) ADDDOCAbsPath ------------- > "+request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH"));
       SRVUtil.printOut("[SRVFileUpload] (doPost) ADDDOCAbsPath ------------- > "+request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH"));
       
//  Check if directory exists       
        File file=new File(request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH"));
        boolean exists = file.exists();
        if (!exists) 
        {
            // It returns false if File or directory does not exist
            System.out.println("[SRVFileUpload] (doPost) The file or directory you are searching does not exist : " + request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH"));
            SRVUtil.printOut("[SRVFileUpload] (doPost) ERROR : Directory ' "+request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH")+" ' doesn't exists");
              
                    domainDir = System.getenv("DOMAIN_HOME");
                    //file = new File(domainDir+ "/" +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER"));
                    System.out.println("[SRVFileUpload] (doPost) Preparing FilePath of Weblogic domain--------------- > ");
                    SRVUtil.printOut("[SRVFileUpload] (doPost) Preparing FilePath of Weblogic domain--------------- > ");
                    file = new File(domainDir+ File.separator +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER"));
                    exists = file.exists();
                    if(!exists)
                    {
                        //new File(domainDir+"/"+request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER")).mkdir();
                        System.out.println("[SRVFileUpload] (doPost) Creating == "+request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER")+" == in FilePath of Weblogic domain--------------- > ");
                        SRVUtil.printOut("[SRVFileUpload] (doPost) Creating == "+request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER")+" == in FilePath of Weblogic domain--------------- > ");
                        new File(domainDir+ File.separator +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER")).mkdir();
                        //filePath = domainDir + "/" +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
                        filePath_ORG = domainDir + File.separator +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
                        System.out.println("[SRVFileUpload] (doPost) FilePath in Weblogic domain ---------- > "+filePath_ORG);
                        SRVUtil.printOut("[SRVFileUpload] (doPost) FilePath in Weblogic domain ---------- > "+filePath_ORG);
                    }
                    else
                    {
                        //filePath = domainDir + "/" +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
                        filePath_ORG = domainDir + File.separator +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
                        System.out.println("[SRVFileUpload] (doPost) FilePath in Weblogic domain ---------- > "+filePath_ORG);
                        SRVUtil.printOut("[SRVFileUpload] (doPost) FilePath in Weblogic domain ---------- > "+filePath_ORG);
                    }
        }
        else
        {
            // It returns true if File or directory exists

    //Search folder in the absolute path
            //file = new File(request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH")+"/"+request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER"));
            file = new File(request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH")+ File.separator +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER"));
            boolean folderExists = file.exists();
                if(!folderExists)
                {
                  file.mkdir();
                  //filePath = request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH")+"/"+request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
                  filePath_ORG = request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH")+ File.separator +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
                  System.out.println("[SRVFileUpload] (doPost) The file or directory you are searching does exist : " + filePath_ORG);
                  SRVUtil.printOut("[SRVFileUpload] (doPost) The file or directory you are searching does exist : " + filePath_ORG);
                }
                
                else
                {
                    //filePath = request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH")+"/"+request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
                    System.out.println("[SRVFileUpload] (doPost) Folder == "+ file +" == already exists");
                    SRVUtil.printOut("[SRVFileUpload] (doPost) Folder == "+ file +" == already exists");
                    filePath_ORG = request.getServletContext().getInitParameter("ADDDOCUMENTABSPATH")+ File.separator +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
                    System.out.println("[SRVFileUpload] (doPost) FilePath == "+filePath_ORG);
                    SRVUtil.printOut("[SRVFileUpload] (doPost) FilePath == "+filePath_ORG);
                }
        }
        
        filePath = filePath_ORG.replace("/", File.separator).replace("\\", File.separator);
      
//      String domainDir = System.getenv("DOMAIN_HOME");
//      filePath = domainDir + "/" +request.getServletContext().getInitParameter("ADD_DOCUMENT_FOLDER") ;
       
      System.out.println("[SRVFileUpload] (doPost) domainDir ------------- > "+domainDir);
      SRVUtil.printOut("[SRVFileUpload] (doPost) domainDir ------------- > "+domainDir);
      
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      java.io.PrintWriter out = response.getWriter( );
      
      HashMap formData = new HashMap();
      
      if( !isMultipart ){
         
         out.println("<p>No file uploaded</p>"); 
         
         return;
      }
      //System.out.println("DiskFileItemFactory");
      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      //factory.setRepository(new File("c:/temp"));
      factory.setRepository(new File(filePath));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      
      upload.setSizeMax( maxFileSize );

      try
      { 
            // Parse the request to get file items.
          entryTime = System.currentTimeMillis();
          
          System.out.println("[SRVFileUpload] (doPost) Preparing LIST ---------- > ");
          SRVUtil.printOut("[SRVFileUpload] (doPost) Preparing LIST ---------- > ");
                
            List fileItems = upload.parseRequest(request);
                System.out.println("[SRVFileUpload] (doPost) LIST ---------- > "+fileItems);
                SRVUtil.printOut("[SRVFileUpload] (doPost) LIST ---------- > "+fileItems);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();


            String filePath_Upload = "";
            String parameters[] = new String[10];
            int index = 0;
            boolean validFileSize = false;
            //System.out.println("Before While");    
            while ( i.hasNext () ) 
            {
                //System.out.println("INSIDE While"); 
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () )	
                {
                    //System.out.println("INSIDE IF");
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    System.out.println("[SRVFileUpload] (doPost) FieldName is ::::::: \n"+fieldName);
                    SRVUtil.printOut("[SRVFileUpload] (doPost) FieldName is ::::::: \n" +fieldName);
                    

                    String fileName_ORG = fi.getName();
                    
                    String fileName = fileName_ORG.replace("/", File.separator).replace("\\", File.separator);
                    System.out.println("[SRVFileUpload] (doPost) fileName_ORG == "+fileName_ORG+" , fileName == "+fileName);
                    SRVUtil.printOut("[SRVFileUpload] (doPost) fileName_ORG == "+fileName_ORG+" , fileName == "+fileName);
                    
//  Checking for file format support                    
                    String uploadedFileExtension = "";
                    try
                    {
                    
                        uploadedFileExtension = fileName.split("\\.")[1];
                    }
                    catch(Exception e)
                    {
                        uploadedFileExtension = fileName_ORG.split("\\.")[1];
                    }
                    
                    System.out.println("[SRVFileUpload] (doPost) uploadedFileExtension = "+uploadedFileExtension);
                    SRVUtil.printOut("[SRVFileUpload] (doPost) uploadedFileExtension = "+uploadedFileExtension);
                    
                    int countValidFileFormat = 0;
                    String fileFormats[] = (String[])request.getServletContext().getAttribute("SUPPORTEDFILEFORMATS");
                    
                    System.out.println("[SRVFileUpload] (doPost) supported file formats are : ");
                    SRVUtil.printOut("[SRVFileUpload] (doPost) supported file formats are : ");
                    
                    for(int fileFormatsIndex = 0; fileFormatsIndex < fileFormats.length; fileFormatsIndex++)
                    {
                        System.out.println("fileFormats["+fileFormatsIndex+"] = "+fileFormats[fileFormatsIndex]);
                        SRVUtil.printOut("fileFormats["+fileFormatsIndex+"] = "+fileFormats[fileFormatsIndex]);
                        
                        if(uploadedFileExtension.equalsIgnoreCase(fileFormats[fileFormatsIndex]))
                        {
                            countValidFileFormat = 1;
                            System.out.println("[SRVFileUpload] (doPost) file format supported");
                            SRVUtil.printOut("[SRVFileUpload] (doPost) file format supported");
                            
                            String contentType = fi.getContentType();
                            boolean isInMemory = fi.isInMemory();
                            long sizeInBytes = fi.getSize();
                            System.out.println("[SRVFileUpload] (doPost) file size is --------- > "+sizeInBytes);
                            SRVUtil.printOut("[SRVFileUpload] (doPost) file size is --------- > "+sizeInBytes);
                            
//  Comparing actual file size with maxFileSize
                            //if(sizeInBytes <= 250 * 1024)
                            System.out.println("[SRVFileUpload] (doPost) file size--------------->"+request.getServletContext().getInitParameter("FILE_SIZE"));
                            int filesize=Integer.parseInt( request.getServletContext().getInitParameter("FILE_SIZE"));
                            System.out.println("FileSize========="+filesize);
                            if(sizeInBytes <= filesize * 1024 * 1024)
                            {
//                                maxFileSize = (int) sizeInBytes + (250 * 1024); //  Increasing maxFileSize for files having size <= 250 kb
//                                System.out.println("[SRVFileUpload] (doPost) maxFileSize is --------- > "+maxFileSize);
//                                SRVUtil.printOut("[SRVFileUpload] (doPost) maxFileSize is --------- > "+maxFileSize);
                                
                                //upload.setSizeMax( maxFileSize );   //  Resetting the max file size
                            
                                // Write the file
                                                    //if( fileName.lastIndexOf("\\") >= 0 )
                                //if( fileName.lastIndexOf(File.separator) >= 0 || fileName.lastIndexOf("/") >= 0 || fileName.lastIndexOf("\\") >= 0)

                                validFileSize = true;
                                
                                if (fileName.trim().isEmpty()) 
                                {
                                    System.out.println("[SRVFileUpload] (doPost) ---------- fileName is empty -------------");
                                    SRVUtil.printOut("[SRVFileUpload] (doPost) ---------- fileName is empty -------------");

                                    RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                                    request.setAttribute("STATUS", "INVALIDFILE");
                                    rd.forward(request, response);
                                    //throw new IllegalStateException("InvalidFile");
                                }

                                //if( fileName.lastIndexOf("/") >= 0 || fileName.lastIndexOf(File.separator) >= 0 || fileName.lastIndexOf("\\") >= 0 )
                                if (fileName.lastIndexOf(File.separator) >= 0) 
                                {
                                    System.out.println("[SRVFileUpload] (doPost) IF ------- SRVFileUpload");
                                    SRVUtil.printOut("[SRVFileUpload] (doPost) IF ------- SRVFileUpload");
                                //file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
                                    System.out.println("[SRVFileUpload] (doPost) Before substring fileName ---------- > "+fileName);
                                    SRVUtil.printOut("[SRVFileUpload] (doPost) Before substring fileName ---------- > "+fileName);
                                    fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
                                    System.out.println("[SRVFileUpload] (doPost) After substring fileName ---------- > "+fileName);
                                    SRVUtil.printOut("[SRVFileUpload] (doPost) After substring fileName ---------- > "+fileName);
                                }
                                
                                //fileName = fileName + UUID.randomUUID() + System.currentTimeMillis();
                                String strUUID = UUID.randomUUID().toString();
                                
                                fileName =  strUUID.substring(0, strUUID.indexOf("-")) + System.currentTimeMillis() + "!!" + fileName ;
                                filePath_Upload = filePath +File.separator + fileName;
                                System.out.println("[SRVFileUpload] (doPost) Before file Write -------------- > ");
                                SRVUtil.printOut(" [SRVFileUpload] (doPost) Before file Write -------------- > ");
                                file = new File(filePath_Upload) ;
                                fi.write( file ) ;
                                System.out.println("[SRVFileUpload] (doPost) File WRITE successful --------------------- > ");
                                SRVUtil.printOut("[SRVFileUpload] (doPost) File WRITE successful --------------------- > ");
                            }
                            else
                            {
                                validFileSize = false;
                                System.out.println("[SRVFileUpload] (doPost) ----- File too large. Upload limit is 250kb ----- >");
                                SRVUtil.printOut("[SRVFileUpload] (doPost) ------ File too large. Upload limit is 250kb ----- >");
                                break;
//                                RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
//                                request.setAttribute("STATUS", "FileTooLarge");
//                                rd.forward(request, response);
                            }
                        }
                    }
                    
                    if(countValidFileFormat == 0)
                    {
                        System.out.println("[SRVFileUpload] (doPost) ----- File Format not supported ");
                        SRVUtil.printOut("[SRVFileUpload] (doPost) ------ File Format not supported ");
                        RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                        request.setAttribute("STATUS", "InvalidFileFormat");
                        rd.forward(request, response);
                    }
                    if(!validFileSize)
                    {
                        System.out.println("[SRVFileUpload] (doPost) ----- Is File Size Valid ----- > "+validFileSize);
                        SRVUtil.printOut("[SRVFileUpload] (doPost) ------ Is File Size Valid ----- > "+validFileSize);
                        RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
                        request.setAttribute("STATUS", "FileTooLarge");
                        rd.forward(request, response);
                    }
                
                }
                else
                {
                    System.out.println("[SRVFileUpload] (doPost) ----- Parameters["+index+"] :::: "+fi.getString());
                    SRVUtil.printOut("[SRVFileUpload] (doPost) ----- Parameters["+index+"] :::: "+fi.getString());
                    parameters[index] = fi.getString();
                    index = index + 1;
                }

            }

            System.out.println("[SRVFileUpload] (doPost)filePath_Upload --------------- > "+filePath_Upload);
            SRVUtil.printOut("[SRVFileUpload] (doPost)filePath_Upload --------------- > "+filePath_Upload);
            
            System.out.println("[SRVFileUpload] (doPost) ------ Parameters are :::::::::: \n"+parameters);
            SRVUtil.printOut("[SRVFileUpload] (doPost)----- Parameters are :::::::::: \n"+parameters);
        //          System.out.println("File : "+request.getParameter("file") +", U : "+request.getParameter("userDBId")+" , P : "+request.getParameter("parent_document_txt")+" , C : "+request.getParameter("child_document_name")+" , A : "+request.getParameter("applicant_type_name"));
            RequestDispatcher rd = request.getRequestDispatcher("SRVFileNomenclature");
            request.setAttribute("filePath", filePath_Upload);
            request.setAttribute("UserDBId", parameters[6]);
            request.setAttribute("User", parameters[7]);
            request.setAttribute("Parent_Document_Id", parameters[3]);
            request.setAttribute("Parent_Document", parameters[4]);
            request.setAttribute("Child_Document", parameters[5]);

            request.setAttribute("Applicant_Type", parameters[2]);
            request.setAttribute("Application_Form_No", parameters[1]);
            request.setAttribute("ApsId", parameters[0]);
            request.setAttribute("UploadFlag", parameters[8]);
            request.setAttribute("Document_Folder_Path", request.getServletContext().getInitParameter("DOCUMENT_FOLDER"));

            rd.forward(request, response);
        //      out.println("</body>");
        //      out.println("</html>");
      }
      catch(FileUploadException ex) 
      {
            System.out.println("[SRVFileUpload] (doPost) ----- File too large. Upload limit is 250kb ----- >"+ex);
            SRVUtil.printOut("[SRVFileUpload] (doPost) ------ File too large. Upload limit is 250kb ----- >"+ex);
            RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
            request.setAttribute("STATUS", "FileTooLarge");
            rd.forward(request, response);
      }
      catch(Exception e)
      {
          System.out.println("[SRVFileUpload] (doPost) ----- Exception ------ > "+SRVUtil.getExcpStackTrace(e));
          SRVUtil.printOut("[SRVFileUpload] (doPost) ----- Exception ------ > "+SRVUtil.getExcpStackTrace(e));
          RequestDispatcher rd = request.getRequestDispatcher("OD.jsp");
          request.setAttribute("STATUS", "ERROR");
          rd.forward(request, response);
      }
   }
   public void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        
        throw new ServletException("GET method used with " +
                getClass( ).getName( )+": POST method required.");
   }

}
