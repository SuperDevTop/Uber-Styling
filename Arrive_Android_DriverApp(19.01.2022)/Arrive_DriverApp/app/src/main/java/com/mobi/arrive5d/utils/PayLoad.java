package com.mobi.arrive5d.utils;

/**
 * Created by android on 11/8/17.
 */
public class PayLoad {
    private String parameter;
    private String value;
    private String media;

    public PayLoad(String parameter, String value, String media) {
        this.parameter = parameter;
        this.value = value;
        this.media = media;
    }

    public String getMedia() {
        return media;
    }

    public String getParameter() {
        return parameter;
    }

    public String getValue() {
        return value;
    }

}
