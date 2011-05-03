
package com.wenugopal.apps.fbfriends.client.dd;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetailsMap;
import com.wenugopal.apps.fbfriends.client.dto.Location;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class FQLFriendsDetailsResponseDecoder
{
    public static FQLFriendsDetailsMap decode(JavaScriptObject result)
    {
        FQLFriendsDetailsMap fqlresponse = new FQLFriendsDetailsMap();
        if(result != null)
        {
            JSONObject friendsJson = new JSONObject(result);
            for(int i = 0; friendsJson.get((new StringBuilder(String.valueOf(i))).toString()) != null; i++)
            {
                JSONValue data = friendsJson.get((new StringBuilder(String.valueOf(i))).toString());
                if(data != null)
                    fqlresponse.addFQLFriendsDetails(decodeFriendsDetails(data));
            }

        }
        return fqlresponse;
    }

    private static FQLFriendsDetails decodeFriendsDetails(JSONValue friendsDetailsJson)
    {
        FQLFriendsDetails fQLFriendsDetails = null;
        JSONObject friendDetailsJsonObj = friendsDetailsJson.isObject();
        if(friendDetailsJsonObj != null)
        {
            fQLFriendsDetails = new FQLFriendsDetails();
            JSONValue uid = friendDetailsJsonObj.get("uid");
            if(uid != null)
            {
                JSONString uidString = null;
                if((uidString = uid.isString()) != null)
                    fQLFriendsDetails.setUid(uidString.stringValue());
            }
            JSONValue name = friendDetailsJsonObj.get("name");
            if(name != null)
            {
                JSONString nameString = null;
                if((nameString = name.isString()) != null)
                    fQLFriendsDetails.setName(nameString.stringValue());
            }
            JSONValue pic_small = friendDetailsJsonObj.get("pic_small");
            if(pic_small != null)
            {
                JSONString pic_smallString = null;
                if((pic_smallString = pic_small.isString()) != null)
                    fQLFriendsDetails.setPic_small(pic_smallString.stringValue());
            }
            
            JSONValue pic_square = friendDetailsJsonObj.get("pic_square");
            if(pic_square != null)
            {
                JSONString pic_squareString = null;
                if((pic_squareString = pic_square.isString()) != null)
                    fQLFriendsDetails.setPic_square(pic_squareString.stringValue());
            }
            
            
            JSONValue pic = friendDetailsJsonObj.get("pic");
            if(pic != null)
            {
                JSONString picString = null;
                if((picString = pic.isString()) != null)
                    fQLFriendsDetails.setPic(picString.stringValue());
            }
            JSONValue sex = friendDetailsJsonObj.get("sex");
            if(sex != null)
            {
                JSONString sexString = null;
                if((sexString = sex.isString()) != null)
                    fQLFriendsDetails.setSex(sexString.stringValue());
            }
            JSONValue profile_url = friendDetailsJsonObj.get("profile_url");
            if(profile_url != null)
            {
                JSONString profile_urlString = null;
                if((profile_urlString = profile_url.isString()) != null)
                    fQLFriendsDetails.setProfile_url(profile_urlString.stringValue());
            }
            JSONValue username = friendDetailsJsonObj.get("username");
            if(username != null)
            {
                JSONString usernameString = null;
                if((usernameString = username.isString()) != null)
                    fQLFriendsDetails.setUsername(usernameString.stringValue());
            }
            fQLFriendsDetails.setHometown_location(decodeLocation(friendDetailsJsonObj.get("hometown_location")));
            fQLFriendsDetails.setCurrent_location(decodeLocation(friendDetailsJsonObj.get("current_location")));
        }
        return fQLFriendsDetails;
    }

    private static Location decodeLocation(JSONValue locationJson)
    {
        Location location = null;
        if(locationJson != null)
        {
            JSONObject locationJsonObj = locationJson.isObject();
            if(locationJsonObj != null)
            {
                location = new Location();
                JSONValue city = locationJsonObj.get("city");
                if(city != null)
                {
                    JSONString cityString = null;
                    if((cityString = city.isString()) != null)
                        location.setCity(cityString.stringValue());
                }
                JSONValue state = locationJsonObj.get("state");
                if(state != null)
                {
                    JSONString stateString = null;
                    if((stateString = state.isString()) != null)
                        location.setState(stateString.stringValue());
                }
                JSONValue country = locationJsonObj.get("country");
                if(country != null)
                {
                    JSONString countryString = null;
                    if((countryString = country.isString()) != null)
                        location.setCountry(countryString.stringValue());
                }
                JSONValue zip = locationJsonObj.get("zip");
                if(zip != null)
                {
                    JSONString zipString = null;
                    if((zipString = zip.isString()) != null)
                        location.setZip(zipString.stringValue());
                }
                JSONValue id = locationJsonObj.get("id");
                if(id != null)
                {
                    JSONString idString = null;
                    if((idString = id.isString()) != null)
                        location.setId(idString.stringValue());
                }
                JSONValue name = locationJsonObj.get("name");
                if(name != null)
                {
                    JSONString nameString = null;
                    if((nameString = name.isString()) != null)
                        location.setName(nameString.stringValue());
                }
            }
        }
        return location;
    }
}
