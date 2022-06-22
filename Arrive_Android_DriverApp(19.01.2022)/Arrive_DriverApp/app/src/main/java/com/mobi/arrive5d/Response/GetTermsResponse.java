package com.mobi.arrive5d.Response;

/**
 * Created by parangat on 23/10/18.
 */

public class GetTermsResponse {

    /**
     * message : Terms & conditions fetch successfully.
     * status : true
     * get_terms : {"title":"Terms & Condition","description":"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."}
     */

    private String message;
    private String status;
    private GetTermsBean get_terms;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public GetTermsBean getGet_terms() {
        return get_terms;
    }

    public static class GetTermsBean {
        /**
         * title : Terms & Condition
         * description : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
         */

        private String title;
        private String description;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
