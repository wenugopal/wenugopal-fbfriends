package com.wenugopal.apps.fbfriends.client.canvas.view;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class Circle {

	private  double transparency = 0.8;
	private  double radius = 0;
	private  double minRadius = 0;
	private  double minTransparency = 0.8;
	private double lineWidth = 1;

	public Circle() {
	}

	public Circle(double radius, double minTransparency) {
		this.radius = radius;
		this.minTransparency = minTransparency;
	}
	
	public Circle(double radius) {
		this.radius = radius;
	}

	public void draw(Context2d context) {
		
		if(radius > CircleConstants.MAX_RADIUS) {
			radius = minRadius;
			transparency = minTransparency;
		}
		
		radius = radius + CircleConstants.RADIUS_INCREMENT;
		transparency = transparency - transparency/((CircleConstants.MAX_RADIUS)/CircleConstants.RADIUS_INCREMENT);
		drawCricle(context, "rgba(50,205,50,"+transparency+")", CircleConstants.MAX_RADIUS+lineWidth, CircleConstants.MAX_RADIUS+lineWidth, radius);
	}
	
	public  void drawCricle(Context2d context, String color, double x, double y, double radius) {
		context.setLineWidth(lineWidth);
		context.setStrokeStyle(CssColor.make(color));
//		FillStyle(CssColor.make(color) );
		context.beginPath();
		context.arc(x, y, radius, 0, Math.PI * 2.0, true);
		context.closePath();
//		context.fill();
		context.stroke();
	}

	/**
	 * @return the transparency
	 */
	public double getTransparency() {
		return transparency;
	}

	/**
	 * @param transparency the transparency to set
	 */
	public void setTransparency(double transparency) {
		this.transparency = transparency;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return the minRadius
	 */
	public double getMinRadius() {
		return minRadius;
	}

	/**
	 * @param minRadius the minRadius to set
	 */
	public void setMinRadius(double minRadius) {
		this.minRadius = minRadius;
	}

	/**
	 * @return the minTransparency
	 */
	public double getMinTransparency() {
		return minTransparency;
	}

	/**
	 * @param minTransparency the minTransparency to set
	 */
	public void setMinTransparency(double minTransparency) {
		this.minTransparency = minTransparency;
	}

}
