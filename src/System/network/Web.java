/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Cagdas Tunca on 12.01.2017.
 */
public class Web {

    private static final String COOKIES_HEADER = "DEAL-Cookie";
    private static final String COOKIE = "DEALCookie";

    private static CookieManager msCookieManager = new CookieManager();

    private static int responseCode;

    public static void saveFile(final String filename, final String urlString) throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
            
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static Response send(Request request) {
        return request.getRequestMethod().equalsIgnoreCase(Request.Method.GET.getName()) ? sendGet(request) : sendPost(request);
    }

    public static void removeCookies() {
        msCookieManager.getCookieStore().removeAll();
    }

    private static Response sendPost(Request request) {

        String requestURL = request.getRequestURL();
        String jsonParams = request.getRequestJson();
        Map<String, String> requestProperties = request.getRequestHeaders();

        URL url;
        String raw = "";
        try {

            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("User-Agent", "MetasoftMobilService");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            //conn.setRequestProperty("Authorization","Basic " + Base64.encodeToString("uzman:123".getBytes(), Base64.NO_WRAP));

            if (requestProperties != null) {
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                //Salla cookie yi
//                conn.setRequestProperty(COOKIE,
//                        TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            }

            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            if (jsonParams != null) {
                writer.write(jsonParams);
            }
            writer.flush();
            writer.close();
            os.close();

            Map<String, List<String>> headerFields = conn.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            setResponseCode(conn.getResponseCode());

            if (getResponseCode() == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

//                String line;
//                while ((line = br.readLine()) != null) {
//                    response += line;
//                }
                char[] buf = new char[1024];
                StringBuilder stringBuilder = new StringBuilder();
                int charsRead;
                while ((charsRead = br.read(buf, 0, 1024)) > 0) {
                    stringBuilder.append(buf, 0, charsRead);
                }
                raw = stringBuilder.toString();
            } else {
                raw = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response(getResponseCode(), raw, request);
    }

    public static Response sendDelete(Request request) {

        String requestURL = request.getRequestURL();
        String jsonParams = request.getRequestJson();
        Map<String, String> requestProperties = request.getRequestHeaders();

        URL url;
        String raw = "";
        try {

            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("DELETE");

            conn.setRequestProperty("User-Agent", "MetasoftMobilService");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            //conn.setRequestProperty("Authorization","Basic " + Base64.encodeToString("uzman:123".getBytes(), Base64.NO_WRAP));

            if (requestProperties != null) {
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                //Salla cookie yi
//                conn.setRequestProperty(COOKIE,
//                        TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            }

            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            if (jsonParams != null) {
                writer.write(jsonParams);
            }
            writer.flush();
            writer.close();
            os.close();

            Map<String, List<String>> headerFields = conn.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            setResponseCode(conn.getResponseCode());

            if (getResponseCode() == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

//                String line;
//                while ((line = br.readLine()) != null) {
//                    response += line;
//                }
                char[] buf = new char[1024];
                StringBuilder stringBuilder = new StringBuilder();
                int charsRead;
                while ((charsRead = br.read(buf, 0, 1024)) > 0) {
                    stringBuilder.append(buf, 0, charsRead);
                }
                raw = stringBuilder.toString();
            } else {
                raw = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response(getResponseCode(), raw, request);
    }

    private static Response sendGet(Request request) {
        String url = request.getRequestURL();
        Map<String, String> requestProperties = request.getRequestHeaders();

        String raw = "";

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("User-Agent", "MetasoftMobil");

            if (requestProperties != null) {
                for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
                    con.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
//                Salla cookie yi
//                con.setRequestProperty(COOKIE,
//                        TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            }

            Map<String, List<String>> headerFields = con.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            setResponseCode(con.getResponseCode());

            if (getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                char[] buf = new char[1024];
                StringBuilder stringBuilder = new StringBuilder();
                int charsRead;
                while ((charsRead = in.read(buf, 0, 1024)) > 0) {
                    stringBuilder.append(buf, 0, charsRead);
                }
                raw = stringBuilder.toString();
            } else {
                raw = "" + getResponseCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response(getResponseCode(), raw, request);
    }

    private static int getResponseCode() {
        return responseCode;
    }

    private static void setResponseCode(int responseCode) {
        Web.responseCode = responseCode;
    }
}
