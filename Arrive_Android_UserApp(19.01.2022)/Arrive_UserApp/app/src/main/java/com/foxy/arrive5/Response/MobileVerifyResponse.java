package com.foxy.arrive5.Response;

/**
 * Created by parangat on 4/6/18.
 */

public class MobileVerifyResponse {

    /**
     * status : true
     * details : {"id":"1"}
     * msg : Mobile number exits
     */

    private String status;
    private DetailsBean details;
    private String message;
    /**
     * otp : 1756
     */

    private String otp;

    public String getStatus() {
        return status;
    }

    public DetailsBean getDetails() {
        return details;
    }

    public String getMsg() {
        return message;
    }

    public String getOtp() {
        return otp;
    }

    public static class DetailsBean {
        /**
         * id : 1
         */

        private String id;
        public String getId() {
            return id;
        }
    }
}
