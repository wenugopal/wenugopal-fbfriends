package com.wenugopal.apps.fbfriends.client.dd;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.wenugopal.apps.fbfriends.client.dto.Friend;
import com.wenugopal.apps.fbfriends.client.dto.Friends;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class FriendsDecoder
{
    public static Friends decode(JavaScriptObject result)
    {
        Friends friends = new Friends();
        if(result != null)
        {
            JSONObject friendsJson = new JSONObject(result);
            JSONValue data = friendsJson.get("data");
            if(data != null)
            {
                JSONArray friendsJsonArray = data.isArray();
                for(int i = 0; i < friendsJsonArray.size(); i++)
                {
                    Friend friend = new Friend();
                    JSONValue name = friendsJsonArray.get(i).isObject().get("name");
                    JSONValue id = friendsJsonArray.get(i).isObject().get("id");
                    friend.setId(id.isString().stringValue());
                    friend.setName(name.isString().stringValue());
                    friends.addFriend(friend);
                }

            }
        }
        return friends;
    }
}
