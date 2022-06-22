package com.mobi.arrive5d.Response;

import java.util.List;

/**
 * Created by parangat on 19/12/18.
 */

public class CurrentBookingResponse {

    /**
     * message : booking list fetch successfully
     * status : true
     * booking : [{"id":"1340","booking_id":"6Z11GPEA","user_id":"163","driver_id":"115","transaction_id":"0","start_point":"A-78, Block A, Sector 4, Noida, Uttar Pradesh 201301, India","end_point":"Iffco Metro Station, Sector 29, Gurugram, Haryana","start_point_lat":"28.583022","start_point_long":"77.320605","end_point_lat":"28.471425","end_point_long":"77.07239","amount":"0","tax":"0","duration":"58 mins","distance":"37.8","promocode":"","no_passanger":"1","no_luggage":"0","mode":"7","cancel_reason":"","booking_type":"","schedule_date":"2018-12-20","schedule_time":"03:42:04","otp":"4805","vehicle_type_id":"1","vehicle_sub_type_id":"1","status":"1","added_on":"2018-12-20 23:40:35","update_on":"0000-00-00 00:00:00","l_lat":"","l_long":"","reject_by_driver":"114,120,122,124,125,","driverImg":"http://arrive5.pcthepro.com/uploads/drivers/f5b54cdd4886221a2d268d938c167614.jpg","driverName":"new test driver","driverEmail":"eriver@gmail.com","userName":"Arpit Jain","userImg":"http://arrive5.pcthepro.com/uploads/users/746f6858399e973626dac35da5f3acb6.jpeg","vehicleSubTypeName":"Clipper","user_rating":"3.5","user_mobile":"+919149132848","push_tag":""}]
     */

    private String message;
    private String status;
    private List<BookingBean> booking;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BookingBean> getBooking() {
        return booking;
    }

    public void setBooking(List<BookingBean> booking) {
        this.booking = booking;
    }

    public static class BookingBean {
        /**
         * id : 1340
         * booking_id : 6Z11GPEA
         * user_id : 163
         * driver_id : 115
         * transaction_id : 0
         * start_point : A-78, Block A, Sector 4, Noida, Uttar Pradesh 201301, India
         * end_point : Iffco Metro Station, Sector 29, Gurugram, Haryana
         * start_point_lat : 28.583022
         * start_point_long : 77.320605
         * end_point_lat : 28.471425
         * end_point_long : 77.07239
         * amount : 0
         * tax : 0
         * duration : 58 mins
         * distance : 37.8
         * promocode :
         * no_passanger : 1
         * no_luggage : 0
         * mode : 7
         * cancel_reason :
         * booking_type :
         * schedule_date : 2018-12-20
         * schedule_time : 03:42:04
         * otp : 4805
         * vehicle_type_id : 1
         * vehicle_sub_type_id : 1
         * status : 1
         * added_on : 2018-12-20 23:40:35
         * update_on : 0000-00-00 00:00:00
         * l_lat :
         * l_long :
         * reject_by_driver : 114,120,122,124,125,
         * driverImg : http://arrive5.pcthepro.com/uploads/drivers/f5b54cdd4886221a2d268d938c167614.jpg
         * driverName : new test driver
         * driverEmail : eriver@gmail.com
         * userName : Arpit Jain
         * userImg : http://arrive5.pcthepro.com/uploads/users/746f6858399e973626dac35da5f3acb6.jpeg
         * vehicleSubTypeName : Clipper
         * user_rating : 3.5
         * user_mobile : +919149132848
         * push_tag :
         */

