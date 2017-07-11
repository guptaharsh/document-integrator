
import com.servo.util.SRVUtil;
import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@WebListener()
public class SRVConfig implements ServletContextListener {

    String urlString, Odw, Apsw, Status, AppletToolbar, DocumentFolder, AddDocumentFolder, DataClass, GenericValue, GenericUserId, GenericPassword,
            NoOfRecords, ServletURL, AppletLibrary, JTSIP, JTSPort,
            //OmniServlet,
            ODLibrary, CabinetName, AddDocumentAbsPath, StampINIPath, InitialContextFactory, ChannelCode,
            ExternalCredentialServlet, ApsManualLogin, strSupportedFileFormats, strFileSize, strUserName, strPassword, WebApiUrl;
    String SupportedFileFormats[] = new String[100];

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {

            SAXParserFactory parserFact = SAXParserFactory.newInstance();
            SAXParser parser = parserFact.newSAXParser();

            DefaultHandler dHandler = new DefaultHandler() {

                boolean url;
                boolean od;
                boolean aps;
                boolean status;
                boolean appletToolbar;
                boolean documentFolder;
                boolean adddocumentFolder;
                boolean dataClass;
                boolean genericValue;
                boolean genericUserId;
                boolean genericPassword;
                boolean noOfRecords;
                boolean servletURL;
                boolean appletLibrary;
                //boolean omniservlet;
                boolean odLibrary;
                boolean cabinetName;
                boolean addDocumentAbsPath;
                boolean supportedFileFormats;
                boolean stampINIPath;
                boolean initialContextFactory;
                boolean channelCode;
                boolean externalCredentialServlet;
                boolean apsManualLogin;
                boolean fileSize;
                boolean bstrusername;
                boolean bstrpassword;
                boolean jtsip;
                boolean jtsport;
                boolean webapiurl;

                public void startElement(String uri, String localName,
                        String element_name, Attributes attributes) throws SAXException {
                    if (element_name.equals("apswindowdisplay")) {
                        status = true;
                    }
                    if (element_name.equals("url")) {
                        url = true;
                    }
                    if (element_name.equals("odw")) {
                        od = true;
                    }
                    if (element_name.equals("apsw")) {
                        aps = true;
                    }
                    if (element_name.equals("applettoolbar")) {
                        appletToolbar = true;
                    }
                    if (element_name.equals("documentfolder")) {
                        documentFolder = true;
                    }
                    if (element_name.equals("adddocumentfolder")) {
                        adddocumentFolder = true;
                    }
                    if (element_name.equals("dataclassname")) {
                        dataClass = true;
                    }
                    if (element_name.equals("genericvalue")) {
                        genericValue = true;
                    }
                    if (element_name.equals("genericuserid")) {
                        genericUserId = true;
                    }
                    if (element_name.equals("genericpassword")) {
                        genericPassword = true;
                    }
                    if (element_name.equals("noofrecords")) {
                        noOfRecords = true;
                    }
                    if (element_name.equals("servleturl")) {
                        servletURL = true;
                    }
                    if (element_name.equals("appletlibrary")) {
                        appletLibrary = true;
                    }
//                    if (element_name.equals("omniservlet")) {
//                        omniservlet = true;
//                    }
                    if (element_name.equals("odlibrary")) {
                        odLibrary = true;
                    }
                    if (element_name.equals("cabinetname")) {
                        cabinetName = true;
                    }
                    if (element_name.equals("webapiurl")) {
                        webapiurl = true;
                    }
                    if (element_name.equals("adddocumentabspath")) {
                        addDocumentAbsPath = true;
                    }
                    if (element_name.equals("supportedfileformats")) {
                        supportedFileFormats = true;
                    }
                    if (element_name.equals("stampinipath")) {
                        stampINIPath = true;
                    }
                    if (element_name.equals("initialcontextfactory")) {
                        initialContextFactory = true;
                    }
                    if (element_name.equals("channelcode")) {
                        channelCode = true;
                    }
                    if (element_name.equals("externalcredentialservlet")) {
                        externalCredentialServlet = true;
                    }
                    if (element_name.equals("apsmanuallogin")) {
                        apsManualLogin = true;
                    }
                    if (element_name.equals("filesize")) {
                        fileSize = true;
                    }
//                    if (element_name.equalsIgnoreCase("dmslogincredentials")){
//                        strUserName = attributes.getValue("username");
//                        System.out.println("strUserName==="+strUserName);
//                        strPassword = attributes.getValue("password");                        
//                        System.out.println("strPassword===="+strPassword);
//                    }
                    if (element_name.equals("dmsloginusername")) {
                        bstrusername = true;
                    }
                    if (element_name.equals("dmsloginpassword")) {
                        bstrpassword = true;
                    }

                    if (element_name.equals("jtsip")) {
                        jtsip = true;
                    }
                    if (element_name.equals("jtsport")) {
                        jtsport = true;
                    }

                }

                public void characters(char[] ch, int start, int len)
                        throws SAXException {
                    String str = new String(ch, start, len);
                    if (url) {
                        urlString = str;
                        url = false;
                    }
                    if (od) {
                        Odw = str;
                        od = false;
                    }
                    if (aps) {
                        Apsw = str;
                        aps = false;
                    }
                    if (status) {
                        Status = str;
                        status = false;
                    }
                    if (appletToolbar) {
                        AppletToolbar = str;
                        appletToolbar = false;
                    }
                    if (documentFolder) {
                        DocumentFolder = str;
                        documentFolder = false;
                    }
                    if (adddocumentFolder) {
                        AddDocumentFolder = str;
                        adddocumentFolder = false;
                    }
                    if (dataClass) {
                        DataClass = str;
                        dataClass = false;
                    }
                    if (genericValue) {
                        GenericValue = str;
                        genericValue = false;
                    }
                    if (genericUserId) {
                        GenericUserId = str;
                        genericUserId = false;
                    }
                    if (genericPassword) {
                        GenericPassword = str;
                        genericPassword = false;
                    }
                    if (noOfRecords) {
                        NoOfRecords = str;
                        noOfRecords = false;
                    }
                    if (servletURL) {
                        ServletURL = str;
                        servletURL = false;
                    }
                    if (appletLibrary) {
                        AppletLibrary = str;
                        appletLibrary = false;
                    }
//                    if (omniservlet) {
//                        OmniServlet = str;
//                        omniservlet = false;
//                    }
                    if (odLibrary) {
                        ODLibrary = str;
                        odLibrary = false;
                    }
                    if (cabinetName) {
                        CabinetName = str;
                        cabinetName = false;
                    }
                    if (webapiurl) {
                        WebApiUrl = str;
                        webapiurl = false;
                    }
                    if (addDocumentAbsPath) {
                        AddDocumentAbsPath = str;
                        addDocumentAbsPath = false;
                    }
                    if (supportedFileFormats) {
                        strSupportedFileFormats = str;
                        SupportedFileFormats = str.split(",");
                        System.out.println("Supported FileFormats are ----- > ");
                        SRVUtil.printOut("Supported FileFormats are ----- > ");
                        for (int i = 0; i < SupportedFileFormats.length; i++) {
                            System.out.println(SupportedFileFormats[i]);
                            SRVUtil.printOut(SupportedFileFormats[i]);
                        }
                        //SupportedFileFormats = fileFormats;
                        supportedFileFormats = false;
                    }
                    if (stampINIPath) {
                        StampINIPath = str;
                        stampINIPath = false;
                    }
                    if (initialContextFactory) {
                        InitialContextFactory = str;
                        initialContextFactory = false;
                    }
                    if (channelCode) {
                        ChannelCode = str;
                        channelCode = false;
                    }
                    if (externalCredentialServlet) {
                        ExternalCredentialServlet = str;
                        externalCredentialServlet = false;
                    }
                    if (apsManualLogin) {
                        ApsManualLogin = str;
                        apsManualLogin = false;
                    }
                    if (fileSize) {
                        strFileSize = str;
                        fileSize = false;
                    }
                    if (bstrusername) {
                        strUserName = str;
                        System.out.println("The user Name is==" + strUserName);
                        bstrusername = false;

                    }
                    if (bstrpassword) {
                        strPassword = str;
                        System.out.println("The user Password is==" + strPassword);
                        bstrpassword = false;
                    }
                    if (jtsip) {
                        JTSIP = str;
                        jtsip = false;
                    }
                    if (jtsport) {
                        JTSPort = str;
                        jtsport = false;
                    }


                }
            };

            //parser.parse("E:\\ApsOd\\src\\java\\Url.xml", dHandler);
//            File f = new File("TestFile.txt");
//            f.createNewFile();
//            System.out.println(f.getAbsolutePath());
            try {
                parser.parse(new File("SRVConfig/APS-ODConfig/SRVApsOdConfig.xml"), dHandler);
            } catch (Exception e) {
                System.out.println("In exception parser");
                parser.parse(new File("SRVConfig" + File.separator + "APS-ODConfig" + File.separator + "SRVApsOdConfig.xml"), dHandler);
            }


//  CODE for splitting on delimeter ('slashes')

            String documentPath = DocumentFolder;
            String[] arrObjectIndex;


            String delimiter = "/";

            arrObjectIndex = documentPath.split(delimiter);
            /*
             * print substrings
             */
            for (int i = 0; i < arrObjectIndex.length; i++) {
                System.out.println(arrObjectIndex[i]);
            }
//  CODE ENDS here            


            sce.getServletContext().setInitParameter("APS-URL", urlString);
            sce.getServletContext().setInitParameter("FILE_SIZE", strFileSize);
            sce.getServletContext().setInitParameter("OD-WIDTH", Odw);
            sce.getServletContext().setInitParameter("APS-WIDTH", Apsw);
            sce.getServletContext().setInitParameter("APS_WINDOW_DISPLAY", Status);
            sce.getServletContext().setInitParameter("APPLET_TOOLBAR", AppletToolbar);
            sce.getServletContext().setInitParameter("DOCUMENT_FOLDER", DocumentFolder);
            sce.getServletContext().setInitParameter("ADD_DOCUMENT_FOLDER", AddDocumentFolder);
            sce.getServletContext().setInitParameter("DATACLASS", DataClass);
            sce.getServletContext().setInitParameter("GENERICVALUE", GenericValue);
            sce.getServletContext().setInitParameter("GENERICUSERID", GenericUserId);
            sce.getServletContext().setInitParameter("GENERICPASSWORD", GenericPassword);
            sce.getServletContext().setInitParameter("NOOFRECORDS", NoOfRecords);
            sce.getServletContext().setInitParameter("SERVLETURL", ServletURL);
            sce.getServletContext().setInitParameter("APPLETLIBRARY", AppletLibrary);
            //sce.getServletContext().setInitParameter("OMNISERVLET", OmniServlet);
            sce.getServletContext().setInitParameter("ODLIBRARY", ODLibrary);
            sce.getServletContext().setInitParameter("CABINETNAME", CabinetName);
            sce.getServletContext().setInitParameter("WEBAPIURL", WebApiUrl);
            sce.getServletContext().setInitParameter("ADDDOCUMENTABSPATH", AddDocumentAbsPath);

            sce.getServletContext().setInitParameter("STAMPINIPATH", StampINIPath);

            sce.getServletContext().setInitParameter("INITIALCONTEXTFACTORY", InitialContextFactory);
            sce.getServletContext().setInitParameter("CHANNELCODE", ChannelCode);
            sce.getServletContext().setInitParameter("EXTERNALCREDENTIALSERVLET", ExternalCredentialServlet);
            sce.getServletContext().setInitParameter("APSMANUALLOGIN", ApsManualLogin);

            sce.getServletContext().setAttribute("SUPPORTEDFILEFORMATS", SupportedFileFormats);// array of string
            sce.getServletContext().setInitParameter("FILEFORMATS", strSupportedFileFormats);//string
            sce.getServletContext().setInitParameter("APSODPASSWORD", strPassword);
            sce.getServletContext().setInitParameter("APSODUSERNAME", strUserName);
            sce.getServletContext().setInitParameter("JTSIP", JTSIP);
            sce.getServletContext().setInitParameter("JTSPORT", JTSPort);
            //sce.getServletContext().setInitParameter("FILESIZE", FileSize);

            //sce.getServletContext().setInitParameter("DOCUMENT_FOLDER_ARRAY", arrObjectIndex);
            System.out.println("Servlet Context ---- > " + sce.getServletContext().getInitParameter("APS-URL")
                    + sce.getServletContext().getInitParameter("OD-WIDTH")
                    + sce.getServletContext().getInitParameter("FILEFORMATS")
                    + sce.getServletContext().getInitParameter("APS-WIDTH")
                    + sce.getServletContext().getInitParameter("APS_WINDOW_DISPLAY")
                    + sce.getServletContext().getInitParameter("SERVLETURL")
                    + sce.getServletContext().getInitParameter("APPLETLIBRARY")
                    + sce.getServletContext().getInitParameter("CABINETNAME")
                    + sce.getServletContext().getInitParameter("WEBAPIURL")
                    + sce.getServletContext().getInitParameter("ADDDOCUMENTABSPATH")
                    + sce.getServletContext().getAttribute("SUPPORTEDFILEFORMATS")
                    + sce.getServletContext().getInitParameter("STAMPINIPATH")
                    + //sce.getServletContext().getInitParameter("FILESIZE"));
                    sce.getServletContext().getInitParameter("APSMANUALLOGIN")
                    + sce.getServletContext().getInitParameter("EXTERNALCREDENTIALSERVLET")
                    + sce.getServletContext().getInitParameter("CHANNELCODE")
                    + sce.getServletContext().getInitParameter("APSODPASSWORD")
                    + sce.getServletContext().getInitParameter("APSODUSERNAME")
                    + sce.getServletContext().getInitParameter("INITIALCONTEXTFACTORY"));
            SRVUtil.printOut("Servlet Context ---- > " + sce.getServletContext().getInitParameter("APS-URL") + sce.getServletContext().getInitParameter("OD-WIDTH") + sce.getServletContext().getInitParameter("APS-WIDTH") + sce.getServletContext().getInitParameter("APS_WINDOW_DISPLAY") + sce.getServletContext().getInitParameter("SERVLETURL") + sce.getServletContext().getInitParameter("APPLETLIBRARY") + sce.getServletContext().getInitParameter("CABINETNAME") + sce.getServletContext().getInitParameter("ADDDOCUMENTABSPATH") + sce.getServletContext().getAttribute("SUPPORTEDFILEFORMATS") + sce.getServletContext().getInitParameter("STAMPINIPATH")
                    + sce.getServletContext().getInitParameter("FILESIZE"));
        } catch (Exception e) {
            System.out.println("[SRVConfig] characters()    , ERROR : XML File hasn't any elements ---- > " + SRVUtil.getExcpStackTrace(e));
            SRVUtil.printOut("[SRVConfig] characters()    , ERROR : XML File hasn't any elements");
            SRVUtil.printErr("[SRVConfig] characters()    , ERROR : XML File hasn't any elements ---- > " + SRVUtil.getExcpStackTrace(e));
            //e.printStackTrace();
        }




    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
