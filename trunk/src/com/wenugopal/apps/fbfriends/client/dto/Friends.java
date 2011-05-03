package com.wenugopal.apps.fbfriends.client.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class Friends
{
	private List<Friend> data;

    public List<Friend> getFriends()
    {
        return data;
    }

    public void setFriends(List<Friend> friends)
    {
        data = friends;
    }

    public void addFriend(Friend friend)
    {
        if(data == null)
            data = new ArrayList<Friend>();
        data.add(friend);
    }
}
