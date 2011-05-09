package com.wenugopal.apps.fbfriends.client.view;

import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.PopupPanel;

public class CustomPopupPanel extends PopupPanel implements HasMouseOutHandlers, HasMouseOverHandlers {

    public CustomPopupPanel() {
      super(true);
      super.setStyleName("customPopupPanel");
      super.addStyleName("customPopupBoxShadow");
    }

    public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		 return addDomHandler(handler, MouseOutEvent.getType());
	}
	
	
}