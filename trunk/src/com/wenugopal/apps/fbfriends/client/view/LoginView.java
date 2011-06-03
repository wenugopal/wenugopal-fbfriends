package com.wenugopal.apps.fbfriends.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wenugopal.apps.fbfriends.client.fb.core.FBCore;
import com.wenugopal.apps.fbfriends.client.fb.view.FBButton;
import com.wenugopal.apps.fbfriends.client.fb.view.FBSocialPlugin;

public class LoginView extends Composite {

	private Anchor loginButton = null;
	
	private VerticalPanel vPanel = null;
	
	private FlowPanel welcomPanel = null;
	
	private FlowPanel footerPanel = null;
	
	private Label spaceLabel = null;
	
	public LoginView() {
		super();
		vPanel = new VerticalPanel();
		welcomPanel = new FlowPanel();
		footerPanel = new FlowPanel();
		spaceLabel = new Label("");
		spaceLabel.addStyleName("spaceLabel");
		loginButton = FBButton.getFBButton("Login with Facebook");
		vPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		vPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		
		
		initWidget(vPanel);
		vPanel.add(welcomPanel);
		vPanel.add(loginButton);
		vPanel.add(spaceLabel);
		vPanel.add(FBSocialPlugin.getFacePile());
		vPanel.add(footerPanel);
	}

	public void initLoginView(final FBCore fbCore) {
		loginButton.addStyleName("loginAnchor");
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fbCore.login(new AsyncCallback<JavaScriptObject>() {

					public void onSuccess(JavaScriptObject result) {
						Window.Location.reload();
					}

					public void onFailure(Throwable throwable) {
					}
				}, "user_location, friends_location");
			}
		});
		initWelcomePanel();
		initFooter();
	}

	private void initFooter() {
		footerPanel.addStyleName("footerPanel");
		Label footerText = new Label("Chek out the self-explaining slideshow about this app ");
		footerText.setStyleName("footerText");
		
		Anchor hereAnchor = new Anchor("here.");
		hereAnchor.addStyleName("here");
		hereAnchor.addStyleName("anchorStyle");
		hereAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(GWT.getHostPageBaseURL()+"screenshots.html", "_blank", null);
				
			}
		});
		
		
		Label worksOnlyText = new Label("Works only on Chrome 10+, Firefox 3.6+, iPad, iPhone 4 browsers.");
		worksOnlyText.setStyleName("worksOnlyText");
		
		footerPanel.add(footerText);
		footerPanel.add(hereAnchor);
		footerPanel.add(worksOnlyText);
	}

	private void initWelcomePanel() {
		welcomPanel.setStyleName("welcomePanel");
		Label welcomeToText = new Label("Welcome to \"World of Friends\" !");
		welcomeToText.setStyleName("welcomeToText");
		
		Label welcomeText = new Label("Find out where your Facebook friends are located around the World. Filter your Friends by Country and by their Location as updated in Facebook.");
		welcomeText.setStyleName("welcomeText");
		
		Label loginText = new Label("Please login with facebook to continue.");
		loginText.setStyleName("loginText");
		
		welcomPanel.add(welcomeToText);
		welcomPanel.add(welcomeText);
		welcomPanel.add(loginText);
	}
	
}
