package com.wenugopal.apps.fbfriends.client.view;

import java.util.ArrayList;
import java.util.Collection;
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

	public CustomScrollPanel(){
		locationPanel = new FlowPanel();
		horizontalFlowPanel = new FlowPanel();
		initializeLocationOptions();
		locationPanel.add(locationOptionsPanel);
		locationPanel.add(horizontalFlowPanel);
		initWidget(locationPanel);
	}

	private void initializeLocationOptions() {
		Label filterBy = new Label("Filter By : ");
		
		filterBy.setStyleName("filterBy");
		locationOptionsPanel = new HorizontalPanel();

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

		locationOptionsPanel.add(filterBy);
		locationOptionsPanel.add(options);
		locationOptionsPanel.add(listBox);

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
						updateListBox(countries);
						showAll(true);
					} else if(options.getValue(selectedIndex).equalsIgnoreCase("Location")) {
						updateListBox(locations);
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
		return this;
	}

	public void show(String location) {
		if(this.horizontalFlowPanel != null) {
			int count = this.horizontalFlowPanel.getWidgetCount();

			for (int i = 0; i < count; i++) {
				Widget widget = horizontalFlowPanel.getWidget(i);

				if(widget instanceof FormattedFriendView) {
					FormattedFriendView friendView = (FormattedFriendView)widget;
					if(friendView.getDetails() != null) {
						if(friendView.getDetails().getCurrent_location() != null &&  friendView.getDetails().getCurrent_location().toString().contains(location)){
							widget.setVisible(true);
						} else {
							widget.setVisible(false);
						}
					}
				}
			}
		}
	}

	public void showAll(boolean isAll) {
		if(this.horizontalFlowPanel != null) {
			int count = this.horizontalFlowPanel.getWidgetCount();
			for (int i = 0; i < count; i++) {
				Widget widget = horizontalFlowPanel.getWidget(i);

				if(widget instanceof FormattedFriendView) {
					FormattedFriendView friendView = (FormattedFriendView)widget;
					if(isAll) {
						widget.setVisible(true);
					} else if (friendView.getDetails() != null && friendView.getDetails().getCurrent_location() == null) {
						widget.setVisible(true);
					} else {
						widget.setVisible(false);
					}
				}
			}
		}
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
