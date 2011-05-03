package com.wenugopal.apps.fbfriends.client.view;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class MapPanel extends Composite {
	
	public static int WIDTH = 800;
	
	public static int HEIGHT = 400;
	
	public static int LEFT = 600;
	
	public static String IMAGE_URL = "800px-Equirectangular-projection.jpg";
	
	private FlowPanel mapArea = null;
	
	private FlowPanel mapAreaPanel = null;


	public MapPanel() {
		this.mapArea = new FlowPanel();
		this.mapAreaPanel = new FlowPanel();
		initStyle();
		initWidget(this.mapAreaPanel);
	}
	
	private void initStyle() {
		mapArea.setWidth(MapPanel.WIDTH+"px");
		mapArea.getElement().getStyle().setPosition(Position.RELATIVE);
		mapArea.getElement().getStyle().setBorderWidth(1, Unit.PX);
		mapArea.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		mapArea.getElement().getStyle().setBorderColor("#CCC");
		mapArea.getElement().getStyle().setPadding(0, Unit.PX);
		mapArea.addStyleName("mapPanelShadow");
		mapAreaPanel.getElement().getStyle().setLeft(MapPanel.LEFT, Unit.PX);
		Image img = new Image(MapPanel.IMAGE_URL, 0, 0, MapPanel.WIDTH, MapPanel.HEIGHT);
		mapArea.add(img);
		mapAreaPanel.add(mapArea);
		
	}
	
	public void addLocationWidget(Widget widget){
		this.mapArea.add(widget);
	}

}
