package com.wenugopal.apps.fbfriends.client.dd;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.wenugopal.apps.fbfriends.client.dto.TGCLocation;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class TGCLocationDecoder
{
    public static TGCLocation decode(JavaScriptObject result)
    {
        TGCLocation tgcLocation = null;
        if(result != null)
        {
            JSONArray friendsJson = new JSONArray(result);
            tgcLocation = new TGCLocation();
            if(friendsJson.get(0) != null)
            {
                JSONValue latitude = friendsJson.get(0);
                if(latitude.isNumber() != null)
                    tgcLocation.setLatitude(latitude.isNumber().doubleValue());
            }
            JSONValue longitude = friendsJson.get(1);
            if(longitude.isNumber() != null)
                tgcLocation.setLongitude(longitude.isNumber().doubleValue());
        }
        return tgcLocation;
    }
}
