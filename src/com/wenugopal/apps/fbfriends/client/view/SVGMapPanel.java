package com.wenugopal.apps.fbfriends.client.view;


import org.vectomatic.dom.svg.OMSVGCircleElement;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGGElement;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.utils.OMSVGParser;
import org.vectomatic.dom.svg.utils.SVGConstants;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wenugopal.apps.fbfriends.client.utils.MapUtil;

public class SVGMapPanel extends Composite {

	public static int WIDTH = 3600;

	public static int HEIGHT = 1800;

	public static int LEFT = 450;

	public static float CIRCLE_RADIUS = 16f; 

	public static int SCROLL_SPACE = 130;
	
	public static int MIN_HEIGHT = 30;
	
	public static int MIN_WIDTH = 60;
	
	public static String IMAGE_URL = "800px-Equirectangular-projection.jpg";

	private OMSVGSVGElement mapSvg = null;

	private OMSVGGElement pathsGroup = null;
	
	private OMSVGGElement userLocationGroup = null;

	private SVGResource resource = SvgMapBundle.INSTANCE.map();

	private FlowPanel flowPanel = null;

	private OMSVGDocument doc = OMSVGParser.createDocument();

	public SVGMapPanel() {
		mapSvg = resource.getSvg();
		pathsGroup  = doc.createSVGGElement();
		
		userLocationGroup = doc.createSVGGElement();
		userLocationGroup.setAttribute("render-order", "1");
		
		flowPanel = new FlowPanel();
		flowPanel.setStyleName("flowPanelStyle");
		flowPanel.getElement().appendChild(mapSvg.getElement());
		
		mapSvg.appendChild(pathsGroup);
		mapSvg.appendChild(userLocationGroup);
		
		initWidget(this.flowPanel);
		Window.addResizeHandler(new MapResizeHandler());
		
		int mapWidth = getWidth(Window.getClientWidth(), Window.getClientHeight());
		
		getSvgMap().setAttribute("width", mapWidth+"px"); 
		getSvgMap().setAttribute("height", (mapWidth/2)+"px");
	}
	
	private int getWidth(int width, int height) {
		height -= SCROLL_SPACE;
		if(height < MIN_HEIGHT || width < MIN_WIDTH) {
			width = MIN_WIDTH;
		} else if(width/2 > height) {
			width = height*2;
		}
		return width;
	}
	
	class MapResizeHandler implements ResizeHandler {
		public void onResize(ResizeEvent event) {
			if(event.getWidth()> 50 && event.getHeight() > 200) {
				int mapWidth = getWidth(Window.getClientWidth(), Window.getClientHeight());
				getSvgMap().setAttribute("width", mapWidth+"px"); 
				getSvgMap().setAttribute("height", (mapWidth/2)+"px");
			}
		}
	}


	public OMSVGSVGElement getSvgMap() {
		return this.mapSvg;
	}

	
	public void addPath(double x1, double y1, double x2, double y2){

		OMSVGPathElement pathElement = doc.createSVGPathElement();
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_VALUE, "#3B5998");
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_WIDTH_PROPERTY, "5");

		pathElement.setAttribute("d", "M"+x1+" "+y1+" L "+x2+" "+y2+"");
		pathElement.setAttribute("stroke-dasharray", "10,10");

		pathsGroup.appendChild(pathElement);
	}

	public void addPathByLatLng(double lat1, double lng1, double lat2, double lng2, Widget widget){
		lng1 = MapUtil.getLeft(SVGMapPanel.WIDTH, lng1);
		lat1 = MapUtil.getTop(SVGMapPanel.HEIGHT, lat1);
		lng2 = MapUtil.getLeft(SVGMapPanel.WIDTH, lng2);
		lat2 = MapUtil.getTop(SVGMapPanel.HEIGHT, lat2);

		final OMSVGCircleElement circle2 = doc.createSVGCircleElement((float)lng2, (float)lat2, CIRCLE_RADIUS);

		circle2.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, SVGConstants.CSS_RED_VALUE);
		circle2.getStyle().setSVGProperty(SVGConstants.CSS_FILL_OPACITY_PROPERTY, "0.4");

		OMSVGPathElement pathElement = doc.createSVGPathElement();

		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_VALUE, "red");
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_WIDTH_PROPERTY, "0.6");
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_OPACITY_PROPERTY, "0.6");

		pathElement.setAttribute("d", "M"+lng1+" "+lat1+" L "+lng2+" "+lat2+"");



		CustomToolTip ctt = new CustomToolTip(circle2, widget);

		pathsGroup.appendChild(pathElement);
		mapSvg.insertBefore(ctt.initializePopup(), userLocationGroup);
	}

	public void addCircleByLatLng(double lat2, double lng2, boolean me, Widget widget){
		lng2 = MapUtil.getLeft(SVGMapPanel.WIDTH, lng2);
		lat2 = MapUtil.getTop(SVGMapPanel.HEIGHT, lat2);

		String color = "red";
		final OMSVGCircleElement circle = doc.createSVGCircleElement((float)lng2, (float)lat2, CIRCLE_RADIUS);
		circle.getStyle().setSVGProperty(SVGConstants.CSS_FILL_OPACITY_PROPERTY, "0.4");
		
		if(me) {
			color = "green";
			circle.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, color);
			CustomToolTip ctt = new CustomToolTip(circle, widget);
			userLocationGroup.appendChild(ctt.initializePopup());
		} else  {
			circle.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, color);
			CustomToolTip ctt = new CustomToolTip(circle, widget);
			mapSvg.insertBefore(ctt.initializePopup(), userLocationGroup);
		}
	}

	public void hidePaths() {
		pathsGroup.getStyle().clearVisibility();
		pathsGroup.getStyle().setSVGProperty("visibility", "hidden");
	}

	public void unHidePaths() {
		pathsGroup.getStyle().clearVisibility();
		pathsGroup.getStyle().setSVGProperty("visibility", "visible");
	}
}
