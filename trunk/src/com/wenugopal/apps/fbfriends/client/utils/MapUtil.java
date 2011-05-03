package com.wenugopal.apps.fbfriends.client.utils;

/**
 * @author wenugopal@gmail.com
 * 
 * http://www.facebook.com/wenugopal
 */
public class MapUtil
{

    public MapUtil()
    {
    }

    public static double getTop(long height, double lat)
    {
        double top = (lat * -1D + 90D) * ((double)height / 180D);
        return top;
    }

    public static double getLeft(long width, double lng)
    {
        double left = (lng + 180D) * ((double)width / 360D);
        return left;
    }
}
