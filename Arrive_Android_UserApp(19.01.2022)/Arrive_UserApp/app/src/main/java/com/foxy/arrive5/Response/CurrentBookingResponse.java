package com.foxy.arrive5.Response;

import java.util.List;

public class CurrentBookingResponse {

    /**
     * message : booking list fetch successfully
     * status : true
     * booking : [{"id":"1340","booking_id":"6Z11GPEA","user_id":"163","driver_id":"115","transaction_id":"0","start_point":"A-78, Block A, Sector 4, Noida, Uttar Pradesh 201301, India","end_point":"Iffco Metro Station, Sector 29, Gurugram, Haryana","start_point_lat":"28.583022","start_point_long":"77.320605","end_point_lat":"28.471425","end_point_long":"77.07239","amount":"0","tax":"0","duration":"58 mins","distance":"37.8","promocode":"","no_passanger":"1","no_luggage":"0","mode":"1","cancel_reason":"","booking_type":"","schedule_date":"2018-12-20","schedule_time":"03:42:04","otp":"4805","vehicle_type_id":"1","vehicle_sub_type_id":"1","status":"1","added_on":"2018-12-20 23:01:55","update_on":"0000-00-00 00:00:00","l_lat":"","l_long":"","reject_by_driver":"114,120,122,124,125,","driverImg":"http://arrive5.pcthepro.com/uploads/drivers/f5b54cdd4886221a2d268d938c167614.jpg","driverName":"new test driver","driverEmail":"eriver@gmail.com","mobile":"+911111111111","driver_longitude":"77.3205622","driver_latitude":"28.5830612","carNo":"plate","userName":"Arpit Jain","userImg":"http://arrive5.pcthepro.com/uploads/users/746f6858399e973626dac35da5f3acb6.jpeg","vehicleSubTypeName":"Clipper","driver_rating":"","push_tag":""}]
     */

    private String message;
    private String status;
    private List<BookingBean> booking;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<BookingBean> getBooking() {
        return booking;
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
         * mode : 1
         * cancel_reason :
         * booking_type :
         * schedule_date : 2018-12-20
         * schedule_time : 03:42:04
         * otp : 4805
         * vehicle_type_id : 1
         * vehicle_sub_type_id : 1
         * status : 1
         * added_on : 2018-12-20 23:01:55
         * update_on : 0000-00-00 00:00:00
         * l_lat :
         * l_long :
         * reject_by_driver : 114,120,122,124,125,
         * driverImg : http://arrive5.pcthepro.com/uploads/drivers/f5b54cdd4886221a2d268d938c167614.jpg
         * driverName : new test driver
         * driverEmail : eriver@gmail.com
         * mobile : +911111111111
         * driver_longitude : 77.3205622
         * driver_latitude : 28.5830612
         * carNo : plate
         * userName : Arpit Jain
         * userImg : http://arrive5.pcthepro.com/uploads/users/746f6858399e973626dac35da5f3acb6.jpeg
         * vehicleSubTypeName : Clipper
         * driver_rating :
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
        private String mobile;
        private String driver_longitude;
        private String driver_latitude;
        private String carNo;
        private String userName;
        private String userImg;
        private String vehicleSubTypeName;
        private String driver_rating;
        private String push_tag;

        public String getId() {
            return id;
        }

        public String getBooking_id() {
            return booking_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public String getStart_point() {
            return start_point;
        }

        public String getEnd_point() {
            return end_point;
        }

        public String getStart_point_lat() {
            return start_point_lat;
        }

        public String getStart_point_long() {
            return start_point_long;
        }

        public String getEnd_point_lat() {
            return end_point_lat;
        }

        public String getEnd_point_long() {
            return end_point_long;
        }

        public String getAmount() {
            return amount;
        }

        public String getTax() {
            return tax;
        }

        public String getDuration() {
            return duration;
        }

        public String getDistance() {
            return distance;
        }

        public String getPromocode() {
            return promocode;
        }

        public String getNo_passanger() {
            return no_passanger;
        }

        public String getNo_luggage() {
            return no_luggage;
        }

        public String getMode() {
            return mode;
        }

        public String getCancel_reason() {
            return cancel_reason;
        }

        public String getBooking_type() {
            return booking_type;
        }

        public String getSchedule_date() {
            return schedule_date;
        }

        public String getSchedule_time() {
            return schedule_time;
        }

        public String getOtp() {
            return otp;
        }

        public String getVehicle_type_id() {
            return vehicle_type_id;
        }

        public String getVehicle_sub_type_id() {
            return vehicle_sub_type_id;
        }

        public String getStatus() {
            return status;
        }

        public String getAdded_on() {
            return added_on;
        }

        public String getUpdate_on() {
            return update_on;
        }

        public String getL_lat() {
            return l_lat;
        }

        public String getL_long() {
            return l_long;
        }

        public String getReject_by_driver() {
            return reject_by_driver;
        }

        public String getDriverImg() {
            return driverImg;
        }

        public String getDriverName() {
            return driverName;
        }

        public String getDriverEmail() {
            return driverEmail;
        }

        public String getMobile() {
            return mobile;
        }

        public String getDriver_longitude() {
            return driver_longitude;
        }

        public String getDriver_latitude() {
            return driver_latitude;
        }

        public String getCarNo() {
            return carNo;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserImg() {
            return userImg;
        }

        public String getVehicleSubTypeName() {
            return vehicleSubTypeName;
        }

        public String getDriver_rating() {
            return driver_rating;
        }

        public String getPush_tag() {
            return push_tag;
        }
    }
}
