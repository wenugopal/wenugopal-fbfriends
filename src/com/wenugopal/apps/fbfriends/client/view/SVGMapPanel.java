package com.wenugopal.apps.fbfriends.client.view;

import org.vectomatic.dom.svg.OMSVGCircleElement;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGPathElement;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.utils.OMSVGParser;
import org.vectomatic.dom.svg.utils.SVGConstants;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.wenugopal.apps.fbfriends.client.utils.MapUtil;

public class SVGMapPanel extends Composite {

	public static int WIDTH = 3600;

	public static int HEIGHT = 1800;

	public static int LEFT = 450;

	public static String IMAGE_URL = "800px-Equirectangular-projection.jpg";

	private OMSVGSVGElement mapSvg = null;

	private SVGResource resource = SvgMapBundle.INSTANCE.map();

	private FlowPanel flowPanel = null;
	
	private OMSVGDocument doc = OMSVGParser.createDocument();
	
	public SVGMapPanel() {
		mapSvg = resource.getSvg();
		flowPanel = new FlowPanel();
		flowPanel.setStyleName("flowPanelStyle");
		flowPanel.getElement().appendChild(mapSvg.getElement());
		initWidget(this.flowPanel);
	}


	public void addPath(double x1, double y1, double x2, double y2){
//		x1 = x1*58.9518363;
//		y1 = y1*58.9518363;
//		x2 = x2*58.9518363;
//		y2 = y2*58.9518363;
		
		OMSVGPathElement pathElement = doc.createSVGPathElement();
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_VALUE, "#3B5998");
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_WIDTH_PROPERTY, "5");
		
//		pathElement.setAttribute("transform", "translate(0,239) scale(0.016963, 0.016963)");
		pathElement.setAttribute("d", "M"+x1+" "+y1+" L "+x2+" "+y2+"");
		pathElement.setAttribute("stroke-dasharray", "10,10");
		
		
		mapSvg.appendChild(pathElement);
	}

	public void addPathByLatLng(double lat1, double lng1, double lat2, double lng2){
		lng1 = MapUtil.getLeft(SVGMapPanel.WIDTH, lng1);
		lat1 = MapUtil.getTop(SVGMapPanel.HEIGHT, lat1);
		lng2 = MapUtil.getLeft(SVGMapPanel.WIDTH, lng2);
		lat2 = MapUtil.getTop(SVGMapPanel.HEIGHT, lat2);

		 // Create a circle
        final OMSVGCircleElement circle1 = doc.createSVGCircleElement((float)lng1, (float)lat1, 8f);
        final OMSVGCircleElement circle2 = doc.createSVGCircleElement((float)lng2, (float)lat2, 8f);
//        circle.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_PROPERTY, SVGConstants.CSS_BLACK_VALUE);
        circle1.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, SVGConstants.CSS_RED_VALUE);
        circle2.getStyle().setSVGProperty(SVGConstants.CSS_FILL_PROPERTY, SVGConstants.CSS_RED_VALUE);
        circle1.getStyle().setSVGProperty(SVGConstants.CSS_FILL_OPACITY_PROPERTY, "0.4");
        circle2.getStyle().setSVGProperty(SVGConstants.CSS_FILL_OPACITY_PROPERTY, "0.4");

        OMSVGPathElement pathElement = doc.createSVGPathElement();
//		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_VALUE, "#3B5998");
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_VALUE, "red");
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_WIDTH_PROPERTY, "0.6");
		pathElement.getStyle().setSVGProperty(SVGConstants.CSS_STROKE_OPACITY_PROPERTY, "0.6");
//		style="stroke-opacity: 0.4;
		
//		pathElement.setAttribute("transform", "translate(0,239) scale(0.016963, 0.016963)");
		pathElement.setAttribute("d", "M"+lng1+" "+lat1+" L "+lng2+" "+lat2+"");
//		pathElement.setAttribute("stroke-dasharray", "10,10");
		
		
		mapSvg.appendChild(pathElement);
		mapSvg.appendChild(circle1);
		mapSvg.appendChild(circle2);
	}
	
}
