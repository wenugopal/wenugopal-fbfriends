package com.wenugopal.apps.fbfriends.client.dto;

import java.util.HashMap;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class FQLFriendsDetailsMap extends HashMap<String, FQLFriendsDetails>
{
	private static final long serialVersionUID = 1L;

    public void addFQLFriendsDetails(FQLFriendsDetails fQLFriendsDetails)
    {
        put(fQLFriendsDetails.getUid(), fQLFriendsDetails);
    }

    
}
