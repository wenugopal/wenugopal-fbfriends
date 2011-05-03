package com.wenugopal.apps.fbfriends.client.dto;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class SessionStatus
{
    private String status;
    private Session session;
    
    public SessionStatus()
    {
        status = null;
        session = null;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Session getSession()
    {
        return session;
    }

    public void setSession(Session session)
    {
        this.session = session;
    }


}
