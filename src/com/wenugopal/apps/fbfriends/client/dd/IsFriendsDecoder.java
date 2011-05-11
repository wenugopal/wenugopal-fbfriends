package com.wenugopal.apps.fbfriends.client.dd;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class IsFriendsDecoder
{
    public static boolean decode(JavaScriptObject result)
    {
    	boolean isFriends = false;
        if(result != null)
        {
        	JSONArray friendsJson = new JSONArray(result);
        	if(friendsJson != null && friendsJson.size() > 0) {
        		isFriends = true;
        	}
        }
        return isFriends;
    }
}
