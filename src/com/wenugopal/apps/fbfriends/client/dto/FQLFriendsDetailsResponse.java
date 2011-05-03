package com.wenugopal.apps.fbfriends.client.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class FQLFriendsDetailsResponse
{
	private List<FQLFriendsDetails> fqlFriendsDetailsList;
	
    public FQLFriendsDetailsResponse()
    {
        fqlFriendsDetailsList = null;
    }

    public List<FQLFriendsDetails> getFqlFriendsDetailsList()
    {
        return fqlFriendsDetailsList;
    }

    public void setFqlFriendsDetailsList(List<FQLFriendsDetails> fqlFriendsDetailsList)
    {
        this.fqlFriendsDetailsList = fqlFriendsDetailsList;
    }

    public void addFQLFriendsDetails(FQLFriendsDetails fQLFriendsDetails)
    {
        if(fqlFriendsDetailsList == null)
            fqlFriendsDetailsList = new ArrayList<FQLFriendsDetails>();
        fqlFriendsDetailsList.add(fQLFriendsDetails);
    }

}
