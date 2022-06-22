package com.foxy.arrive5.Response;

/**
 * Created by parangat on 7/8/18.
 */

public class CheckPromoCodeResponse {

    private boolean success;
    private String message;
    private ResultBean result;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ResultBean getResult() {
        return result;
    }

    public static class ResultBean {

        private String promo_type_name;
        private String promoValue;
        private String discount;
        private String minimumPurchaseValue;
        private String id;

        public String getPromo_type_name() {
            return promo_type_name;
        }

        public String getPromoValue() {
            return promoValue;
        }

        public String getDiscount() {
            return discount;
        }

        public String getMinimumPurchaseValue() {
            return minimumPurchaseValue;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
