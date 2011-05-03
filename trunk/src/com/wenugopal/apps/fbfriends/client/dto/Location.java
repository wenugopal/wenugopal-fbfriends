
package com.wenugopal.apps.fbfriends.client.dto;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class Location
{
    private String city;
    private String state;
    private String country;
    private String zip;
    private String id;
    private String name;

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public String toString()
    {
        String locationString = "";
        if(city != null)
            locationString = (new StringBuilder(String.valueOf(locationString))).append(city).append(", ").toString();
        if(state != null)
            locationString = (new StringBuilder(String.valueOf(locationString))).append(state).append(", ").toString();
        if(country != null)
            locationString = (new StringBuilder(String.valueOf(locationString))).append(country).toString();
        return locationString;
    }

}
