package com.foxy.arrive5.Response;

/**
 * Created by parangat on 4/6/18.
 */

public class LoginResponse {


    /**
     * msg : Successfully Login.
     * status : true
     * details : {"id":"163","stripe_user_id":"cus_E5KIU87m2JDhc8","email":"arpit.j@parangat.com","first_name":"Arpit","last_name":"Jain","password":"","img":"746f6858399e973626dac35da5f3acb6.jpeg","mobile":"+919149132848","appPlatform":"android","latitude":"28.5830536","longitude":"77.3205518","city":"","fav_music":"","about_me":"","code":"7878","online":"1","status":"1","added_on":"2018-12-06 05:29:36","invite_code":"UE67TWZ7","invited_by":"","token":"f1noUnIm3SE:APA91bHbZmS8pRxdVA9JfiWXz8y-Wfi4Vt2Y6J7kBtJ9XxWSx3M5luvklnJ43nyQ0fpTaXYZbPCKGsLW0OjgSNQiGJGaEiUxfIaOc1p7VIgDXkdWwJ6n_FtnJ802N9j5nxIZjrw7AQ1d","total_points":"0","used_point":"0","points_available":"0","cancelled_point":"0","join_date":"December 2018","image":"http://arrive5.pcthepro.com/uploads/users/746f6858399e973626dac35da5f3acb6.jpeg"}
     */

    private String msg;
    private String status;
    private DetailsBean details;

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

    public DetailsBean getDetails() {
        return details;
    }

    public void setDetails(DetailsBean details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * id : 163
         * stripe_user_id : cus_E5KIU87m2JDhc8
         * email : arpit.j@parangat.com
         * first_name : Arpit
         * last_name : Jain
         * password :
         * img : 746f6858399e973626dac35da5f3acb6.jpeg
         * mobile : +919149132848
         * appPlatform : android
         * latitude : 28.5830536
         * longitude : 77.3205518
         * city :
         * fav_music :
         * about_me :
         * code : 7878
         * online : 1
         * status : 1
         * added_on : 2018-12-06 05:29:36
         * invite_code : UE67TWZ7
         * invited_by :
         * token : f1noUnIm3SE:APA91bHbZmS8pRxdVA9JfiWXz8y-Wfi4Vt2Y6J7kBtJ9XxWSx3M5luvklnJ43nyQ0fpTaXYZbPCKGsLW0OjgSNQiGJGaEiUxfIaOc1p7VIgDXkdWwJ6n_FtnJ802N9j5nxIZjrw7AQ1d
         * total_points : 0
         * used_point : 0
         * points_available : 0
         * cancelled_point : 0
         * join_date : December 2018
         * image : http://arrive5.pcthepro.com/uploads/users/746f6858399e973626dac35da5f3acb6.jpeg
         */

        private String id;
        private String stripe_user_id;
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
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStripe_user_id() {
            return stripe_user_id;
        }

        public void setStripe_user_id(String stripe_user_id) {
            this.stripe_user_id = stripe_user_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAppPlatform() {
            return appPlatform;
        }

        public void setAppPlatform(String appPlatform) {
            this.appPlatform = appPlatform;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getFav_music() {
            return fav_music;
        }

        public void setFav_music(String fav_music) {
            this.fav_music = fav_music;
        }

        public String getAbout_me() {
            return about_me;
        }

        public void setAbout_me(String about_me) {
            this.about_me = about_me;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAdded_on() {
            return added_on;
        }

        public void setAdded_on(String added_on) {
            this.added_on = added_on;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getInvited_by() {
            return invited_by;
        }

        public void setInvited_by(String invited_by) {
            this.invited_by = invited_by;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTotal_points() {
            return total_points;
        }

        public void setTotal_points(String total_points) {
            this.total_points = total_points;
        }

        public String getUsed_point() {
            return used_point;
        }

        public void setUsed_point(String used_point) {
            this.used_point = used_point;
        }

        public String getPoints_available() {
            return points_available;
        }

        public void setPoints_available(String points_available) {
            this.points_available = points_available;
        }

        public String getCancelled_point() {
            return cancelled_point;
        }

        public void setCancelled_point(String cancelled_point) {
            this.cancelled_point = cancelled_point;
        }

        public String getJoin_date() {
            return join_date;
        }

        public void setJoin_date(String join_date) {
            this.join_date = join_date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
