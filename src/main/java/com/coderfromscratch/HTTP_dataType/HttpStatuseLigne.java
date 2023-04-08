package com.coderfromscratch.HTTP_dataType;

public class HttpStatuseLigne {
    String protcoleVersion ;
    String StatusCode;
    String statusMessage;


    public String getProtcoleVersion() {
        return protcoleVersion;
    }

    public void setProtcoleVersion(String protcoleVersion) {
        this.protcoleVersion = protcoleVersion;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    public String toString(){
        String statuseLigne=this.protcoleVersion+" "+this.StatusCode+" "+this.statusMessage+"\r\n";
        return statuseLigne;

     }
}
