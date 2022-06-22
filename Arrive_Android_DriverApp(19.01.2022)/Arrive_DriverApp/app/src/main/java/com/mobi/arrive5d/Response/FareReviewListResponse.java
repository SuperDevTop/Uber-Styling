package com.mobi.arrive5d.Response;

import java.util.List;

/**
 * Created by parangat on 16/8/18.
 */

public class FareReviewListResponse {
    private String status;
    private String message;
    private List<ResultBean> result;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public static class ResultBean {
        private String id;
        private String type;

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

    }
}
