package com.wenugopal.apps.fbfriends.client.geocoding.service;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wenugopal.apps.fbfriends.client.dto.Location;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class MapQuestApi
{

	  private static String BASE_URL = "http://www.mapquestapi.com/geocoding/v1/address?";
	    private static String KEY = URL.decode("Fmjtd%7Cluu22qu129%2C2n%3Do5-h6aw5");
	    private static int TIMEOUT = 30000;
	    
    public MapQuestApi()
    {
    }

    public void send(String location, AsyncCallback<JavaScriptObject> callback)
    {
        JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
        String url = URL.encode((new StringBuilder(String.valueOf(BASE_URL))).append("key=").append(KEY).append("&inFormat=json&outFormat=json&json=").append(location).toString());
        jsonp.setTimeout(TIMEOUT);
        jsonp.requestObject(url, callback);
    }

    public void send(Location location, AsyncCallback<JavaScriptObject> callback)
    {
        JSONObject locationJsonReq = new JSONObject();
        JSONObject locationJson = new JSONObject();
        if(location != null)
        {
            if(location.getCity() != null)
                locationJson.put("city", new JSONString(location.getCity()));
            if(location.getState() != null)
                locationJson.put("state", new JSONString(location.getState()));
            if(location.getCountry() != null)
                locationJson.put("country", new JSONString(location.getCountry()));
        }
        locationJsonReq.put("location", locationJson);
        send(locationJsonReq.toString(), callback);
    }

  

}
