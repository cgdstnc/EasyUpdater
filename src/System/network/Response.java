/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.network;

/**
 * Created by Cagdas Tunca on 12.01.2017.
 */

public class Response {

    private String responseTime;
    private int responseCode;
    private String responseRaw;

    private Request originalRequest;

    public Response(int responseCode, String responseRaw, Request originalRequest) {
        this.responseCode = responseCode;
        this.responseRaw = responseRaw;
        this.originalRequest = originalRequest;
        this.responseTime = String.valueOf(System.currentTimeMillis());
    }

    public Response(String responseTime, int responseCode, String responseRaw, Request originalRequest) {
        this.responseTime = responseTime;
        this.responseCode = responseCode;
        this.responseRaw = responseRaw;
        this.originalRequest = originalRequest;
    }

    //region GETTER-SETTER

    public String getResponseTime() {
        return responseTime;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseRaw() {
        return responseRaw;
    }

    public Request getOriginalRequest() {
        return originalRequest;
    }

    @Override
    public String toString() {
        String str ="";
        if (originalRequest!=null){
            str=originalRequest.toString();
        }


        return "Response{" +
                "responseTime=" + responseTime +
                ", responseCode=" + responseCode +
                ", responseRaw='" + responseRaw + '\'' +
                ", originalRequest=" + str +
                '}';
    }
    //endregion
}