        private String id;
        private String booking_id;
        private String user_id;
        private String driver_id;
        private String transaction_id;
        private String start_point;
        private String end_point;
        private String start_point_lat;
        private String start_point_long;
        private String end_point_lat;
        private String end_point_long;
        private String amount;
        private String tax;
        private String duration;
        private String distance;
        private String promocode;
        private String no_passanger;
        private String no_luggage;
        private String mode;
        private String cancel_reason;
        private String booking_type;
        private String schedule_date;
        private String schedule_time;
        private String otp;
        private String vehicle_type_id;
        private String vehicle_sub_type_id;
        private String status;
        private String added_on;
        private String update_on;
        private String l_lat;
        private String l_long;
        private String reject_by_driver;
        private String driverImg;
        private String driverName;
        private String driverEmail;
        private String userName;
        private String userImg;
        private String vehicleSubTypeName;
        private String user_rating;
        private String user_mobile;
        private String push_tag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(String booking_id) {
            this.booking_id = booking_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getStart_point() {
            return start_point;
        }

        public void setStart_point(String start_point) {
            this.start_point = start_point;
        }

        public String getEnd_point() {
            return end_point;
        }

        public void setEnd_point(String end_point) {
            this.end_point = end_point;
        }

        public String getStart_point_lat() {
            return start_point_lat;
        }

        public void setStart_point_lat(String start_point_lat) {
            this.start_point_lat = start_point_lat;
        }

        public String getStart_point_long() {
            return start_point_long;
        }

        public void setStart_point_long(String start_point_long) {
            this.start_point_long = start_point_long;
        }

        public String getEnd_point_lat() {
            return end_point_lat;
        }

        public void setEnd_point_lat(String end_point_lat) {
            this.end_point_lat = end_point_lat;
        }

        public String getEnd_point_long() {
            return end_point_long;
        }

        public void setEnd_point_long(String end_point_long) {
            this.end_point_long = end_point_long;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPromocode() {
            return promocode;
        }

        public void setPromocode(String promocode) {
            this.promocode = promocode;
        }

        public String getNo_passanger() {
            return no_passanger;
        }

        public void setNo_passanger(String no_passanger) {
            this.no_passanger = no_passanger;
        }

        public String getNo_luggage() {
            return no_luggage;
        }

        public void setNo_luggage(String no_luggage) {
            this.no_luggage = no_luggage;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getCancel_reason() {
            return cancel_reason;
        }

        public void setCancel_reason(String cancel_reason) {
            this.cancel_reason = cancel_reason;
        }

        public String getBooking_type() {
            return booking_type;
        }

        public void setBooking_type(String booking_type) {
            this.booking_type = booking_type;
        }

        public String getSchedule_date() {
            return schedule_date;
        }

        public void setSchedule_date(String schedule_date) {
            this.schedule_date = schedule_date;
        }

        public String getSchedule_time() {
            return schedule_time;
        }

        public void setSchedule_time(String schedule_time) {
            this.schedule_time = schedule_time;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getVehicle_type_id() {
            return vehicle_type_id;
        }

        public void setVehicle_type_id(String vehicle_type_id) {
            this.vehicle_type_id = vehicle_type_id;
        }

        public String getVehicle_sub_type_id() {
            return vehicle_sub_type_id;
        }

        public void setVehicle_sub_type_id(String vehicle_sub_type_id) {
            this.vehicle_sub_type_id = vehicle_sub_type_id;
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

        public String getUpdate_on() {
            return update_on;
        }

        public void setUpdate_on(String update_on) {
            this.update_on = update_on;
        }

        public String getL_lat() {
            return l_lat;
        }

        public void setL_lat(String l_lat) {
            this.l_lat = l_lat;
        }

        public String getL_long() {
            return l_long;
        }

        public void setL_long(String l_long) {
            this.l_long = l_long;
        }

        public String getReject_by_driver() {
            return reject_by_driver;
        }

        public void setReject_by_driver(String reject_by_driver) {
            this.reject_by_driver = reject_by_driver;
        }

        public String getDriverImg() {
            return driverImg;
        }

        public void setDriverImg(String driverImg) {
            this.driverImg = driverImg;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getDriverEmail() {
            return driverEmail;
        }

        public void setDriverEmail(String driverEmail) {
            this.driverEmail = driverEmail;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getVehicleSubTypeName() {
            return vehicleSubTypeName;
        }

        public void setVehicleSubTypeName(String vehicleSubTypeName) {
            this.vehicleSubTypeName = vehicleSubTypeName;
        }

        public String getUser_rating() {
            return user_rating;
        }

        public void setUser_rating(String user_rating) {
            this.user_rating = user_rating;
        }

        public String getUser_mobile() {
            return user_mobile;
        }

        public void setUser_mobile(String user_mobile) {
            this.user_mobile = user_mobile;
        }

        public String getPush_tag() {
            return push_tag;
        }

        public void setPush_tag(String push_tag) {
            this.push_tag = push_tag;
        }
    }
}
