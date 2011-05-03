package com.wenugopal.apps.fbfriends.client.view;

import org.vectomatic.dom.svg.ui.SVGResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface SvgMapBundle extends ClientBundle {
    public static SvgMapBundle INSTANCE = GWT.create(SvgMapBundle.class);
    @Source("mozMap.xml")
    SVGResource map();
}