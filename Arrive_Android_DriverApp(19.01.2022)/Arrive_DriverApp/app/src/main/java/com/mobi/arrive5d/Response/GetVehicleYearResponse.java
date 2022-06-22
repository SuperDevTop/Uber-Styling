package com.mobi.arrive5d.Response;

import java.util.List;

public class GetVehicleYearResponse {


    /**
     * detail : [{"year":"2008"},{"year":"2009"},{"year":"2010"},{"year":"2011"},{"year":"2012"},{"year":"2013"},{"year":"2014"},{"year":"2015"},{"year":"2016"},{"year":"2017"},{"year":"2018"}]
     * msg : successful
     * status : true
     */

    private String msg;
    private String status;
    private List<DetailBean> detail;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * year : 2008
         */

        private String year;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
