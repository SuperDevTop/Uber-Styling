package com.foxy.arrive5.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 5/18/18.
 */

public class BookingListResponse {
    @SerializedName("result")
    @Expose
    private List<BookingList> result = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<BookingList> getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
