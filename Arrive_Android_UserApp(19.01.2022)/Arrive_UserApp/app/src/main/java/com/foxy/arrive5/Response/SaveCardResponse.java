package com.foxy.arrive5.Response;

/**
 * Created by parangat on 12/11/18.
 */

public class SaveCardResponse {

    /**
     * result : true
     * msg : Card save successfully
     * stripe_user_id : cus_DsfJbTTbfrJ312
     */

    private String result;
    private String msg;
    private String stripe_user_id;

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public String getStripe_user_id() {
        return stripe_user_id;
    }
}
