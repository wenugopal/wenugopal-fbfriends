package com.wenugopal.apps.fbfriends.client.fb.view;

import com.google.gwt.user.client.ui.Anchor;


/**
 * @author wenugopal@gmail.com
 *
 */
public class FBButton  {
	
	
	public static Anchor getFBButton(String text) {
		Anchor  loginAnchor  = new Anchor();
		loginAnchor.addStyleName("fb_button");
		loginAnchor.addStyleName("fb_button_medium");
		
		String html = "<span class='fb_button_text'>"+text+"</span>";
		loginAnchor.setHTML(html);
		
		return loginAnchor;
	}

}
