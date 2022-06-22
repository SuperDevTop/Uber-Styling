package com.foxy.arrive5.Response;

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

        public void setRatingOneList(List<List<RatingComments>> ratingOneList) {
            this.ratingOneList = ratingOneList;
        }

        public List<List<RatingComments>> getRatingTwoList() {
            return ratingTwoList;
        }

        public void setRatingTwoList(List<List<RatingComments>> ratingTwoList) {
            this.ratingTwoList = ratingTwoList;
        }

        public List<List<RatingComments>> getRatingThreeList() {
            return ratingThreeList;
        }

        public void setRatingThreeList(List<List<RatingComments>> ratingThreeList) {
            this.ratingThreeList = ratingThreeList;
        }

        public List<List<RatingComments>> getRatingFourList() {
            return ratingFourList;
        }

        public void setRatingFourList(List<List<RatingComments>> ratingFourList) {
            this.ratingFourList = ratingFourList;
        }

        public List<List<RatingComments>> getRatingFiveList() {
            return ratingFiveList;
        }

        public void setRatingFiveList(List<List<RatingComments>> ratingFiveList) {
            this.ratingFiveList = ratingFiveList;
        }
    }


}