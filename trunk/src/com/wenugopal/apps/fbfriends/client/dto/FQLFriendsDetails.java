
package com.wenugopal.apps.fbfriends.client.dto;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class FQLFriendsDetails
{
    private String uid;
    private String name;
    private String pic_small;
    private String pic_square;
    private String pic;
    private String sex;
    private String profile_url;
    private String username;
    private Location hometown_location;
    private Location current_location;

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPic_small()
    {
        return pic_small;
    }

    public void setPic_small(String pic_small)
    {
        this.pic_small = pic_small;
    }

    public String getPic()
    {
        return pic;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getProfile_url()
    {
        return profile_url;
    }

    public void setProfile_url(String profile_url)
    {
        this.profile_url = profile_url;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Location getHometown_location()
    {
        return hometown_location;
    }

    public void setHometown_location(Location hometown_location)
    {
        this.hometown_location = hometown_location;
    }

    public Location getCurrent_location()
    {
        return current_location;
    }

    public void setCurrent_location(Location current_location)
    {
        this.current_location = current_location;
    }

	/**
	 * @return the pic_square
	 */
	public String getPic_square() {
		return pic_square;
	}

	/**
	 * @param pic_square the pic_square to set
	 */
	public void setPic_square(String pic_square) {
		this.pic_square = pic_square;
	}

}
