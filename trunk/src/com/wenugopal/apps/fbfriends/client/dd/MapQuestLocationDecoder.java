package com.wenugopal.apps.fbfriends.client.dd;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.wenugopal.apps.fbfriends.client.dto.TGCLocation;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class MapQuestLocationDecoder
{

    public MapQuestLocationDecoder()
    {
    }

    public static TGCLocation decode(JavaScriptObject result)
    {
        TGCLocation tgcLocation = null;
        if(result != null)
        {
            JSONObject mapQuestResp = new JSONObject(result);
            if(mapQuestResp.get("results") != null)
            {
                JSONArray resultsArray = mapQuestResp.get("results").isArray();
                if(resultsArray != null && resultsArray.get(0).isObject() != null)
                {
                    JSONValue locations = resultsArray.get(0).isObject().get("locations");
                    JSONArray locationsArray = locations.isArray();
                    if(locationsArray != null)
                    {
                        tgcLocation = new TGCLocation();
                        JSONValue locationValue = locationsArray.get(0);
                        if(locationValue != null && locationValue.isObject() != null)
                        {
                            JSONObject location = locationValue.isObject();
                            JSONValue latLng = location.get("latLng");
                            if(latLng != null && latLng.isObject() != null)
                            {
                                JSONObject latLngObj = latLng.isObject();
                                JSONValue latJValue = latLngObj.get("lat");
                                if(latJValue != null)
                                    tgcLocation.setLatitude(latJValue.isNumber().doubleValue());
                                JSONValue lngJValue = latLngObj.get("lng");
                                if(lngJValue != null)
                                    tgcLocation.setLongitude(lngJValue.isNumber().doubleValue());
                            }
                        }
                    }
                }
            }
        }
        return tgcLocation;
    }
}
