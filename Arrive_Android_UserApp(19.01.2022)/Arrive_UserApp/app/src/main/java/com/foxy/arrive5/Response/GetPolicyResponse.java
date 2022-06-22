package com.foxy.arrive5.Response;

/**
 * Created by parangat on 23/10/18.
 */

public class GetPolicyResponse {

    /**
     * message : Privacy policy fetch successfully.
     * status : true
     * get_data : {"title":"Privacy Policy","description":"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."}
     */

    private String message;
    private String status;
    private GetDataBean get_data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public GetDataBean getGet_data() {
        return get_data;
    }

    public static class GetDataBean {
        /**
         * title : Privacy Policy
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
