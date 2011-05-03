package com.wenugopal.apps.fbfriends.client.factory;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Image;

/**
 * @author wenugopal@gmail.com
 *
 */
@SuppressWarnings("serial")
public class ImageFactory implements Serializable {

	public static Image createCustomUrlImage(String url, String width, String height) {
		Image image = new Image(url);
		image.setWidth(width);
		image.setHeight(height);
		return image;
	}
	
	public static Image createCustomUrlImage(String url) {
		Image image = new Image(url);
		return image;
	}
	
	
	
}
