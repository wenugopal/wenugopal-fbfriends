package com.wenugopal.apps.fbfriends.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.wenugopal.apps.fbfriends.client.dd.FQLFriendsDetailsResponseDecoder;
import com.wenugopal.apps.fbfriends.client.dd.MapQuestLocationDecoder;
import com.wenugopal.apps.fbfriends.client.dd.SessionDecoder;
import com.wenugopal.apps.fbfriends.client.dd.TGCLocationDecoder;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetailsMap;
import com.wenugopal.apps.fbfriends.client.dto.Location;
import com.wenugopal.apps.fbfriends.client.dto.SessionStatus;
import com.wenugopal.apps.fbfriends.client.dto.TGCLocation;
import com.wenugopal.apps.fbfriends.client.fb.core.FBCore;
import com.wenugopal.apps.fbfriends.client.fb.core.FBEvent;
import com.wenugopal.apps.fbfriends.client.fb.view.FBButton;
import com.wenugopal.apps.fbfriends.client.geocoding.service.MapQuestApi;
import com.wenugopal.apps.fbfriends.client.geocoding.service.TinyGeoCoderApi;
import com.wenugopal.apps.fbfriends.client.utils.MapUtil;
import com.wenugopal.apps.fbfriends.client.utils.UIUtils;
import com.wenugopal.apps.fbfriends.client.view.FormattedContentView;
import com.wenugopal.apps.fbfriends.client.view.FriendsScrollPanelView;
import com.wenugopal.apps.fbfriends.client.view.MapPanel;
import com.wenugopal.apps.fbfriends.client.view.SVGMapPanel;


