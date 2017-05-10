/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.network;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Cagdas Tunca on 12.01.2017.
 */

public class Request implements Serializable{

    public enum Method{
        GET("GET"),
        POST("POST");

        private String name;

        Method(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private int apiId;
    private String apiName;
    private int callerId;
    private String callerName;
    private String requestMethod;
    private String requestJson;

    private String requestURL;
    private String requestTime;


    private String URL;
    private Map<String,String> urlParameters;
    private Object objParameter;
    private Map<String, String> requestHeaders;


//    @Deprecated
//    public Request(int apiId, String apiName, Integer callerId, String callerName,Method requestMethod, String URL, Map<String, String> urlParameters, Object objParameter, Map<String, String> requestHeaders) {
//        this.apiId = apiId;
//        this.apiName = apiName;
//        this.callerId = callerId;
//        this.callerName = callerName;
//        this.requestMethod = requestMethod.getName();
//        this.URL = URL;
//        this.urlParameters = urlParameters;
//        this.objParameter = objParameter;
//        this.requestHeaders = requestHeaders;
//
//        this.requestURL = this.URL+ getFormattedUrlParameters();
//        this.requestTime = String.valueOf(System.currentTimeMillis());
//        this.requestJson = objParamToJson();
//    }
//
//    public Request(int apiId, String apiName, Integer callerId, String callerName, String requestMethod, String requestJson, String URL, Map<String, String> urlParameters, Map<String, String> requestHeaders) {
//        this.apiId = apiId;
//        this.apiName = apiName;
//        this.callerId = callerId;
//        this.callerName = callerName;
//        this.requestMethod = requestMethod;
//        this.requestJson = requestJson;
//        this.URL = URL;
//        this.urlParameters = urlParameters;
//        this.requestHeaders = requestHeaders;
//
//        this.requestURL = this.URL+ getFormattedUrlParameters();
//        this.requestTime = String.valueOf(System.currentTimeMillis());
//    }
//
//    public Request(int apiId, String apiName, int callerId, String callerName, String requestMethod, String requestJson, String requestURL, String requestTime, Map<String, String> requestHeaders) {
//        this.apiId = apiId;
//        this.apiName = apiName;
//        this.callerId = callerId;
//        this.callerName = callerName;
//        this.requestMethod = requestMethod;
//        this.requestJson = requestJson;
//        this.requestURL = requestURL;
//        this.requestTime = requestTime;
//        this.requestHeaders = requestHeaders;
//    }

    
    
    
    private String getFormattedUrlParameters(){
        String params="";

        if (urlParameters!=null){
            try {
                params+="?";
                for (Map.Entry<String,String> entry : urlParameters.entrySet()){
                    params+=entry.getKey()+"="+java.net.URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8");
                    params+="&";
                }
                params = params.substring(0,params.length()-1);//Remove last &
            }catch (Exception e){
                e.printStackTrace();
                params="";
            }
        }
        return params;
    }

    private String objParamToJson(){
        return objParameter==null ? "" : new Gson().toJson(objParameter,objParameter.getClass());
    }

    //region GETTER-SETTER

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    @Override
    public String toString() {
        String str="null";
        if (requestHeaders!=null){
            str=requestHeaders.toString();
        }
        return "Request{" +
                "apiId=" + apiId +
                ", apiName='" + apiName + '\'' +
                ", callerId=" + callerId +
                ", callerName='" + callerName + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestJson='" + requestJson + '\'' +
                ", requestURL='" + requestURL + '\'' +
                ", requestTime=" + requestTime +
                ", URL='" + URL + '\'' +
                ", urlParameters=" + urlParameters +
                ", objParameter=" + objParameter +
                ", requestHeaders=" + str +
                '}';
    }

    //endregion
}
