package com.wenugopal.apps.fbfriends.client.view;

import java.io.Serializable;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;
import com.wenugopal.apps.fbfriends.client.factory.ImageFactory;

/**
 * @author wenugopal@gmail.com
 *
 */
@SuppressWarnings("serial")
public class FormattedContentView extends Composite implements Serializable {

	private CustomFocusPanel customFocusPanel = null;

	private FlexTable contentFlexTable = null;


	public FormattedContentView() {
		this.customFocusPanel = new CustomFocusPanel();
		this.contentFlexTable = new FlexTable();
		initWidget(this.customFocusPanel);
		this.customFocusPanel.add(this.contentFlexTable);
		this.customFocusPanel.addStyleName("focusPanelWithoutHand");
		//		this.focusPanel.addMouseOutHandler(new HighlightMouseHandler());
		//		this.focusPanel.addMouseOverHandler(new HighlightMouseHandler());
	}


	public void setData(FQLFriendsDetails friendsDetails) {

		FlexCellFormatter cellFormatter = contentFlexTable.getFlexCellFormatter();
		contentFlexTable.addStyleName("contentHPanel");

		cellFormatter.setHorizontalAlignment(0,0, HorizontalPanel.ALIGN_CENTER);
		cellFormatter.setVerticalAlignment(0,0, HorizontalPanel.ALIGN_TOP);


		cellFormatter.setHorizontalAlignment(0,1, HorizontalPanel.ALIGN_LEFT);
		cellFormatter.setVerticalAlignment(0,1, HorizontalPanel.ALIGN_TOP);

		FlexTable itemDetailsFTable =  new FlexTable();
		FlexCellFormatter itemCellFormatter = itemDetailsFTable.getFlexCellFormatter();


		itemCellFormatter.setAlignment(0, 0, HorizontalPanel.ALIGN_LEFT, VerticalPanel.ALIGN_TOP);
		Label nameLabel = new Label(friendsDetails.getName());
		nameLabel.addStyleName("itemName");

		Label mfrLabel = new Label(friendsDetails.getSex());
		mfrLabel.addStyleName("itemManufacturer");

		Label descLabel = new Label(""+(friendsDetails.getCurrent_location() == null ? "unknown" : friendsDetails.getCurrent_location().toString()));
		descLabel.addStyleName("itemDescription");

		itemDetailsFTable.setWidget(0, 0, nameLabel);
		itemDetailsFTable.setWidget(1, 0, mfrLabel);
		itemDetailsFTable.setWidget(2, 0, descLabel);

		Image img  = null;
		if(friendsDetails.getPic() != null) {
			img = ImageFactory.createCustomUrlImage(friendsDetails.getPic_small(), "80px", "80px");
		}
		
		cellFormatter.setWidth(0, 0, "85px");
		contentFlexTable.setWidget(0, 0, img);
		contentFlexTable.setWidget(0, 1, itemDetailsFTable);
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return this.customFocusPanel.addClickHandler(handler);
	}

	/**
	 * @return the customFocusPanel
	 */
	public CustomFocusPanel getCustomFocusPanel() {
		return customFocusPanel;
	}

	/**
	 * @param customFocusPanel the customFocusPanel to set
	 */
	public void setCustomFocusPanel(CustomFocusPanel customFocusPanel) {
		this.customFocusPanel = customFocusPanel;
	}

	/**
	 * @return the contentFlexTable
	 */
	public FlexTable getContentFlexTable() {
		return contentFlexTable;
	}

	/**
	 * @param contentFlexTable the contentFlexTable to set
	 */
	public void setContentFlexTable(FlexTable contentFlexTable) {
		this.contentFlexTable = contentFlexTable;
	}

}
