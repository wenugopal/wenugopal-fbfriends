package com.wenugopal.apps.fbfriends.client.view;

import org.vectomatic.dom.svg.OMSVGCircleElement;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author wenugopal@gmail.com
 *
 */
public class CustomToolTip {
	
	private static int POPUP_MOUSEOUT_TIMEOUT = 300;
	private static int POPUP_MOUSEOVER_TIMEOUT = 500;
	private static int FOCUSWIDGET_MOUSEOUT_TIMEOUT = 300;
	
	private CustomPopupPanel popup = null;
	
	private boolean onFocus = false;
	private boolean onPopupFocus = false;
	
	private Integer X = null;
	private Integer Y = null;
	
	private Widget widget = null; 
	
	OMSVGCircleElement circle = null;

	public CustomToolTip(OMSVGCircleElement circle, Widget widget) {
		this.circle = circle;
		this.widget = widget;
		this.circle.addMouseOverHandler(new MouseTrackerMouseOverHandler());
		this.circle.addMouseOutHandler(new MouseTrackerMouseOutHandler());
		this.circle.addMouseMoveHandler(new MouseTrackerMouseMoveHandler());
	}
	
	class MouseTrackerMouseOverHandler implements MouseOverHandler {
		public void onMouseOver(MouseOverEvent event) {
			onFocus = true;
			X = event.getClientX();
			Y = event.getClientY();
//			System.out.println("event.getClientX() : "+event.getClientX());
//			System.out.println("event.getClientY() : "+event.getClientY() );
//			System.out.println("event.getX(): "+event.getX());
//			System.out.println("event.getY(): "+event.getY());
//			System.out.println("event.getScreenX(): "+event.getScreenX());
//			System.out.println("event.getScreenY(): "+event.getScreenY());
			new PopupShowTimer().schedule(CustomToolTip.POPUP_MOUSEOVER_TIMEOUT);
		}
	}
	class MouseTrackerMouseMoveHandler implements MouseMoveHandler {
		public void onMouseMove(MouseMoveEvent event) {
			onFocus = true;
			X = event.getClientX();
			Y = event.getClientY();
		}
	}
	
	class MouseTrackerMouseOutHandler implements MouseOutHandler {
		public void onMouseOut(MouseOutEvent event) {
			onFocus = false;
			X = null;
			Y = null;
			new PopupHideTimer().schedule(CustomToolTip.FOCUSWIDGET_MOUSEOUT_TIMEOUT);
		}
	}
	
	
	class MyPopupMouseOverHandler implements MouseOverHandler {
		public void onMouseOver(MouseOverEvent event) {
			popup.getElement().getStyle().clearProperty("clip");
			onPopupFocus = true;
		}
	}
	

	class MyPopupMouseMoveHandler implements MouseMoveHandler {
		public void onMouseMove(MouseMoveEvent event) {
			popup.getElement().getStyle().clearProperty("clip");
		}
	}
	
	class MyPopupMouseOutHandler implements MouseOutHandler {
		public void onMouseOut(MouseOutEvent event) {
			onPopupFocus = false;
			new PopupHideTimer().schedule(CustomToolTip.POPUP_MOUSEOUT_TIMEOUT);
		}
	}

	class PopupHideTimer extends Timer {
		@Override
		public void run() {
			if(!onFocus && !onPopupFocus && popup.isShowing()) {
				popup.hide();
			}
		}
	}
	
	class PopupShowTimer extends Timer {
		@Override
		public void run() {
			if((onFocus || onPopupFocus) && !popup.isShowing()) {
				if(X != null && Y != null) {
					popup.setPopupPosition(X + 3, Y);
					popup.show();
				}
			}
		}
	}

	/**
	 * @return the popup
	 */
	public CustomPopupPanel getPopup() {
		return popup;
	}

	/**
	 * @param popup the popup to set
	 */
	public OMSVGCircleElement initializePopup() {
		this.popup = new CustomPopupPanel();
		
		if(this.widget != null) {
			this.popup.add(this.widget);
		}
		
		popup.setAnimationEnabled(true);
		popup.setAutoHideEnabled(true);
		popup.addMouseOverHandler(new MyPopupMouseOverHandler());
		popup.addMouseOutHandler(new MyPopupMouseOutHandler());
		popup.addMouseMoveHandler(new MyPopupMouseMoveHandler());
		
		return circle;
	}

}
