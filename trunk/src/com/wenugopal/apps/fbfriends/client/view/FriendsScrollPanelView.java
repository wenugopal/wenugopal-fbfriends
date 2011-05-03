package com.wenugopal.apps.fbfriends.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;

public class FriendsScrollPanelView extends Composite {
	
	private FlowPanel flowPanel = null;

	private ScrollPanel scrollPanel = null;
	
	private CellList<FQLFriendsDetails> cellList = null;
	
	private FriendsCell cell = null;
	
	private CellList.Resources resources = GWT.create(MyCellResources.class);

	public FriendsScrollPanelView() {
		flowPanel = new FlowPanel();
		scrollPanel = new ScrollPanel();
		cell = new FriendsCell();
		cellList = new CellList<FQLFriendsDetails>(cell, resources);
		
		scrollPanel.addStyleName("scroll");
		scrollPanel.addStyleName("scrollPanel");
		
		flowPanel.getElement().getStyle().setPosition(Position.ABSOLUTE);
		flowPanel.addStyleName("boxShadow");
		flowPanel.addStyleName("scrollFlowPanel");
		flowPanel.add(scrollPanel);
		
		scrollPanel.add(cellList);
		initWidget(flowPanel);
	}
	
	public void setData(List<FQLFriendsDetails> friendsDetailsList) {
		cellList.setRowData(friendsDetailsList);
	}
	
	interface MyCellResources extends CellList.Resources {
        @Source(MyCellStyle.STYLE)
        MyCellStyle cellListStyle();
    }

     interface MyCellStyle extends CellList.Style {
        String STYLE = "MyCellListStyle.css";
        
        String cellListEvenItem();

        String cellListKeyboardSelectedItem();

        String cellListOddItem();

        String cellListSelectedItem();

        String cellListWidget();
        
    }
     

}
