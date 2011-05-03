package com.wenugopal.apps.fbfriends.client.dd;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.wenugopal.apps.fbfriends.client.dto.Session;
import com.wenugopal.apps.fbfriends.client.dto.SessionStatus;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class SessionDecoder
{
	public static SessionStatus decode(JavaScriptObject jso)
	{
		SessionStatus sessionStatus = new SessionStatus();
		if(jso != null)
		{
			JSONObject response = new JSONObject(jso);
			if(response.containsKey("status"))
			{
				JSONString status = response.get("status").isString();
				if(status != null)
					sessionStatus.setStatus(status.stringValue());
			}
			if(response.containsKey("session"))
			{
				JSONObject sessionObj = response.get("session").isObject();
				Session session = new Session();
				if(sessionObj != null)
				{
					JSONString access_token = sessionObj.get("access_token").isString();
					if(access_token != null)
						session.setAccess_token(access_token.stringValue());

					JSONString expires = sessionObj.get("expires").isString();
					if(expires != null)
						session.setExpires(expires.stringValue());
					JSONString secret = sessionObj.get("secret").isString();
					if(secret != null)
						session.setSecret(secret.stringValue());
					JSONString session_key = sessionObj.get("session_key").isString();
					if(session_key != null)
						session.setSession_key(session_key.stringValue());
					JSONString sig = sessionObj.get("sig").isString();
					if(sig != null)
						session.setSig(sig.stringValue());
					JSONString uid = sessionObj.get("uid").isString();
					if(uid != null)
						session.setUid(uid.stringValue());
					sessionStatus.setSession(session);
				}
			} else
			{
				Window.alert("session not present in response");
			}
		}
		return sessionStatus;
	}
}
