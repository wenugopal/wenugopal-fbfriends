package com.wenugopal.apps.fbfriends.client.fb.view;

import com.google.gwt.user.client.ui.HTML;

//TODO: consider revising code to prevent from XSS.
public class FBSocialPlugin {

	public static HTML getFacePile(){
		HTML facepileHtml  = new HTML();
		facepileHtml.setHTML("<fb:facepile></fb:facepile>");
		return facepileHtml;
	}
	
	public static HTML getLikeHtml(String url){
		HTML facepileHtml  = new HTML();
		facepileHtml.setHTML("<iframe src='http://www.facebook.com/plugins/like.php?href=http%3A%2F%2Ffballinone.appspot.com&amp;send=true&amp;layout=standard&amp;width=450&amp;show_faces=false&amp;action=like&amp;colorscheme=light&amp;font=segoe+ui&amp;height=35' scrolling='no' frameborder='0' style='border:none; overflow:hidden; width:450px; height:35px;' allowTransparency='true'></iframe>");
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