/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class FbFriends implements EntryPoint {

	private String APP_ID = "139642032774480";
	private final FBCore fbCore = GWT.create(FBCore.class);
	private final FBEvent fbEvent =  GWT.create(FBEvent.class);
	private FQLFriendsDetailsMap fqlFriendsDetailsMap;
	public static Map<String, String> idLocationMap = new HashMap<String, String>();
	private MapPanel mapPanel = null;
	private SVGMapPanel svgMapPanel = null;
	private Label infoLabel = null;
	private String uid = null;
	private TGCLocation uidLocation = null;
	
	public void onModuleLoad() {
		RootPanel.get("main").addStyleName("main");
		initFBCore();
	}

	private void initFBCore() {
		fbCore.init(APP_ID, true, true, true);
		fbCore.getLoginStatus(new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject result) {
				SessionStatus sessionStatus = SessionDecoder.decode(result);
				if (sessionStatus.getSession() != null) {
					//					Window.alert(sessionStatus.getStatus());
					uid = sessionStatus.getSession().getUid();
					initLogoutButton();
					initInfoDivArea();
					initGetFriendsDetailsButton();
					initGetLatLngButton();
					initMapArea();
				}
				else {
					initLoginButton();
				}
			}

			public void onFailure(Throwable caught) {
				Window.alert("failed to call loginstatus");
			}
		});


		fbEvent.subscribe("auth.login", new AsyncCallback<JavaScriptObject>() {

			public void onSuccess(JavaScriptObject result) {
				Window.alert("auth.login is invoked");
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error while logging in..");
			}
		});


		fbEvent.subscribe("auth.logout", new AsyncCallback<JavaScriptObject>() {

			public void onSuccess(JavaScriptObject result) {
				//				Window.alert("Event after logout");
				Window.Location.reload();
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error while logging out");
			}
		});
	}

	protected void initInfoDivArea() {
		FlowPanel infoDiv = new FlowPanel();
		infoDiv.setStylePrimaryName("infoDiv");
		this.infoLabel = new Label();
		this.infoLabel.setStylePrimaryName("infoLabel");
		this.infoLabel.setVisible(false);
		infoDiv.add(this.infoLabel);
		RootPanel.get().add(infoDiv);
	}

	protected void initMapArea() {
//		mapPanel = new MapPanel();
		svgMapPanel = new SVGMapPanel();
		RootPanel.get("main").add(svgMapPanel);
	}

	private void initGetLatLngButton() {
		Button getLatLngButton = new Button("Get Latitude and Longitudes");
		getLatLngButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				RootPanel.get("friendscontent").clear();
				updateFriendsLocationMapViaMapService();
			}
		});
		RootPanel.get("friendsDiv").add(getLatLngButton);
	}

	private void initLoginButton() {
		Anchor loginButton = FBButton.getFBButton("Login with Facebook");
		loginButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				fbCore.login(new AsyncCallback<JavaScriptObject>() {

					public void onSuccess(JavaScriptObject result) {
						//						Window.alert("logged in");
						Window.Location.reload();
					}

					public void onFailure(Throwable throwable) {
					}
				}, "user_location, friends_location");
			}
		});
		RootPanel.get("loginlogout").add(loginButton);
	}

	private void initLogoutButton() {
		Anchor logout = FBButton.getFBButton("Logout from Facebook");
		logout.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				fbCore.logout(new AsyncCallback<JavaScriptObject>() {

					public void onSuccess(JavaScriptObject result) {
						//						Window.alert("logged out");

					}

					public void onFailure(Throwable caught) {
						//						Window.alert("Error while logging out.");
					}
				});
			}

		});
		RootPanel.get("loginlogout").add(logout);
	}

	private void initGetFriendsDetailsButton() {
		Button queryButton = new Button("Get Friends Details");
		queryButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				RootPanel.get("friendscontent").clear();
				
				infoLabel.setVisible(true);
				infoLabel.setText("Getting Friends Details..");
				
				configureFBquery();
			}
		});
		RootPanel.get("friendsDiv").add(queryButton);
	}


	private void configureFBquery() {
		String method = "fql.query";
		String fql = "SELECT uid, name, pic_small, pic, pic_square, sex, hometown_location, current_location, profile_url, username FROM user WHERE uid = me() OR uid IN (SELECT uid2 FROM friend WHERE uid1 = me()) ";
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));
		fbCore.api(query.getJavaScriptObject(), new FBQueryCallback());
	}


	public void updateFriendsLocationMapViaMapService() {
		Set<Entry<String, FQLFriendsDetails>> friendsDetailsList = fqlFriendsDetailsMap.entrySet();
		Iterator<Entry<String, FQLFriendsDetails>> it = friendsDetailsList.iterator();
		for (int i = 0; it.hasNext(); i++) {
			Entry<String, FQLFriendsDetails> entry = it.next();
			if (((FQLFriendsDetails) entry.getValue()).getCurrent_location() != null
					&& ((FQLFriendsDetails) entry.getValue()).getCurrent_location().toString().trim().length() > 0) {
				idLocationMap.put(entry.getKey(),(entry.getValue()).getCurrent_location().toString());
				getLatLngFromMapQuest(entry.getValue().getCurrent_location());
			}
		}
	}

	@SuppressWarnings("unused")
	private void getLatLngFromTGC(final String locationString, int i, FlexTable flexTable) {
		(new TinyGeoCoderApi()).send(locationString, new AsyncCallback<JavaScriptObject>() {

			public void onFailure(Throwable throwable) {
			}

			public void onSuccess(JavaScriptObject result) {
				TGCLocation tgcLocation = TGCLocationDecoder.decode(result);
				RootPanel.get("mapArea").add(
						UIUtils.getLocationDiv(locationString, 
								MapUtil.getTop(MapPanel.HEIGHT,tgcLocation.getLatitude()),
								MapUtil.getLeft(MapPanel.WIDTH,tgcLocation.getLongitude())));
			}
		});
	}

	private void getLatLngFromMapQuest(final Location location) {
		new MapQuestApi().send(location, new AsyncCallback<JavaScriptObject>() {

			public void onFailure(Throwable caught) {
				Window.alert("Error occured while requesting MAPQuest");
			}

			public void onSuccess(JavaScriptObject result) {
				TGCLocation tgcLocation = MapQuestLocationDecoder.decode(result);
				if (tgcLocation != null && svgMapPanel !=null)
//					mapPanel.addLocationWidget(
//							UIUtils.getLocationDiv(
//									location.toString(),
//									MapUtil.getTop(MapPanel.HEIGHT,tgcLocation.getLatitude()),
//									MapUtil.getLeft(MapPanel.WIDTH,tgcLocation.getLongitude())));
					svgMapPanel.addPathByLatLng(17.385044, 78.486671, tgcLocation.getLatitude(), tgcLocation.getLongitude());
					
			}
		});
	}

	

	class FBQueryCallback implements AsyncCallback<JavaScriptObject> {
		public void onFailure(Throwable caught) {
			Window.alert("Error occured while executing FBQueryCallback");
		}

		public void onSuccess(JavaScriptObject result) {
			RootPanel.get("friendscontent").clear();
			infoLabel.setVisible(false);
			fqlFriendsDetailsMap = FQLFriendsDetailsResponseDecoder.decode(result);
			updateScrollPanelUI(fqlFriendsDetailsMap);
		}
	}


	public void updateFQLFriendsDetailsUI(FQLFriendsDetailsMap fqlFriendsDetailsMap) {
		Set<Entry<String, FQLFriendsDetails>> friendsDetailsList = fqlFriendsDetailsMap.entrySet();
		Iterator<Entry<String, FQLFriendsDetails>> it = friendsDetailsList.iterator();
		FlowPanel friendsPanel = new FlowPanel();
		
		for (int i = 0; it.hasNext(); i++) {
			Entry<String, FQLFriendsDetails> entry = it.next();
			FormattedContentView formattedView = new FormattedContentView();
			formattedView.setData(entry.getValue());
			friendsPanel.add(formattedView);
		}
		RootPanel.get("friendscontent").add(friendsPanel);
	}
	
	public void updateScrollPanelUI(FQLFriendsDetailsMap fqlFriendsDetailsMap) {
		List<FQLFriendsDetails> friendsDetailsList = new ArrayList<FQLFriendsDetails>(fqlFriendsDetailsMap.values());
		FriendsScrollPanelView friendsScrollPanelView = new FriendsScrollPanelView();
		friendsScrollPanelView.setData(friendsDetailsList);
		RootPanel.get("main").add(friendsScrollPanelView);
	}
	
}
