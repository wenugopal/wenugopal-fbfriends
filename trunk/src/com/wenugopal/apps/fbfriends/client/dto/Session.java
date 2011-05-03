package com.wenugopal.apps.fbfriends.client.dto;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class Session
{
    private String access_token;
    private String expires;
    private String secret;
    private String session_key;
    private String sig;
    private String uid;
    

    public String getAccess_token()
    {
        return access_token;
    }

    public void setAccess_token(String access_token)
    {
        this.access_token = access_token;
    }

    public String getExpires()
    {
        return expires;
    }

    public void setExpires(String expires)
    {
        this.expires = expires;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public String getSession_key()
    {
        return session_key;
    }

    public void setSession_key(String session_key)
    {
        this.session_key = session_key;
    }

    public String getSig()
    {
        return sig;
    }

    public void setSig(String sig)
    {
        this.sig = sig;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }


}
