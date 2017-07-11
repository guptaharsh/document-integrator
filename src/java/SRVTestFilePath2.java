


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;



public class SRVTestFilePath2 {

    
   
   public static void main(String args[])
              throws java.io.IOException {
      // Check that we have a file upload request
      System.out.println("<---------------- INSIDE Post ----------------->");
      
      String path ="/istreams/abc";
      String filePath_Org = "C:\\folder\\newfolder\\img.tif";
      String filePath = "C:/folder/newfolder/img.tif";
      String fileUpload1 = "";
      String fileUpload2 = "";

    try
      { 
          
          
          
          
          
            if( filePath.lastIndexOf("/") >= 0 || filePath.lastIndexOf(File.separator) >= 0 || filePath.lastIndexOf("\\") >= 0 )
            {
                System.out.println("IF ------- SRVFileUpload");
                try
                {
                     System.out.println("filePath :"+ filePath);
                     System.out.println("filePath.lastIndexOf(File.separator) :"+ filePath.lastIndexOf(File.separator));
                    fileUpload1 = filePath.substring( filePath.lastIndexOf(File.separator));
                    System.out.println("Separating on ("+File.separator+")------------- > '"+fileUpload1+"'");
                }
                catch(Exception e)
                {
                    System.out.println("Exception while using SEPARATOR (IF)---------------> "+e.getStackTrace());
                    try
                    {
                        fileUpload1 = filePath.substring( filePath.lastIndexOf("\\"));
                        System.out.println("Separating on ------------- > '\\' === "+fileUpload1);
                    }
                    catch(Exception exp)
                    {
                        fileUpload1 = filePath.substring( filePath.lastIndexOf("/"));
                        System.out.println("Separating on ------------- > '/' ==== "+fileUpload1);
                    }
                }
            }
            else
            {
            try
                {
                    fileUpload1 = filePath.substring( filePath.lastIndexOf(File.separator));
                    System.out.println("ELSE Separating on "+File.separator+" ------------- > '"+fileUpload1+"'");
                }
                catch(Exception e)
                {
                    System.out.println("Exception while using File.separator (ELSE)---------------> "+e.getStackTrace());
                    try
                        {
                            fileUpload1 = filePath.substring( filePath.lastIndexOf("\\")+1);
                            System.out.println("CATCH ELSE Separating on ------------- > '\\' == "+fileUpload1);
                        }
                        catch(Exception ex)
                        {
                            fileUpload1 = filePath.substring( filePath.lastIndexOf("/"));
                            System.out.println("CATCH ELSE Separating on ------------- > '/' == "+fileUpload1);
                        }
                }
            }
                    try
                        {
                            System.out.println("TRY "+filePath.substring(filePath.lastIndexOf(File.separator)+1));
                            fileUpload2 = filePath.substring(filePath.lastIndexOf(File.separator)+1);
                        }
                        catch(Exception e)
                        {
                            System.out.println("+1 Exception while using SEPARATOR FilePath_Upload ---------------> "+e.getStackTrace());
                            try
                            {
                                System.out.println("+1 Using SEPARATOR FilePath_Upload ---------------> '\\'");
                                fileUpload2 = filePath.substring(filePath.lastIndexOf("\\")+1);
                            }
                            catch(Exception ex)
                            {
                                System.out.println("+1 Using SEPARATOR FilePath_Upload ---------------> '/'");
                                fileUpload2 = filePath.substring(filePath.lastIndexOf("/")+1);
                            }
                        }



            System.out.println("filePath_Upload1 --------------- > "+fileUpload1);
            System.out.println("filePath_Upload2 --------------- > "+fileUpload2);
      }
      catch(Exception e)
      {
          System.out.println("Exception ------ > "+e.getMessage());
      }
   }

}
