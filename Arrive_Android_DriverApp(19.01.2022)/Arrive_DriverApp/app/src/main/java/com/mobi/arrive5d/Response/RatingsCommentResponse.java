package com.mobi.arrive5d.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by parangat on 5/2/18.
 */

public class RatingsCommentResponse {
    private String message;
    private ResultBean result;
    private String status;

    public String getMessage() {
        return message;
    }

    public ResultBean getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public static class ResultBean {
        @SerializedName("1")
        private List<List<RatingComments>> ratingOneList;
        @SerializedName("2")
        private List<List<RatingComments>> ratingTwoList;
        @SerializedName("3")
        private List<List<RatingComments>> ratingThreeList;
        @SerializedName("4")
        private List<List<RatingComments>> ratingFourList;
        @SerializedName("5")
        private List<List<RatingComments>> ratingFiveList;

        public ResultBean() {
        }

        public List<List<RatingComments>> getRatingOneList() {
            return ratingOneList;
        }

        public List<List<RatingComments>> getRatingTwoList() {
            return ratingTwoList;
        }

        public List<List<RatingComments>> getRatingThreeList() {
            return ratingThreeList;
        }

        public List<List<RatingComments>> getRatingFourList() {
            return ratingFourList;
        }

        public List<List<RatingComments>> getRatingFiveList() {
            return ratingFiveList;
        }

    }

}
