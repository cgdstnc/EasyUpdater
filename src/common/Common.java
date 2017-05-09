/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Administrator
 */
public class Common {

    public static String getTxtRecord(String hostName) {
        // Get the first TXT record
        java.util.Hashtable<String, String> env = new java.util.Hashtable<String, String>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");

        try {
            javax.naming.directory.DirContext dirContext = new javax.naming.directory.InitialDirContext(env);
            javax.naming.directory.Attributes attrs = dirContext.getAttributes(hostName, new String[]{"TXT"});
            javax.naming.directory.Attribute attr = attrs.get("TXT");

            String txtRecord = "";

            if (attr != null) {
                txtRecord = attr.get().toString();
            }

            return txtRecord;

        } catch (javax.naming.NamingException e) {

            e.printStackTrace();
            return "";
        }
    }

    public static void terminateProcess(String port) {

        String[] args = {"netstat", "-a", "-n", "-o"};
        Object[] ret = startProcess(args);
        BufferedReader in = (BufferedReader) ret[0];
        BufferedReader err = (BufferedReader) ret[1];
        Process p = (Process) ret[2];

        try {
            String s = null;
            while ((s = in.readLine()) != null) {
                if (s.contains(":" + port)) {
                    System.out.println("< terminateProcess > " + s);
                    try {
                        String pid = s.split("LISTENING")[1].replaceAll(" ", "");
                        System.out.println(pid);
                        String[] args2 = {"taskkill", "/pid", pid, "/F"};

                        Object[] ret0 = startProcess(args2);
                        BufferedReader in0 = (BufferedReader) ret0[0];
                        BufferedReader err0 = (BufferedReader) ret0[1];

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String s0 = null;
                                    while ((s0 = in0.readLine()) != null) {
                                        System.out.println("< taskkill > " + s0);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }).start();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String s0 = null;
                                    while ((s0 = err0.readLine()) != null) {
                                        System.out.println("< taskkill >[Error] " + s0);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }).start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = null;
                    while ((s = err.readLine()) != null) {
                        System.out.println("< terminateProcess >[Error] " + s);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            p.waitFor(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * return[0] -> stdInputStream return[1] -> errInputStream return[2]
     * ->Process
     */
    public static Object[] startProcess(String... command) {
        Object[] ret = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(command); //"java", "-version"
            pb.redirectErrorStream(true);
            Process proc = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            ret = new Object[3];
            ret[0] = stdInput;
            ret[1] = stdError;
            ret[2] = proc;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static byte[] keyBytes = "my_ideal_way_mts".getBytes(); // Anahtar 16 Byte olmak zorunda
    private static byte[] ivBytes = "metasoft_yazilim".getBytes(); // Anahtar 16 Byte olmak zorunda
    private static final String algoritma = "AES";

    private static String encryptString_V2(String text) {
        if (text != null && !text.equals("") && !text.equals("0")) {
            try {
                /**
                 * Oncelikle TEXT verisinin HARF ve NUMARA harici tum OZEL
                 * karakterleri sifrelenir
                 */
                text = specialCharacterEncrypt(text);

                /**
                 * STANDART Sifreleme islemi gerceklestirilir
                 */
                Key secretKey = new SecretKeySpec(keyBytes, algoritma);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
                String encryptedValue = Base64.encodeBase64String(cipher.doFinal((text.getBytes())));
                return encryptedValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String decryptString_V2(String encryptedText) {
        String decryptedValue = null;
        Boolean tryOldDecryptedFunction = false;
        try {
            /**
             * STANDART Sifre Cozme islemi gerceklestirilir
             */
            Key secretKey = new SecretKeySpec(keyBytes, algoritma);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
            decryptedValue = new String(cipher.doFinal(Base64.decodeBase64(encryptedText)));

            /**
             * Sonrasin da TEXT verisinin HARF ve NUMARA harici tum OZEL
             * karakterlerinin sifresi cozulur
             */
            decryptedValue = specialCharacterDecrypt(decryptedValue);
            return decryptedValue;
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            tryOldDecryptedFunction = true;
        }

        return null;
    }

    private static String specialCharacterEncrypt(String text) {
        String result = "";
        if (text != null && !text.equals("") && !text.equals("0") && text.length() > 0) {
            try {
                for (int x = 0; x < text.length(); x++) {
                    char tmpChar = text.charAt(x);

                    Boolean isLetterOrNumber = isLetterOrNumber(tmpChar);

                    if (isLetterOrNumber) {
                        String tmpCharStr = Character.toString(tmpChar);
                        result = result + tmpCharStr;
                    } else {
                        int asciiNumber = (int) tmpChar;
                        result = result + "~S" + Integer.toString(asciiNumber) + "E~";
                    }

                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }

    private static String specialCharacterDecrypt(String text) {
        String result = "";
        if (text != null && !text.equals("") && !text.equals("0") && text.length() > 0) {
            try {
                Pattern asciEncryptPattern = Pattern.compile("~S(.*?)E~");
                Matcher matcher = asciEncryptPattern.matcher(text);

                while (matcher.find()) {
                    String encryptedAsciiChar = matcher.group(1);
                    String decreptedChar = Character.toString((char) Integer.parseInt(encryptedAsciiChar));
                    text = text.replaceAll("~S" + encryptedAsciiChar + "E~", decreptedChar);
                }

                return text;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }

    private static Boolean isLetterOrNumber(char prmChar) {
        String charStr = Character.toString(prmChar);
        return charStr.matches("[a-zA-Z0-9]");
    }

}
