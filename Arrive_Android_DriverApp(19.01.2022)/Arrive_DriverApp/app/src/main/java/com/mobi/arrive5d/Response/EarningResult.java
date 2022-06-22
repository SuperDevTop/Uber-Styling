package com.mobi.arrive5d.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 6/18/18.
 */

public class EarningResult {
    @SerializedName("bookinglist")
    @Expose
    private List<Bookinglist> bookinglist = null;
    @SerializedName("totalride")
    @Expose
    private Integer totalride;
    @SerializedName("totalearnamount")
    @Expose
    private Integer totalearnamount;
    @SerializedName("totaltime")
    @Expose
    private String totaltime;

    public List<Bookinglist> getBookinglist() {
        return bookinglist;
    }

    public Integer getTotalride() {
        return totalride;
    }

    public Integer getTotalearnamount() {
        return totalearnamount;
    }

    public String getTotaltime() {
        return totaltime;
    }
}
