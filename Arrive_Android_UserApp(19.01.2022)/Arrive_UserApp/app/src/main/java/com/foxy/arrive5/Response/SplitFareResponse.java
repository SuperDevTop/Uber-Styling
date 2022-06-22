package com.foxy.arrive5.Response;

public class SplitFareResponse {

    /**
     * split_fair : 4.00
     * message : Split Fare
     * status : true
     */

    private String split_fair;
    private String message;
    private String status;

    public String getSplit_fair() {
        return split_fair;
    }

    public void setSplit_fair(String split_fair) {
        this.split_fair = split_fair;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
