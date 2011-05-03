package com.wenugopal.apps.fbfriends.client.geocoding.service;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class BingMapsApi
{
    private static String BASE_URL = "http://dev.virtualearth.net/REST/v1/Locations?";
    private static String KEY = "Ah_HtWqIephLqS2wR6_9l2l1P7kj_gLqjNc0ZnzrB5cQDYKa_k8A7I4Ne-yBLkil";
    private static int TIMEOUT = 30000;

    public BingMapsApi()
    {
    }

    public void send(String location, AsyncCallback<JavaScriptObject> callback)
    {
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        String url = URL.encode((new StringBuilder(String.valueOf(BASE_URL))).append("key=").append(KEY).append("&output=json&q=").append(location).toString());
        jsonp.setCallbackParam("jsonp");
        jsonp.setTimeout(TIMEOUT);
        jsonp.requestObject(url, callback);
    }
}
