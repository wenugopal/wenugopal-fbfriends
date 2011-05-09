package com.wenugopal.apps.fbfriends.client.fb.view;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;

public class FacePile extends Composite {

	private FlowPanel facePilePanel = null;
	
	public FacePile() {
		facePilePanel = new FlowPanel();
		facePilePanel.setStyleName("uiFacePile");
		initWidget(facePilePanel);
	}

	public FacePile setData(List<FQLFriendsDetails> detailsList) {
		for(FQLFriendsDetails item : detailsList){
			addFacePileItem(item);
		}
		return this;
	}
	
	private void addFacePileItem(final FQLFriendsDetails item) {
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("uiFacepileItem");
		Anchor anchor = new Anchor();
		anchor.addStyleName("uiTooltip");
		anchor.setHTML("<img class='uiFacepileItemImg' src='"+item.getPic_square()+"'	alt=''>	<span class='uiTooltipWrap '><span class='uiTooltipText'>"+item.getName()+"</span></span>");
		anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(item.getProfile_url(), "_blank", null);

			}
		});
		flowPanel.add(anchor);

		this.facePilePanel.add(flowPanel);
	}
}
