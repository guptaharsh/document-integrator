
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
public class SRVTestFilePath {

    public static void main(String args[])
            throws java.io.IOException {
        // Check that we have a file upload request
        System.out.println("<---------------- INSIDE Post ----------------->");

        String path_ORG = "/istreams/abc";
        String path = path_ORG.replace("/", File.separator).replace("\\", File.separator);
        String filePath_Org = " ";
        String filePath = filePath_Org.replace("/", File.separator).replace("\\", File.separator);
        // String filePath = "img.tif";;
        String fileUpload1 = "";
        String fileUpload2 = "";
        String final_filePath = "";

        try {
            if (filePath_Org.trim().isEmpty()) {
                throw new IllegalStateException("InvalidFile");
            }
            

            if (filePath.lastIndexOf(File.separator) >= 0) {
                filePath = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
                System.out.println("Separating on (" + File.separator + ")------------- > '" + final_filePath + "'");
            }

            final_filePath = path + File.separator + filePath;

            System.out.println("filePath_Upload1 --------------- > " + final_filePath);
        } catch (Exception e) {
            System.out.println("Exception ------ > " + e.getMessage());
        }
    }
}
