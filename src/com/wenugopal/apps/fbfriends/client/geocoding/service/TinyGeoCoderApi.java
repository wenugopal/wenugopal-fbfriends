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
public class TinyGeoCoderApi
{
	private static String BASE_URL = "http://tinygeocoder.com/create-api.php?";
    private static int TIMEOUT = 30000;
    
    public TinyGeoCoderApi()
    {
    }

    public void send(String location, AsyncCallback<JavaScriptObject> callback)
    {
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        String url = URL.encode((new StringBuilder(String.valueOf(BASE_URL))).append("q=").append(location).toString());
        jsonp.setTimeout(TIMEOUT);
        jsonp.requestObject(url, callback);
    }
}
