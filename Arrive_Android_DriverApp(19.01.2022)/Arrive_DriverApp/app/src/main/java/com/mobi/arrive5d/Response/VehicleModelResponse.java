package com.mobi.arrive5d.Response;

import java.util.List;

public class VehicleModelResponse {

    /**
     * detail : [{"id":"1","model":"MDX"},{"id":"2","model":"RDX"},{"id":"3","model":"RL"},{"id":"4","model":"TL"},{"id":"5","model":"TSX"},{"id":"368","model":"MDX"},{"id":"369","model":"RDX"},{"id":"370","model":"RL"},{"id":"371","model":"TL"},{"id":"372","model":"TSX"},{"id":"731","model":"MDX"},{"id":"732","model":"RDX"},{"id":"733","model":"RL"},{"id":"734","model":"TL"},{"id":"735","model":"TSX"},{"id":"736","model":"ZDX"},{"id":"1078","model":"MDX"},{"id":"1079","model":"RDX"},{"id":"1080","model":"RL"},{"id":"1081","model":"TL"},{"id":"1082","model":"TSX"},{"id":"1083","model":"ZDX"},{"id":"1425","model":"MDX"},{"id":"1426","model":"RDX"},{"id":"1427","model":"RL"},{"id":"1428","model":"TL"},{"id":"1429","model":"TSX"},{"id":"1430","model":"ZDX"},{"id":"1769","model":"ILX"},{"id":"1770","model":"MDX"},{"id":"1771","model":"RDX"},{"id":"1772","model":"TL"},{"id":"1773","model":"TSX"},{"id":"1774","model":"ZDX"},{"id":"2122","model":"ILX"},{"id":"2123","model":"MDX"},{"id":"2124","model":"RDX"},{"id":"2125","model":"RLX"},{"id":"2126","model":"RLX Sport Hybrid"},{"id":"2127","model":"TL"},{"id":"2128","model":"TSX"},{"id":"2492","model":"ILX"},{"id":"2493","model":"MDX"},{"id":"2494","model":"RDX"},{"id":"2495","model":"RLX"},{"id":"2496","model":"TLX"},{"id":"2881","model":"ILX"},{"id":"2882","model":"MDX"},{"id":"2883","model":"RDX"},{"id":"2884","model":"RLX"},{"id":"2885","model":"RLX Sport Hybrid"},{"id":"2886","model":"TLX"},{"id":"3284","model":"ILX"},{"id":"3285","model":"MDX"},{"id":"3286","model":"MDX Sport Hybrid"},{"id":"3287","model":"NSX"},{"id":"3288","model":"RDX"},{"id":"3289","model":"RLX"},{"id":"3290","model":"RLX Sport Hybrid"},{"id":"3291","model":"TLX"},{"id":"3767","model":"ILX"},{"id":"3768","model":"MDX"},{"id":"3769","model":"NSX"},{"id":"3770","model":"RDX"},{"id":"3771","model":"RLX"},{"id":"3772","model":"RLX Sport Hybrid"},{"id":"3773","model":"TLX"},{"id":"3774","model":"MDX Sport Hybrid"}]
     * status : true
     */

    private String status;
    private List<DetailBean> detail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * id : 1
         * model : MDX
         */

        private String id;
        private String modelname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModel() {
            return modelname;
        }

        public void setModel(String model) {
            this.modelname = model;
        }
    }
}
