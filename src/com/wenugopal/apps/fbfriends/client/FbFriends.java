package com.wenugopal.apps.fbfriends.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.vectomatic.dom.svg.OMSVGPoint;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.BarChart;
import com.wenugopal.apps.fbfriends.client.dd.FQLFriendsDetailsResponseDecoder;
import com.wenugopal.apps.fbfriends.client.dd.IsFriendsDecoder;
import com.wenugopal.apps.fbfriends.client.dd.MapQuestLocationDecoder;
import com.wenugopal.apps.fbfriends.client.dd.SessionDecoder;
import com.wenugopal.apps.fbfriends.client.dd.TGCLocationDecoder;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetailsMap;
import com.wenugopal.apps.fbfriends.client.dto.Location;
import com.wenugopal.apps.fbfriends.client.dto.SessionStatus;
import com.wenugopal.apps.fbfriends.client.dto.TGCLocation;
import com.wenugopal.apps.fbfriends.client.factory.ImageFactory;
import com.wenugopal.apps.fbfriends.client.fb.core.FBCore;
import com.wenugopal.apps.fbfriends.client.fb.core.FBEvent;
import com.wenugopal.apps.fbfriends.client.fb.core.FBXfbml;
import com.wenugopal.apps.fbfriends.client.fb.view.FBButton;
import com.wenugopal.apps.fbfriends.client.fb.view.FBSocialPlugin;
import com.wenugopal.apps.fbfriends.client.fb.view.FacePile;
import com.wenugopal.apps.fbfriends.client.geocoding.service.MapQuestApi;
import com.wenugopal.apps.fbfriends.client.geocoding.service.TinyGeoCoderApi;
import com.wenugopal.apps.fbfriends.client.utils.MapUtil;
import com.wenugopal.apps.fbfriends.client.utils.UIUtils;
import com.wenugopal.apps.fbfriends.client.view.CustomScrollPanel;
import com.wenugopal.apps.fbfriends.client.view.FormattedContentView;
import com.wenugopal.apps.fbfriends.client.view.FriendsScrollPanelView;
import com.wenugopal.apps.fbfriends.client.view.LoginView;
import com.wenugopal.apps.fbfriends.client.view.MapPanel;
import com.wenugopal.apps.fbfriends.client.view.SVGMapPanel;
import com.wenugopal.apps.fbfriends.client.view.StatsView;


/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */

//TODO: this class is becoming messier; clean it up!
public class FbFriends implements EntryPoint {

	private String APP_ID = "164977740229619";
	private String WENUGOPAL = "561226677";
	private static String MAX_FRIENDS = "1000";
	private final FBCore fbCore = GWT.create(FBCore.class);
	private final FBEvent fbEvent =  GWT.create(FBEvent.class);
	private final FBXfbml fbXfbml =  GWT.create(FBXfbml.class);
	private FQLFriendsDetailsMap fqlFriendsDetailsMap;
	private  Map<Location, String> locationIdMap = new HashMap<Location, String>();
	private SVGMapPanel svgMapPanel = null;
	private Label infoLabel = null;
	private String uid = null;
	private FQLFriendsDetails currentUserDetails = null;
	private TGCLocation userTgcLocation = null;
	private CustomScrollPanel customScrollPanelView = null;
	private CheckBox pathBox = null;
	HorizontalPanel likeLogoutPanel = null;
	private Anchor statsAnchor = new Anchor("Stats");
	
	public void onModuleLoad() {
		RootPanel.get("main").addStyleName("main");
		initFBCore();
		RootPanel.getBodyElement().getStyle().setMargin(0, Unit.PX);
		RootPanel.getBodyElement().getStyle().setPadding(0, Unit.PX);
	}

