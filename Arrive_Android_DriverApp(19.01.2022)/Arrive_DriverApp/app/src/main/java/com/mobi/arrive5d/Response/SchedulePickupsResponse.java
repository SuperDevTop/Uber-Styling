package com.mobi.arrive5d.Response;

import java.util.List;

/**
 * Created by parangat on 16/10/18.
 */

public class SchedulePickupsResponse {

    /**
     * scheduled_pickups : [{"id":"1028","booking_id":"K1B887T0","user_id":"132","driver_id":"0","transaction_id":"0","start_point":"Noida, Uttar Pradesh, India","end_point":"IFFCO Chowk, Sector 29, Gurugram, Haryana, India","start_point_lat":"28.5355161","start_point_long":"77.3910265","end_point_lat":"28.472120999999998","end_point_long":"77.0725106","amount":"137","tax":"0","duration":"1 hour 8 mins","distance":"45.6","promocode":"","no_passanger":"0","no_luggage":"0","mode":"5","cancel_reason":"","booking_type":"schedule later","schedule_date":"2018-10-16","schedule_time":"00:59:42","otp":"9726","vehicle_type_id":"0","vehicle_sub_type_id":"3","status":"1","added_on":"2018-10-16 00:59:42","update_on":"0000-00-00 00:00:00"}]
     * msg : Scheduled Pickups List
     * status : true
     */

    private String msg;
    private String status;
    private List<ScheduledPickupsBean> scheduled_pickups;

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public List<ScheduledPickupsBean> getScheduled_pickups() {
        return scheduled_pickups;
    }

    public static class ScheduledPickupsBean {
        /**
         * id : 1028
         * booking_id : K1B887T0
         * user_id : 132
         * driver_id : 0
         * transaction_id : 0
         * start_point : Noida, Uttar Pradesh, India
         * end_point : IFFCO Chowk, Sector 29, Gurugram, Haryana, India
         * start_point_lat : 28.5355161
         * start_point_long : 77.3910265
         * end_point_lat : 28.472120999999998
         * end_point_long : 77.0725106
         * amount : 137
         * tax : 0
         * duration : 1 hour 8 mins
         * distance : 45.6
         * promocode :
         * no_passanger : 0
         * no_luggage : 0
         * mode : 5
         * cancel_reason :
         * booking_type : schedule later
         * schedule_date : 2018-10-16
         * schedule_time : 00:59:42
         * otp : 9726
         * vehicle_type_id : 0
         * vehicle_sub_type_id : 3
         * status : 1
         * added_on : 2018-10-16 00:59:42
         * update_on : 0000-00-00 00:00:00
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

    }
}
