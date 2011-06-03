package com.wenugopal.apps.fbfriends.client.dto;

import com.wenugopal.apps.fbfriends.client.fb.view.FacePile;

public class FriendsByCountry implements Comparable<FriendsByCountry> {
	
	private String counrty = null;
	
	private FacePile mFacePile = null;
	
	private FacePile fFacePile = null;
	
	private Integer men = 0;
	
	private Integer women = 0;

	/**
	 * @return the counrty
	 */
	public String getCounrty() {
		return counrty;
	}

	/**
	 * @param counrty the counrty to set
	 */
	public void setCounrty(String counrty) {
		this.counrty = counrty;
	}

	/**
	 * @return the men
	 */
	public Integer getMen() {
		return men;
	}

	/**
	 * @param men the men to set
	 */
	public void setMen(Integer men) {
		this.men = men;
	}

	/**
	 * @return the women
	 */
	public Integer getWomen() {
		return women;
	}

	/**
	 * @param women the women to set
	 */
	public void setWomen(Integer women) {
		this.women = women;
	}

	public Integer getTotal() {
		return this.women + this.men;
	}
	
	@Override
	public int compareTo(FriendsByCountry o) {
		return o.getTotal().compareTo(this.getTotal());
	}

	
	public void addFFreind(FQLFriendsDetails friend){
		if(this.fFacePile == null) {
			this.fFacePile = new FacePile();
		}
		this.fFacePile.addFacePileItem(friend);
	}

	public void addMFreind(FQLFriendsDetails friend){
		if(this.mFacePile == null) {
			this.mFacePile = new FacePile();
		}
		this.mFacePile.addFacePileItem(friend);
	}

	/**
	 * @return the mFacePile
	 */
	public FacePile getmFacePile() {
		return mFacePile;
	}

	/**
	 * @param mFacePile the mFacePile to set
	 */
	public void setmFacePile(FacePile mFacePile) {
		this.mFacePile = mFacePile;
	}

	/**
	 * @return the fFacePile
	 */
	public FacePile getfFacePile() {
		return fFacePile;
	}

	/**
	 * @param fFacePile the fFacePile to set
	 */
	public void setfFacePile(FacePile fFacePile) {
		this.fFacePile = fFacePile;
	}


}

