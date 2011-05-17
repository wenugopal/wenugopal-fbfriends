package com.wenugopal.apps.fbfriends.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;

public class CustomScrollPanel extends Composite {

	private FlowPanel horizontalFlowPanel = null;
	private FlowPanel locationPanel = null;
	private HorizontalPanel locationOptionsPanel = null;

	private ListBox listBox = null;
	private ListBox options = null;

	private Set<String> locations = null;
	private Set<String> countries = null;
	
	private List<String> locationsSortedList = null;
	private List<String> countriesSortedList = null;

	private Label noOfFriendsValue = null;

	public CustomScrollPanel(){
		locationPanel = new FlowPanel();
		horizontalFlowPanel = new FlowPanel();
		noOfFriendsValue = new Label();

		initializeLocationOptions();
		locationPanel.add(locationOptionsPanel);
		locationPanel.add(horizontalFlowPanel);
		initWidget(locationPanel);
	}

	private void initializeLocationOptions() {
		Label filterBy = new Label("Filter By : ");
		Label noOfFriends = new Label("Friends : ");

		noOfFriends.setStyleName("noOfFriends");
		noOfFriendsValue.setStyleName("noOfFriendsValue");
		
		filterBy.setStyleName("filterBy");
		locationOptionsPanel = new HorizontalPanel();
		locationOptionsPanel.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);

		listBox = new ListBox();
		options = new ListBox();

		options.addItem("Country");
		options.addItem("Location");

		listBox.addStyleName("listBox");
		options.addStyleName("listBox");

		horizontalFlowPanel.setStyleName("customScrollBar");
		locationPanel.setStyleName("locationListBoxPanel");
		locationOptionsPanel.addStyleName("locationListBox");

		locations = new HashSet<String>();
		countries = new HashSet<String>();

		locationsSortedList = new ArrayList<String>();
		countriesSortedList = new ArrayList<String>();
		
		locationOptionsPanel.add(filterBy);
		locationOptionsPanel.add(options);
		locationOptionsPanel.add(listBox);
		locationOptionsPanel.add(noOfFriends);
		locationOptionsPanel.add(noOfFriendsValue);

		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int selectedIndex = listBox.getSelectedIndex();
				if (selectedIndex >= 0) {
					if(listBox.getValue(selectedIndex).equalsIgnoreCase("All")) {
						showAll(true);
					} else if(listBox.getValue(selectedIndex).equalsIgnoreCase("Unknown")) {
						showAll(false);
					} else {
						show(listBox.getValue(selectedIndex));
					}
				}
			}
		});


		options.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int selectedIndex = options.getSelectedIndex();
				if (selectedIndex >= 0) {
					if(options.getValue(selectedIndex).equalsIgnoreCase("Country")) {
						updateListBox(countriesSortedList);
						showAll(true);
					} else if(options.getValue(selectedIndex).equalsIgnoreCase("Location")) {
						updateListBox(locationsSortedList);
						showAll(true);
					}
				}
			}
		});

	}


	public void updateListBox(Collection<String> options) {
		listBox.clear();
		listBox.addItem("All");
		listBox.addItem("Unknown");

		for(String detail  : options) {
			if(detail != null) {
				listBox.addItem(detail);
			}
		}
	}

	public CustomScrollPanel setData(List<FQLFriendsDetails> data) {
		listBox.addItem("All");
		listBox.addItem("Unknown");
		if(data != null) {
			int size = data.size();
			noOfFriendsValue.setText(size+"");
		}

		for(FQLFriendsDetails detail  : data) {
			if(detail.getCurrent_location() != null) {
				if(detail.getCurrent_location().getCountry() != null && countries.add(detail.getCurrent_location().getCountry())) {
					listBox.addItem(detail.getCurrent_location().getCountry());
				}

				if(locations.add(detail.getCurrent_location().toString())) {
					//					listBox.addItem(detail.getCurrent_location().toString());
				}
			}

			horizontalFlowPanel.add(new FormattedFriendView().setData(detail));
		}

		
		// add options to an array and sort the list box options.
		locationsSortedList.addAll(locations);
		countriesSortedList.addAll(countries);
		
		Collections.sort(locationsSortedList);
		Collections.sort(countriesSortedList);
		
		updateListBox(countriesSortedList);
		
		return this;
	}

	public void show(String location) {
		int size = 0;
		if(this.horizontalFlowPanel != null) {
			int count = this.horizontalFlowPanel.getWidgetCount();
			for (int i = 0; i < count; i++) {
				Widget widget = horizontalFlowPanel.getWidget(i);
				if(widget instanceof FormattedFriendView) {
					FormattedFriendView friendView = (FormattedFriendView)widget;
					if(friendView.getDetails() != null) {
						if(friendView.getDetails().getCurrent_location() != null &&  friendView.getDetails().getCurrent_location().toString().contains(location)){
							widget.setVisible(true);
							size++;
						} else {
							widget.setVisible(false);
						}
					}
				}
			}
		}
		noOfFriendsValue.setText(size+"");
	}

	public void showAll(boolean isAll) {
		int size = 0;
		if(this.horizontalFlowPanel != null) {
			int count = this.horizontalFlowPanel.getWidgetCount();
			for (int i = 0; i < count; i++) {
				Widget widget = horizontalFlowPanel.getWidget(i);

				if(widget instanceof FormattedFriendView) {
					FormattedFriendView friendView = (FormattedFriendView)widget;
					if(isAll) {
						widget.setVisible(true);
						size++;
					} else if (friendView.getDetails() != null && friendView.getDetails().getCurrent_location() == null) {
						widget.setVisible(true);
						size++;
					} else {
						widget.setVisible(false);
					}
				}
			}
		}
		noOfFriendsValue.setText(size+"");
	}

	public List<FQLFriendsDetails> getDetials(String location) {
		List<FQLFriendsDetails> detailsList = null;
		if(this.horizontalFlowPanel != null) {
			int count = this.horizontalFlowPanel.getWidgetCount();
			detailsList = new ArrayList<FQLFriendsDetails>();
			for (int i = 0; i < count; i++) {
				Widget widget = horizontalFlowPanel.getWidget(i);

				if(widget instanceof FormattedFriendView) {
					FormattedFriendView friendView = (FormattedFriendView)widget;
					if(friendView.getDetails() != null) {
						if(friendView.getDetails().getCurrent_location() != null &&  friendView.getDetails().getCurrent_location().toString().equalsIgnoreCase(location)){
							detailsList.add(friendView.getDetails());
						}
					}
				}
			}
		}
		return detailsList;
	}
}
