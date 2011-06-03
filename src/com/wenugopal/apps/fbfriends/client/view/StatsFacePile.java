package com.wenugopal.apps.fbfriends.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wenugopal.apps.fbfriends.client.fb.view.FacePile;

public class StatsFacePile extends Composite {
	
	private VerticalPanel statsFacePilePanel = null;
	private FlowPanel facePilePanel = null;
	private HorizontalPanel heading = null;
	private Label headingLabel = null;
	
	private Label friendsValue = null;
	
	public StatsFacePile() {
		statsFacePilePanel = new VerticalPanel();
		statsFacePilePanel.getElement().getStyle().setMarginTop(25, Unit.PX);
		facePilePanel = new FlowPanel();
		heading = new HorizontalPanel();
		
		heading.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		heading.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		
		friendsValue =  new Label();
		headingLabel = new Label();
		
		statsFacePilePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		
		initWidget(statsFacePilePanel);
		initializeHeading();
		statsFacePilePanel.add(facePilePanel);
	}

	private void initializeHeading() {
		headingLabel = new Label();
		headingLabel.setStyleName("statsPanelFriends");
		friendsValue.setStyleName("statsPanelFriendsValue");
		heading.add(headingLabel);
		heading.add(friendsValue);
		statsFacePilePanel.add(heading);
	}
	
	public StatsFacePile setData(String country, FacePile facePile, int total, boolean isMale) {
		facePilePanel.clear();
		facePile.setStyleName("statsFacePile");
		facePilePanel.add(facePile);
		headingLabel.setText(country+" : ");
		friendsValue.setText(""+facePile.getCount()+"("+(isMale ? "M" : "F")+")");
		return this;
	}
	
}
