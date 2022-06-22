package com.foxy.arrive5.Response;

import java.util.List;

/**
 * Created by parangat on 23/10/18.
 */

public class ContactListResponse {


    /**
     * contact_list : [{"id":"146","email":"arpit.j@gmail.com","first_name":"Arpit","last_name":"Jain","password":"1234","img":"815e78edb215e51c1631400947207214.jpeg","mobile":"+919149132847","appPlatform":"android","latitude":"28.583083","longitude":"77.3204829","city":"noida","fav_music":"","about_me":"","code":"5562","online":"1","status":"1","added_on":"2018-10-23 06:19:21","invite_code":"BCOK258H","invited_by":"","token":"cMm2Y9-vmhU:APA91bE2ARwtzJnvklwMcBm9Ys0Ib3jrnHDbBrMoIG00UNrpO9ToCE3jgvIeUhq40GWBPyAPkQHNKbxAARd2aIQa5XlbVFozGajjS4GPmu0EZu9WXpfDgM5TUKLq_YBo7AO1UDBZn12-","total_points":"0","used_point":"0","points_available":"0","cancelled_point":"0"}]
     * status : true
     * msg : contact list fetch successfully.
     */

    private String status;
    private String msg;
    private List<ContactListBean> contact_list;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<ContactListBean> getContact_list() {
        return contact_list;
    }

    public static class ContactListBean {
        /**
         * id : 146
         * email : arpit.j@gmail.com
         * first_name : Arpit
         * last_name : Jain
         * password : 1234
         * img : 815e78edb215e51c1631400947207214.jpeg
         * mobile : +919149132847
         * appPlatform : android
         * latitude : 28.583083
         * longitude : 77.3204829
         * city : noida
         * fav_music :
         * about_me :
         * code : 5562
         * online : 1
         * status : 1
         * added_on : 2018-10-23 06:19:21
         * invite_code : BCOK258H
         * invited_by :
         * token : cMm2Y9-vmhU:APA91bE2ARwtzJnvklwMcBm9Ys0Ib3jrnHDbBrMoIG00UNrpO9ToCE3jgvIeUhq40GWBPyAPkQHNKbxAARd2aIQa5XlbVFozGajjS4GPmu0EZu9WXpfDgM5TUKLq_YBo7AO1UDBZn12-
         * total_points : 0
         * used_point : 0
         * points_available : 0
         * cancelled_point : 0
         */

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

    }
}
