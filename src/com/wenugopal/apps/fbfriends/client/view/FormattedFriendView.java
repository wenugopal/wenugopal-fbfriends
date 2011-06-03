package com.wenugopal.apps.fbfriends.client.view;

import java.io.Serializable;

import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;
import com.wenugopal.apps.fbfriends.client.factory.ImageFactory;

/**
 * @author wenugopal@gmail.com
 *
 */
@SuppressWarnings("serial")
public class FormattedFriendView extends Composite implements Serializable {

	private CustomFocusPanel customFocusPanel = null;

	private FlowPanel mainPanel = null;

	private FlowPanel imagePanel = null;
	private FlowPanel nameLocationPanel = null;
	private FlowPanel namePanel = null;
	private FlowPanel locationPanel = null;
	
	private boolean isFiltered = true;

	private FQLFriendsDetails fqlFriendsDetails = null;


	public FormattedFriendView() {
		this.customFocusPanel = new CustomFocusPanel();
		this.mainPanel = new FlowPanel();
		imagePanel = new FlowPanel();
		nameLocationPanel = new FlowPanel();
		namePanel = new FlowPanel();
		locationPanel = new FlowPanel();

		initWidget(this.customFocusPanel);
		this.customFocusPanel.add(this.mainPanel);
		this.customFocusPanel.addStyleName("customFriendFocusPanel");
	}


	public FormattedFriendView setData(final FQLFriendsDetails friendsDetails) {

		this.fqlFriendsDetails = friendsDetails;
		mainPanel.addStyleName("mainPanel");
		imagePanel.addStyleName("imagePanel");
		namePanel.setStylePrimaryName("namePanel");
		locationPanel.addStyleName("locationPanel");
		nameLocationPanel.addStyleName("nameLocationPanel");

		Image img  = null;
		if(friendsDetails.getPic_square() != null) {
			img = ImageFactory.createCustomUrlImage(friendsDetails.getPic_square(), "22px", "22px");
			img.getElement().getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		}

		imagePanel.add(img);

		Anchor nameLabel = new Anchor(friendsDetails.getName());
		nameLabel.setStylePrimaryName("name");
		nameLabel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.open(friendsDetails.getProfile_url(), "_blank", null);
				
			}
		});

		namePanel.add(nameLabel);

		Label descLabel = new Label(""+(friendsDetails.getCurrent_location() == null ? "unknown" : friendsDetails.getCurrent_location().toString()));
		descLabel.addStyleName("location");

		locationPanel.add(descLabel);

		nameLocationPanel.add(namePanel);
		nameLocationPanel.add(locationPanel);

		this.mainPanel.add(imagePanel);
		this.mainPanel.add(nameLocationPanel);
		
		return this;
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return this.customFocusPanel.addClickHandler(handler);
	}

	public CustomFocusPanel getCustomFocusPanel() {
		return customFocusPanel;
	}

	public void setCustomFocusPanel(CustomFocusPanel customFocusPanel) {
		this.customFocusPanel = customFocusPanel;
	}
	
	public FQLFriendsDetails getDetails() {
		return this.fqlFriendsDetails;
	}


	/**
	 * @return the isFiltered
	 */
	public boolean isFiltered() {
		return isFiltered;
	}


	/**
	 * @param isFiltered the isFiltered to set
	 */
	public void setFiltered(boolean isFiltered) {
		this.isFiltered = isFiltered;
	}
}
