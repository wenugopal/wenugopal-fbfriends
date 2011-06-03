package com.wenugopal.apps.fbfriends.client.fb.view;

import com.google.gwt.user.client.ui.HTML;

//TODO: consider revising code to prevent from XSS.
public class FBSocialPlugin {

	public static HTML getFacePile(){
		HTML facepileHtml  = new HTML();
		facepileHtml.setHTML("<fb:facepile width='500'></fb:facepile>");
		return facepileHtml;
	}
	
	public static HTML getLikeSendHtml(String url){
		HTML facepileHtml  = new HTML();
		facepileHtml.setHTML("<fb:like href='"+url+"' send='true' width='450' show_faces='false' font='segoe ui'></fb:like>");
		return facepileHtml;
	}
	
	
	public static HTML getCommentsHtml(String url){
		HTML facepileHtml  = new HTML();
		facepileHtml.setHTML("<fb:comments href='"+url+"' num_posts='20' width='500'></fb:comments>");
		return facepileHtml;
	}
}
