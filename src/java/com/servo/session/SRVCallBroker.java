/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servo.session;

import com.servo.util.SRVUtil;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 * @author Administrator
 */
public class SRVCallBroker {

   
    //public static String makeCall(String strAppServerIp, int strAppServerPort, String inputXML) throws Exception {
    public static String makeCall(String strAPI, String inputXML) throws Exception {
        
        String outputXML = null;
        //<Protocol>://<IP>:<Port>/omnidocs/servlet/GenericCallBroker?InputXML= &JTSIp= &JTSPort=
        
        String strURL = SRVUtil.getSRVApplicationConfig().getServerConfig().getHttpProtocol() + "://" +
                        SRVUtil.getSRVApplicationConfig().getServerConfig().getHttpServerIp() + ":" +
                        SRVUtil.getSRVApplicationConfig().getServerConfig().getHttpServerPort()+"/omnidocs/servlet/GenericCallBroker?";
        
        String pUrl = strURL + "InputXML=" + encodeURIComponent(inputXML) + "&JTSIp=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerIp() + 
                                "&JTSPort=" + SRVUtil.getSRVApplicationConfig().getServerConfig().getAppServerPort();
       
        System.out.println("[SRVCallBroker]     makeCall()  GenericCallBroker URL ------------- > "+pUrl);
        SRVUtil.printOut("[SRVCallBroker]     makeCall()  GenericCallBroker URL ------------- > "+pUrl);
        SRVUtil.printErr("[SRVCallBroker]     makeCall()  GenericCallBroker URL ------------- > "+pUrl);
        
        try{
            SRVUtil.printXML("[SRVCallBroker]", inputXML);
            /*long callStartTime = System.currentTimeMillis();
            SRVUtil.printXML("[SRVCallBroker]", "Call started at : " + callStartTime);
            SRVUtil.printXML("[SRVCallBroker]", inputXML);
            SRVUtil.printOut(inputXML);
            SRVUtil.printErr(inputXML);
            printProperties(strAPI, " !!!!! BEFORE !!!!!");*/
            outputXML = callBroker(strAPI, pUrl);
             SRVUtil.printXML("[SRVCallBroker]", outputXML);
           /* printProperties(strAPI, " !!!!! AFTER !!!!!");
            long callEndTime = System.currentTimeMillis();
            SRVUtil.printXML("[SRVCallBroker]", " Call ended at : " + callEndTime);
            SRVUtil.printTransanction("[iSRVCallBroker]", strAPI, callStartTime, callEndTime, 0);
            SRVUtil.printXML("[SRVCallBroker]", outputXML);*/
        }
        catch(Exception e){
            SRVUtil.printErr("[SRVCallBroker] Exception in makeCall ------>");
            SRVUtil.printOut("[SRVCallBroker] Exception in makeCall ------>");
            SRVUtil.printErr(SRVUtil.getExcpStackTrace(e));
            throw new Exception (e);
        }
        catch(Error e){
            SRVUtil.printErr("[SRVCallBroker] Error in makeCall");
            SRVUtil.printOut("[SRVCallBroker] Error in makeCall");
            SRVUtil.printErr(SRVUtil.getErrorStackTrace(e));
            throw new Error (e);
        }
        return outputXML;
    }
    
    
    public static String encodeURIComponent(String component) {
        String result1 = null;
        try {
            result1 = URLEncoder.encode(component, "UTF-8").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\+", "%20").replaceAll("\\%27", "'").replaceAll("\\%21", "!").replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result1 = component;
        }

        return result1;
    }
    
    
    public static String callBroker(String strAPI, String url1) {

        URLConnection urlConnection = null;
        String sOutputXML = null;

        try {

           
            //SRVUtil.printXML("[SRVCallBroker]", "Call started at : " + callStartTime);
           // SRVUtil.printXML("[SRVCallBroker]", inputXML);
            //SRVUtil.printOut(inputXML);
            //SRVUtil.printErr(inputXML);
            printProperties(strAPI, " !!!!! BEFORE !!!!!");
            long urlStartTime = System.currentTimeMillis();
            URL url = new URL(url1);
            urlConnection = url.openConnection();
            System.out.println("[SRVCallBroker] callBroker() urlConnection ----->" + urlConnection);
            SRVUtil.printOut("[SRVCallBroker] callBroker() urlConnection ----->" + urlConnection);

            if (urlConnection instanceof HttpURLConnection) {
                //((HttpURLConnection) urlConnection).setRequestMethod("POST");
                ((HttpURLConnection) urlConnection).setRequestMethod("GET");
            } else {
                SRVUtil.printOut("[SRVCallBroker] Throwing error>> This connection is NOT an HttpUrlConnection connection");
                SRVUtil.printErr("[SRVCallBroker] Throwing error>> This connection is NOT an HttpUrlConnection connection");
                throw new Exception("[SRVCallBroker] This connection is NOT an HttpUrlConnection connection");
            }
            
            urlConnection.setDoOutput(true);
          
            urlConnection.setRequestProperty("Content-Type", "application/xml");
            long urlEndTime = System.currentTimeMillis();
            SRVUtil.printTransanction("[iSRVCallBroker]", "URLConn-" + strAPI , urlStartTime, urlEndTime, 0);
            System.out.println("[SRVCallBroker] getDoOutput()  ----->" + urlConnection.getDoOutput());
            SRVUtil.printOut("[SRVCallBroker] getDoOutput()  ----->" + urlConnection.getDoOutput());
             
            long callStartTime = System.currentTimeMillis();
            InputStream is = urlConnection.getInputStream();
            if (is != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte ba[] = new byte[1024];
                int iByteRead = 0;
                while ((iByteRead = is.read(ba, 0, ba.length)) != (-1)) {
                    baos.write(ba, 0, iByteRead);
                }
                baos.flush();
                is.close();
                sOutputXML = new String(baos.toByteArray(), "UTF-8");

                System.out.println("[SRVCallBroker] sOutputXML-->" + sOutputXML);
                SRVUtil.printOut("[SRVCallBroker] sOutputXML-->" + sOutputXML);

                is = null;
                baos.close();
                baos = null;
               
            
            long callEndTime = System.currentTimeMillis();
            printProperties(strAPI, " !!!!! AFTER !!!!!");
            SRVUtil.printXML("[SRVCallBroker]", " Call ended at : " + callEndTime);
            SRVUtil.printTransanction("[iSRVCallBroker]", strAPI, callStartTime, callEndTime, 0);
            SRVUtil.printXML("[SRVCallBroker]", sOutputXML);
            }

        } catch (EOFException eof) {
            SRVUtil.printOut("[SRVCallBroker] EOF>>" + eof.toString());
            SRVUtil.printErr("[SRVCallBroker] EOF>>" + eof.toString());
        } catch (Exception ex) {
            SRVUtil.printOut("[SRVCallBroker] Excp-->" + ex.toString());
            SRVUtil.printErr("[SRVCallBroker] Excp-->" + ex.toString());
            SRVUtil.printErr(SRVUtil.getExcpStackTrace(ex));
            //throw ex;
        } catch (Error er) {
            SRVUtil.printOut("[SRVCallBroker] Excp-->" + er.toString());
            SRVUtil.printErr("[SRVCallBroker] Excp-->" + er.toString());
            SRVUtil.printErr(SRVUtil.getErrorStackTrace(er));
            //throw er;
        } finally {
            if (urlConnection != null && urlConnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlConnection).disconnect();
                urlConnection = null;
            }

        }
        return (sOutputXML);

    }
    
    private static void printProperties( String strAPI, String strStatus){
        
     
        SRVUtil.printOut("*****************************************************System Properties Start API: "+ strStatus +" --- " +strAPI + " *****************************************************");
        SRVUtil.printErr("*****************************************************System Properties Start : "+ strStatus +" --- " +strAPI +" *****************************************************");
        System.out.println("*****************************************************System Properties Start : "+ strStatus +" --- " +strAPI +" *****************************************************");
        /*Iterator<Entry<Object, Object>> itrProp = System.getProperties().entrySet().iterator();
        while (itrProp.hasNext()) {
            Entry<Object, Object> entry = itrProp.next();
            SRVUtil.printOut(entry.getKey() + " = " + entry.getValue());
            SRVUtil.printErr(entry.getKey() + " = " + entry.getValue());
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }*/
        System.out.println("PROVIDER_URL = " + System.getProperty("java.naming.provider.url"));
        SRVUtil.printOut("PROVIDER_URL = " + System.getProperty("java.naming.provider.url"));
        SRVUtil.printErr("PROVIDER_URL = " + System.getProperty("java.naming.provider.url"));
        System.out.println("INITIAL_CONTEXT = " + System.getProperty("java.naming.factory.initial"));
        SRVUtil.printOut("INITIAL_CONTEXT = " + System.getProperty("java.naming.factory.initial"));
        SRVUtil.printErr("INITIAL_CONTEXT = " + System.getProperty("java.naming.factory.initial"));
        
        SRVUtil.printOut("*****************************************************System Properties End : "+ strStatus +" --- " +strAPI +" *****************************************************");
        SRVUtil.printErr("*****************************************************System Properties End : "+ strStatus +" --- " +strAPI +" *****************************************************");
        System.out.println("*****************************************************System Properties End : "+ strStatus +" --- " +strAPI +" *****************************************************");

    }
}
