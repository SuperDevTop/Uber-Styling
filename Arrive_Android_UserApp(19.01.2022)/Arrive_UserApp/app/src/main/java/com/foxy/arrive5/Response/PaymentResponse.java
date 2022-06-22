package com.foxy.arrive5.Response;

public class PaymentResponse {

    /**
     * tran_id : 465
     * result : true
     * msg : Payment made successfully.
     */

    private int tran_id;
    private boolean result;
    private String msg;

    public int getTran_id() {
        return tran_id;
    }

    public void setTran_id(int tran_id) {
        this.tran_id = tran_id;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
