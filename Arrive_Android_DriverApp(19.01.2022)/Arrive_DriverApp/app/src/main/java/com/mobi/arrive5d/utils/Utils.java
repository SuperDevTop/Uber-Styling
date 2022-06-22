package com.mobi.arrive5d.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Utils {

    public static MultipartBody generateMultiPartBody(ArrayList<PayLoad> payloads) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (PayLoad payload : payloads) {
            switch (payload.getMedia()) {
                case Constants.MEDIA_IMAGE:
                    //changes begin
                    String imageObject = payload.getValue();
                    try {
                        JSONArray jsonArray = new JSONArray(imageObject);
                        String imageArray[] = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String path = jsonObject.getString("image");
                            imageArray[i] = path;
                            File file = new File(path);
                            //multipart/form-data
                            RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
                            requestBodyBuilder.addFormDataPart(payload.getParameter(), "img.jpg", fbody);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //changes end
                   /* RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), new File(payload.getValue()));
                    requestBodyBuilder.addFormDataPart(payload.getParameter(), "image.jpeg", fbody);*/
                    break;
                case Constants.MEDIA_TEXT:
                    RequestBody name = RequestBody.create(MediaType.parse("text/plain"), payload.getValue());
                    requestBodyBuilder.addFormDataPart(payload.getParameter(), null, name);
                    break;
            }
        }
        return requestBodyBuilder.build();
    }
}
