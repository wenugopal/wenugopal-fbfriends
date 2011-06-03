package com.wenugopal.apps.fbfriends.client.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.BarChart;
import com.google.gwt.visualization.client.visualizations.BarChart.Options;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetails;
import com.wenugopal.apps.fbfriends.client.dto.FQLFriendsDetailsMap;
import com.wenugopal.apps.fbfriends.client.dto.FriendsByCountry;

@SuppressWarnings("deprecation")
public class StatsView extends Composite {
	
	private HorizontalPanel statsPanel = null;
	private FQLFriendsDetailsMap fqlFriendsDetailsMap;
	private  Map<String, String> locationIdMap = new HashMap<String, String>();
	private ArrayList<FriendsByCountry> list = new ArrayList<FriendsByCountry>();
	private StatsFacePile statsFacePile = null;
	
	
	public StatsView() {
		statsPanel = new HorizontalPanel();
		statsPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		statsPanel.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		statsFacePile = new StatsFacePile();
		initWidget(statsPanel);
	}

	public StatsView setData(FQLFriendsDetailsMap fqlFriendsDetailsMap, Map<String, String> locationIdMap) {
		this.fqlFriendsDetailsMap = fqlFriendsDetailsMap;
		this.locationIdMap = locationIdMap;
		
		final DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Country");
		data.addColumn(ColumnType.NUMBER, "Male");
		data.addColumn(ColumnType.NUMBER, "Female");

		data.addRows(locationIdMap.size());

		Set<Entry<String, String> > friendsDetailsList = locationIdMap.entrySet();
		Iterator<Entry<String, String> > it = friendsDetailsList.iterator();
		for (int i = 0; it.hasNext(); i++) {
			Entry<String, String>  entry = it.next();
			FriendsByCountry fbc = getFriendsByCountry(entry.getKey());
			list.add(fbc);
		}
		
		Collections.sort(list);

		for(int i = 0; i<list.size(); i++){
			data.setValue(i, 0, list.get(i).getCounrty());
			data.setValue(i, 1, list.get(i).getMen());
			data.setValue(i, 2, list.get(i).getWomen());	
		}

		Options options = BarChart.Options.create();
		options.setWidth(700);
		options.setHeight(locationIdMap.size()*40);
		options.setTitle("You have a total of "+fqlFriendsDetailsMap.size()+" Friends from "+locationIdMap.size()+" countries!");
		options.setLegend(LegendPosition.BOTTOM);
		options.setTitleY("Countries");
		options.setTitleX("Number of Friends");

		//		    options.set3D(true);
		options.setAxisFontSize(10);
		options.setLegendFontSize(12);
		options.setTitleFontSize(13);
		options.setEnableTooltip(false);

		final BarChart bc = new BarChart(data, options);

		bc.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				JsArray<Selection> selections = bc.getSelections();
				Selection selection = selections.get(0);
				int row = selection.getRow();
				int column = selection.getColumn();
				FriendsByCountry fbc = list.get(row);
				if(column == 1) {
					statsPanel.remove(1);
					statsPanel.add(statsFacePile.setData(fbc.getCounrty(), fbc.getmFacePile(), fbc.getTotal(), true));
				} else if(column == 2) {
					statsPanel.remove(1);
					statsPanel.add(statsFacePile.setData(fbc.getCounrty(), fbc.getfFacePile(), fbc.getTotal(), false));
				}
			}
		});
		
//		bc.addHandler(handler, type)
		statsPanel.add(bc);
		statsPanel.add(statsFacePile);
		
		return this;
	}


	protected FriendsByCountry getFriendsByCountry(String location) {
		FriendsByCountry fbc = new FriendsByCountry();
		String idsString = locationIdMap.get(location);
		String ids[] = idsString.split(",");

		int men = 0;
		int women = 0;

		for(String id : ids) {
			FQLFriendsDetails details = fqlFriendsDetailsMap.get(id);
			if(details.getSex().equalsIgnoreCase("male")) {
				men++;
				fbc.addMFreind(details);
			}else {
				women++;
				fbc.addFFreind(details);
			}
		}
		fbc.setMen(men);
		fbc.setWomen(women);
		fbc.setCounrty(location);
		return fbc;
	}





	
}
