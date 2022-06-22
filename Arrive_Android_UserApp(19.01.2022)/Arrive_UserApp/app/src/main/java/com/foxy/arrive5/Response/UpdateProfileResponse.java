package com.foxy.arrive5.Response;

/**
 * Created by parangat on 6/15/18.
 */

public class UpdateProfileResponse {

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public static class ResultBean {
        private String id;
        private String email;
        private String first_name;
        private String last_name;
        private String password;
        private String img;
        private String mobile;
        private String appPlatform;
        private String latitude;
        private String longitude;
        private String city;
        private String fav_music;
        private String about_me;
        private String code;
        private String online;
        private String status;
        private String added_on;
        private String invite_code;
        private String invited_by;
        private String token;
        private String total_points;
        private String used_point;
        private String points_available;
        private String cancelled_point;
        private String join_date;

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getPassword() {
            return password;
        }

        public String getImg() {
            return img;
        }

        public String getMobile() {
            return mobile;
        }

        public String getAppPlatform() {
            return appPlatform;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getCity() {
            return city;
        }

        public String getFav_music() {
            return fav_music;
        }

        public String getAbout_me() {
            return about_me;
        }

        public String getCode() {
            return code;
        }

        public String getOnline() {
            return online;
        }

        public String getStatus() {
            return status;
        }

        public String getAdded_on() {
            return added_on;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public String getInvited_by() {
            return invited_by;
        }

        public String getToken() {
            return token;
        }

        public String getTotal_points() {
            return total_points;
        }

        public String getUsed_point() {
            return used_point;
        }

        public String getPoints_available() {
            return points_available;
        }

        public String getCancelled_point() {
            return cancelled_point;
        }

        public String getJoin_date() {
            return join_date;
        }
    }
}
