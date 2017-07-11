
package com.servo.aps.security;

import com.servo.util.SRVUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import sun.misc.BASE64Encoder;
//import sun.misc.BASE64Encoder;


public class Authenticate {
    
    private static final byte bufferByte = (byte) 0x99;
    
    public String validateUser(String security, String credential, String strEncoding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        String base64 = "";
        try
        {
            SRVUtil.printOut("INSIDE -------------- Authenticate -------------");
        
            Key key =  generateKey(security);
            byte[] buffer = null , ciphertext = null;
            BASE64Encoder encoder = new BASE64Encoder();
            
            // create and initialize the encryption engine
                        Cipher cipher = Cipher.getInstance("DESede");
                        cipher.init(Cipher.ENCRYPT_MODE, key);                  
                        //buffer = credential.getBytes(characterEncoding);
                        
                        buffer = credential.getBytes(strEncoding);
                        //buffer = credential.getBytes("ASCII");
                        ciphertext = cipher.doFinal(buffer);
                        base64 = encoder.encode(ciphertext);
            SRVUtil.printOut("[ Authenticate ]  validateUser() -------------- > base64 == "+base64);            
            System.out.println("[ Authenticate ]  validateUser() -------------- > base64 == "+base64);            
        }
        catch(Exception ex)
        {
            System.out.println("[ Authenticate ]  validateUser() -------------- > EXCEPTION : "+SRVUtil.getExcpStackTrace(ex));
            SRVUtil.printOut("[ Authenticate ]  validateUser() -------------- > EXCEPTION : "+SRVUtil.getExcpStackTrace(ex));
            SRVUtil.printErr("[ Authenticate ]  validateUser() -------------- > EXCEPTION : "+SRVUtil.getExcpStackTrace(ex));
        }
        catch(Error e)
        {
            SRVUtil.printOut("[ Authenticate ]  validateUser() -------------- > ERROR : "+SRVUtil.getErrorStackTrace(e));
            System.out.println("[ Authenticate ]  validateUser() -------------- > ERROR : "+SRVUtil.getErrorStackTrace(e));
            SRVUtil.printErr("[ Authenticate ]  validateUser() -------------- > ERROR : "+SRVUtil.getErrorStackTrace(e));
            
        }
                        return base64;
    }
    
    private SecretKey generateKey(String textKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        
            SRVUtil.printOut("[ Authenticate ]  validateUser() -------------- > textKey == "+textKey);   
            System.out.println("[ Authenticate ]  validateUser() -------------- > textKey ---------- > "+textKey);
            byte[] keyBytes = new byte[24];
            byte[] tmpBytes = textKey.getBytes();
            SRVUtil.printOut("[ Authenticate ]  validateUser() -------------- > tmpBytes.length == "+tmpBytes.length);
            System.out.println("[ Authenticate ]  validateUser() -------------- > tmpBytes.length == "+tmpBytes.length);
            for (int i = 0; i < 24; i++)
            {
                try
                {
                    
                    if (i < tmpBytes.length) 
                    {
                        keyBytes[i] = tmpBytes[i];
                    }
                    else
                    {
                        keyBytes[i] = bufferByte;
                    }
                }
                catch(Error e)
                {
                    SRVUtil.printOut("[ Authenticate ]  validateUser()  ERROR : -------------- > "+SRVUtil.getErrorStackTrace(e));
                    SRVUtil.printErr("[ Authenticate ]  validateUser()  ERROR : -------------- > "+SRVUtil.getErrorStackTrace(e));
                    System.out.println("[ Authenticate ]  validateUser()    ERROR : -------------- > "+SRVUtil.getErrorStackTrace(e));
                }
                catch(Exception ex)
                {
                    SRVUtil.printOut("[ Authenticate ]  validateUser()  EXCEPTION : -------------- > "+SRVUtil.getExcpStackTrace(ex));
                    SRVUtil.printErr("[ Authenticate ]  validateUser()  EXCEPTION : -------------- > "+SRVUtil.getExcpStackTrace(ex));
                    System.out.println("[ Authenticate ]  validateUser()    EXCEPTION : -------------- > "+SRVUtil.getExcpStackTrace(ex));
                }
            }

            DESedeKeySpec desKeySpec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory keyFac = SecretKeyFactory.getInstance("DESede");
            // generate key
            SRVUtil.printOut("[ Authenticate ]  validateUser() -------------- > Generated Secret Key == "+keyFac.generateSecret(desKeySpec));
            System.out.println("Generated Secret Key ---------- > "+keyFac.generateSecret(desKeySpec));
            return keyFac.generateSecret(desKeySpec);
        }
        
    }
    

