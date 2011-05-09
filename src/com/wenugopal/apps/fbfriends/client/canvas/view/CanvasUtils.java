package com.wenugopal.apps.fbfriends.client.canvas.view;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class CanvasUtils {

	public static  void drawCricle(Context2d context, String color, double x, double y, double radius) {
		context.setLineWidth(1);
		context.setStrokeStyle(CssColor.make(color));
//		FillStyle(CssColor.make(color) );
		context.beginPath();
		context.arc(x, y, radius, 0, Math.PI * 2.0, true);
		context.closePath();
//		context.fill();
		context.stroke();
	}
	
}