	private void initFBCore() {
		fbCore.init(APP_ID, true, true, true);
		fbCore.getLoginStatus(new AsyncCallback<JavaScriptObject>() {
			public void onSuccess(JavaScriptObject result) {
				SessionStatus sessionStatus = SessionDecoder.decode(result);
				if (sessionStatus.getSession() != null) {
					//					Window.alert(sessionStatus.getStatus());
					uid = sessionStatus.getSession().getUid();
					initLikeLogoutPanel();
					initInfoDivArea();
					initMapArea();
					initSrollPanel();
					//					loadFriendsAndLatLng();
					getFriendsDetails();
				}
				else {
					initLoginView();
				}
				
				fbXfbml.parse();
			}

			public void onFailure(Throwable caught) {
				Window.alert("failed to call loginstatus");
			}
		});


		fbEvent.subscribe("auth.login", new AsyncCallback<JavaScriptObject>() {

			public void onSuccess(JavaScriptObject result) {
				//				Window.alert("auth.login is invoked");
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

	protected void loadFriendsAndLatLng() {
		//		initGetFriendsDetailsButton();
		//		initGetLatLngButton();
		getFriendsDetails();
	}

	protected void initSrollPanel() {
		customScrollPanelView = new CustomScrollPanel();
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
				getLocations();
			}
		});
		RootPanel.get("friendsDiv").add(getLatLngButton);
	}

	protected void getLocations(){
		RootPanel.get("friendscontent").clear();
		getCurrentUserLocation();
	}
	protected void getCurrentUserLocation() {
		if(this.uid != null) {
			//			currentUserDetails = fqlFriendsDetailsMap.get(this.uid);
			currentUserDetails = fqlFriendsDetailsMap.get(this.uid);
			if(currentUserDetails != null && currentUserDetails.getCurrent_location() != null) {
				getLatLngFromMapQuestForCurrentUser(currentUserDetails.getCurrent_location(), true);
			} else {
				updateFriendsLocationMapViaMapService();
			}
		}
	}

	private void initLoginView() {
		LoginView loginView = new LoginView();
		loginView.initLoginView(fbCore);
		RootPanel.get("main").add(loginView);
	}

	private Anchor getLogoutButton() {
		Anchor logout = FBButton.getFBButton("Logout");
		logout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fbCore.logout(new AsyncCallback<JavaScriptObject>() {

					public void onSuccess(JavaScriptObject result) {
//												Window.alert("logged out");
					}

					public void onFailure(Throwable caught) {
						//						Window.alert("Error while logging out.");
					}
				});
			}
		});
		return logout;
	}

	private void initLikeLogoutPanel() {
		likeLogoutPanel = new HorizontalPanel();
		likeLogoutPanel.getElement().getStyle().setRight(0, Unit.PX);
		likeLogoutPanel.getElement().getStyle().setTop(0, Unit.PX);
		pathBox = new CheckBox("Hide Paths");
		pathBox.addStyleName("marginRight");
		pathBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(pathBox.getValue()) {
					svgMapPanel.hidePaths();
				} else {
					svgMapPanel.unHidePaths();
				}
			}
		});
		addZoomOptions();
		likeLogoutPanel.add(pathBox);
		addCommentsDocsLinks();
		likeLogoutPanel.add(FBSocialPlugin.getLikeSendHtml(GWT.getHostPageBaseURL()));
		//		likeLogoutPanel.add(getLogoutButton());
		RootPanel.get("loginlogout").add(likeLogoutPanel);
	}


	private void addZoomOptions() {
		Image zoomIn = new Image("Zoom_In.png");
		Image zoomOut = new Image("Zoom_Out.png");
		zoomIn.addStyleName("cursorHand");
		zoomOut.addStyleName("cursorHand");
		zoomIn.getElement().getStyle().setMarginRight(8, Unit.PX);
		zoomOut.getElement().getStyle().setMarginRight(20, Unit.PX);
		zoomIn.setSize("20px", "20px");
		zoomOut.setSize("20px", "20px");
		
		zoomIn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				svgMapPanel.scale(4, getMidPoint());
			}
		});
		
		zoomOut.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				svgMapPanel.scale(-4, getMidPoint());
			}
		});
		
		likeLogoutPanel.add(zoomIn);
		likeLogoutPanel.add(zoomOut);
		
	}
	
	private OMSVGPoint getMidPoint() {
		OMSVGPoint point = svgMapPanel.getSvgMap().createSVGPoint();
		point.setX(Window.getClientWidth()/2);
		point.setY(Window.getClientHeight()/2);
		return point;
	}

	private void initGetFriendsDetailsButton() {
		Button queryButton = new Button("Get Friends Details");
		queryButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				getFriendsDetails();
			}
		});
		RootPanel.get("friendsDiv").add(queryButton);
	}

	public void getFriendsDetails() {
		RootPanel.get("friendscontent").clear();

		infoLabel.setVisible(true);
		infoLabel.setText("Loading Friends Details..");

		configureFBquery();
	}

	private void configureFBquery() {
		String method = "fql.query";
		String fql = "SELECT uid, name, pic_small, pic, pic_square, sex, hometown_location, current_location, profile_url, username FROM user WHERE uid = me() OR uid IN (SELECT uid2 FROM friend WHERE uid1 = me())  Limit "+MAX_FRIENDS;
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
			
			// Store location and corresponding user ids to optimize the number of requests sent to location service.
			if (entry.getValue().getCurrent_location() != null && entry.getValue().getCurrent_location().toString().trim().length() > 0) {
				updateCountryIdMap(entry);
				if(locationIdMap.containsKey(entry.getValue().getCurrent_location())) {
					String idString = locationIdMap.get(entry.getValue().getCurrent_location());
					idString = idString+entry.getKey()+",";
					locationIdMap.put(entry.getValue().getCurrent_location(), idString);
				} else {
					locationIdMap.put(entry.getValue().getCurrent_location(), entry.getKey()+",");
				}
			}
		}
		enableStats();
		getLatLngFromMapQuest();
	}

	private void enableStats() {
		statsAnchor.setEnabled(true);
	}

	private void updateCountryIdMap (Entry<String, FQLFriendsDetails> entry) {
		if (entry.getValue().getCurrent_location() != null && entry.getValue().getCurrent_location().getCountry()!= null && entry.getValue().getCurrent_location().toString().trim().length() > 0) {
			if(countryIdMap.containsKey(entry.getValue().getCurrent_location().getCountry())) {
				String idString = countryIdMap.get(entry.getValue().getCurrent_location().getCountry());
				idString = idString+entry.getKey()+",";
				countryIdMap.put(entry.getValue().getCurrent_location().getCountry(), idString);
			} else {
				countryIdMap.put(entry.getValue().getCurrent_location().getCountry(), entry.getKey()+",");
			}
		}
	}
	
	private void getLatLngFromMapQuest() {
		Set<Entry<Location, String>> locationIds = locationIdMap.entrySet();
		Iterator<Entry<Location, String>> it = locationIds.iterator();
		for (int i = 0; it.hasNext(); i++) {
			Entry<Location, String> entry = it.next();
			getLatLngFromMapQuest(entry.getKey()); 
		}
	}
	
	
	private void getLatLngFromMapQuest(final Location location) {
		new MapQuestApi().send(location, new AsyncCallback<JavaScriptObject>() {

			public void onFailure(Throwable caught) {
//				Window.alert("Error occured while requesting MAPQuest");
			}
			
			public void onSuccess(JavaScriptObject result) {
				TGCLocation tgcLocation = MapQuestLocationDecoder.decode(result);
				updateLocationUi(tgcLocation, location);
			}
		});
	}

	
	

	protected void updateLocationUi(TGCLocation tgcLocation, Location location) {
		String idsString = locationIdMap.get(location);
		String ids[] = idsString.split(",");
		for(String id : ids) {
			if (id != null && id.trim().length() > 0 && tgcLocation != null && svgMapPanel !=null)
				if(FbFriends.this.userTgcLocation != null) {
					if(FbFriends.this.currentUserDetails != null) {
						if(FbFriends.this.currentUserDetails.getCurrent_location().toString().equalsIgnoreCase(location.toString())) {
							svgMapPanel.addCircleByLatLng(tgcLocation.getLatitude(), tgcLocation.getLongitude(), true, getFacePilePopup(location.toString()));
						} else {
							svgMapPanel.addPathByLatLng(FbFriends.this.userTgcLocation.getLatitude(), FbFriends.this.userTgcLocation.getLongitude(), tgcLocation.getLatitude(), tgcLocation.getLongitude(), getFacePilePopup(location.toString()));
						}
					}
				} else {
					svgMapPanel.addCircleByLatLng(tgcLocation.getLatitude(), tgcLocation.getLongitude(), false, getFacePilePopup(location.toString()));
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

	
	protected Widget getFacePilePopup(String location) {
		FacePile facePile = new FacePile();
		facePile.setData(this.customScrollPanelView.getDetials(location));
		return facePile;
	}

	private void getLatLngFromMapQuestForCurrentUser(final Location location, final boolean me) {
		new MapQuestApi().send(location, new AsyncCallback<JavaScriptObject>() {

			public void onFailure(Throwable caught) {
				Window.alert("Error occured while requesting MAPQuest");
			}

			public void onSuccess(JavaScriptObject result) {
				TGCLocation tgcLocation = MapQuestLocationDecoder.decode(result);
				if (tgcLocation != null && svgMapPanel !=null)
					FbFriends.this.userTgcLocation = tgcLocation;
				svgMapPanel.addCircleByLatLng(tgcLocation.getLatitude(), tgcLocation.getLongitude(), me, getFacePilePopup(location.toString()));
				updateFriendsLocationMapViaMapService();
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
			if(!uid.equals(WENUGOPAL)) {
				updateIfFriends();
			}
			updateCustomScrollPanelUI(fqlFriendsDetailsMap);
			// Added below to get friends and locations on module load.
			getLocations();
		}
	}

	class IsFriendsFBQueryCallback implements AsyncCallback<JavaScriptObject> {
		public void onFailure(Throwable caught) {
			Window.alert("Error occured while executing FBQueryCallback");
		}

		public void onSuccess(JavaScriptObject result) {
			boolean isFriends = IsFriendsDecoder.decode(result);
			if(!isFriends) {
				updateAddVenuUi();
			}
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

	public void updateAddVenuUi() {
		Anchor addVenuLabel = new Anchor("Add Venu as Friend");
		addVenuLabel.addStyleName("addVenu");
		addVenuLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.open("https://www.facebook.com/wenugopal", "_blank", null);
			}
		});
		RootPanel.get().add(addVenuLabel);
	}

	public void addCommentsDocsLinks() {
		initStats();
		statsAnchor.setEnabled(false);
		statsAnchor.addStyleName("marginRight");
		statsAnchor.addStyleName("anchorStyle");
		likeLogoutPanel.add(statsAnchor);

		Anchor commentsAnchor = new Anchor("Comment");
		commentsAnchor.addStyleName("marginRight");
		commentsAnchor.addStyleName("anchorStyle");
		commentsAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(GWT.getHostPageBaseURL()+"comments.html", "_blank", null);
			}
		});
		likeLogoutPanel.add(commentsAnchor);

		Anchor docssAnchor = new Anchor("About");
		docssAnchor.addStyleName("marginRight");
		docssAnchor.addStyleName("anchorStyle");
		docssAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(GWT.getHostPageBaseURL()+"about.html", "_blank", null);
			}
		});
		likeLogoutPanel.add(docssAnchor);
	}

	public void updateIfFriends() {
		String method = "fql.query";
		String fql = "SELECT uid1, uid2 from friend where uid1="+uid+" and uid2="+WENUGOPAL;
		JSONObject query = new JSONObject();
		query.put("method", new JSONString(method));
		query.put("query", new JSONString(fql));
		fbCore.api(query.getJavaScriptObject(), new IsFriendsFBQueryCallback());
	}

	public void updateScrollPanelUI(FQLFriendsDetailsMap fqlFriendsDetailsMap) {
		List<FQLFriendsDetails> friendsDetailsList = new ArrayList<FQLFriendsDetails>(fqlFriendsDetailsMap.values());
		FriendsScrollPanelView friendsScrollPanelView = new FriendsScrollPanelView();
		friendsScrollPanelView.setData(friendsDetailsList);
		RootPanel.get("main").add(friendsScrollPanelView);
	}

	public void updateCustomScrollPanelUI(FQLFriendsDetailsMap fqlFriendsDetailsMap) {
		List<FQLFriendsDetails> friendsDetailsList = new ArrayList<FQLFriendsDetails>(fqlFriendsDetailsMap.values());
		customScrollPanelView.setData(friendsDetailsList);
		RootPanel.get().add(customScrollPanelView);
	}

	private DialogBox box = new DialogBox();
	private FlowPanel panel = new FlowPanel();
	private StatsView statsView = null;
	private Label label = new Label("Loading...");
	private HorizontalPanel loadingPanel = null;
	private final Button close = new Button("Close Stats");
	private  Map<String, String> countryIdMap = new HashMap<String, String>();
	
	private void initStats(){
		box.setWidget(panel);
		label.getElement().getStyle().setMarginLeft(10, Unit.PX);
		loadingPanel = new HorizontalPanel();
		Image loadingImg = ImageFactory.createCustomUrlImage("loader.gif", "15px", "15px");
		loadingPanel.add(loadingImg);
		loadingPanel.add(label);
		
//		box.setWidth("951px");
//		box.setHeight((locationIdMap.size()*40)+"px");
		box.setGlassEnabled(true);
		box.center();
		box.getElement().getStyle().setTop(20, Unit.PX);
		box.hide();

//		panel.addStyleName("");
		statsAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				box.center();
				panel.clear();
				close.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						box.hide();
					}
				});
				close.getElement().getStyle().setPosition(Position.RELATIVE);
				close.setVisible(false);
				panel.add(loadingPanel);
				panel.add(close);
				loadPieChart();
			}
		});
	}

	private void loadPieChart(){
		loadingPanel.setVisible(true);
		close.setVisible(false);
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			public void run() {
				panel.add(new StatsView().setData(fqlFriendsDetailsMap, countryIdMap));
				loadingPanel.setVisible(false);
				box.getElement().getStyle().setLeft((Window.getClientWidth() > 950 ? (Window.getClientWidth() - 950)/2 : 0), Unit.PX);
				close.getElement().getStyle().setLeft(390, Unit.PX);
				close.setVisible(true);
				box.getElement().getStyle().setTop(20, Unit.PX);
			}}, BarChart.PACKAGE);
	}

}
