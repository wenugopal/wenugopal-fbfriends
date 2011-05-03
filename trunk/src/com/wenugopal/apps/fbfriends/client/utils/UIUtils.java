package com.wenugopal.apps.fbfriends.client.utils;

import com.google.gwt.user.client.ui.FlowPanel;

public class UIUtils {
	
	public static FlowPanel getLocationDiv(String name, double top, double left) {
		FlowPanel contentDiv = new FlowPanel();
		contentDiv
		.getElement()
		.setAttribute(
				"style",
				(new StringBuilder(
				"position: absolute; z-index: 2; height: 0; width: 0; margin: 0; padding: 0; top: "))
				.append(top).append("px; left: ").append(left)
				.append("px;").toString());

		contentDiv
		.getElement()
		.setInnerHTML(
				(new StringBuilder(
				" <div style='position: relative; text-align: center; left: -4px; top: -4px; width: 8px; font-size: 8px;' title= '"))
				.append(name)
				.append("'> <img src='http://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Red_pog.svg/8px-Red_pog.svg.png' width='8' height='8' alt='")
				.append(name).append("'></div>").toString());
		return contentDiv;
	}


}
