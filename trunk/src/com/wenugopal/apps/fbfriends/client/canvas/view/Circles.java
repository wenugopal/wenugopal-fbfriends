package com.wenugopal.apps.fbfriends.client.canvas.view;

import com.google.gwt.canvas.dom.client.Context2d;

public class Circles {

	// canvas size, in px
	public static Circle circle = new Circle();
	public static Circle circle2 = new Circle(CircleConstants.MAX_RADIUS - CircleConstants.MAX_RADIUS/3);
	public static Circle circle3 = new Circle(CircleConstants.MAX_RADIUS - CircleConstants.MAX_RADIUS/3 - CircleConstants.MAX_RADIUS/3);
	
	
	public static void draw(Context2d context) {
		clear(context);
		circle.draw(context);
		circle2.draw(context);
		circle3.draw(context);
	}

	private static void clear(Context2d context) {
		context.clearRect(0, 0, CircleConstants.CANVAS_WIDTH, CircleConstants.CANVAS_HEIGHT);
	}
}
