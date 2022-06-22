package com.foxy.arrive5.Response;

import java.util.List;

/**
 * Created by parangat on 4/10/18.
 */

public class GetProfileResponse {

    /**
     * message : Business profile list fetch successfully.
     * status : true
     * business_profile_list : [{"id":"73","user_id":"152","email":"dgh@xh.com","payment_method_type":"1","report_status":"1","type":"2","status":"1","created_at":"2018-11-12 06:15:28","cardList":[{"id":"card_1DVde7JEorJsMEvkcoS4nfNS","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":null,"address_zip_check":null,"brand":"Visa","country":"US","customer":"cus_DxYeKrhd0NGftO","cvc_check":"pass","dynamic_last4":null,"exp_month":9,"exp_year":2019,"fingerprint":"CrNeqNGIhK4Li8XM","funding":"credit","last4":"4242","metadata":[],"name":null,"tokenization_method":null},{"id":"card_1DVeK7JEorJsMEvkvAjaTga5","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":null,"address_zip_check":null,"brand":"Visa","country":"US","customer":"cus_DxYeKrhd0NGftO","cvc_check":"pass","dynamic_last4":null,"exp_month":2,"exp_year":2019,"fingerprint":"CrNeqNGIhK4Li8XM","funding":"credit","last4":"4242","metadata":[],"name":null,"tokenization_method":null}]}]
     */

    private String message;
    private String status;
    private List<BusinessProfileListBean> business_profile_list;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<BusinessProfileListBean> getBusiness_profile_list() {
        return business_profile_list;
    }

    public static class BusinessProfileListBean {
        /**
         * id : 73
         * user_id : 152
         * email : dgh@xh.com
         * payment_method_type : 1
         * report_status : 1
         * type : 2
         * status : 1
         * created_at : 2018-11-12 06:15:28
         * cardList : [{"id":"card_1DVde7JEorJsMEvkcoS4nfNS","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":null,"address_zip_check":null,"brand":"Visa","country":"US","customer":"cus_DxYeKrhd0NGftO","cvc_check":"pass","dynamic_last4":null,"exp_month":9,"exp_year":2019,"fingerprint":"CrNeqNGIhK4Li8XM","funding":"credit","last4":"4242","metadata":[],"name":null,"tokenization_method":null},{"id":"card_1DVeK7JEorJsMEvkvAjaTga5","object":"card","address_city":null,"address_country":null,"address_line1":null,"address_line1_check":null,"address_line2":null,"address_state":null,"address_zip":null,"address_zip_check":null,"brand":"Visa","country":"US","customer":"cus_DxYeKrhd0NGftO","cvc_check":"pass","dynamic_last4":null,"exp_month":2,"exp_year":2019,"fingerprint":"CrNeqNGIhK4Li8XM","funding":"credit","last4":"4242","metadata":[],"name":null,"tokenization_method":null}]
         */

        private String id;
        private String user_id;
        private String email;
        private String payment_method_type;
        private String report_status;
        private String type;
        private String status;
        private String created_at;
        private List<CardListBean> cardList;

        public String getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getEmail() {
            return email;
        }

        public String getPayment_method_type() {
            return payment_method_type;
        }

        public String getReport_status() {
            return report_status;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public List<CardListBean> getCardList() {
            return cardList;
        }

        public static class CardListBean {
            /**
             * id : card_1DVde7JEorJsMEvkcoS4nfNS
             * object : card
             * address_city : null
             * address_country : null
             * address_line1 : null
             * address_line1_check : null
             * address_line2 : null
             * address_state : null
             * address_zip : null
             * address_zip_check : null
             * brand : Visa
             * country : US
             * customer : cus_DxYeKrhd0NGftO
             * cvc_check : pass
             * dynamic_last4 : null
             * exp_month : 9
             * exp_year : 2019
             * fingerprint : CrNeqNGIhK4Li8XM
             * funding : credit
             * last4 : 4242
             * metadata : []
             * name : null
             * tokenization_method : null
             */

            private String id;
            private String object;
            private Object address_city;
            private Object address_country;
            private Object address_line1;
            private Object address_line1_check;
            private Object address_line2;
            private Object address_state;
            private Object address_zip;
            private Object address_zip_check;
            private String brand;
            private String country;
            private String customer;
            private String cvc_check;
            private Object dynamic_last4;
            private int exp_month;
            private int exp_year;
            private String fingerprint;
            private String funding;
            private String last4;
            private Object name;
            private Object tokenization_method;
            private List<?> metadata;

            public String getId() {
                return id;
            }

            public String getObject() {
                return object;
            }

            public Object getAddress_city() {
                return address_city;
            }

            public Object getAddress_country() {
                return address_country;
            }

            public Object getAddress_line1() {
                return address_line1;
            }

            public Object getAddress_line1_check() {
                return address_line1_check;
            }

            public Object getAddress_line2() {
                return address_line2;
            }

            public Object getAddress_state() {
                return address_state;
            }

            public Object getAddress_zip() {
                return address_zip;
            }

            public Object getAddress_zip_check() {
                return address_zip_check;
            }

            public String getBrand() {
                return brand;
            }

            public String getCountry() {
                return country;
            }

            public String getCustomer() {
                return customer;
            }

            public String getCvc_check() {
                return cvc_check;
            }

            public Object getDynamic_last4() {
                return dynamic_last4;
            }

            public int getExp_month() {
                return exp_month;
            }

            public int getExp_year() {
                return exp_year;
            }

            public String getFingerprint() {
                return fingerprint;
            }

            public String getFunding() {
                return funding;
            }

            public String getLast4() {
                return last4;
            }

            public Object getName() {
                return name;
            }

            public Object getTokenization_method() {
                return tokenization_method;
            }

            public List<?> getMetadata() {
                return metadata;
            }
        }
    }
}
