package org.yajac.rvaweek.facebook;

/**
 * Created by ian.mcewan on 9/24/17.
 */
public class FacebookAccessManager {

    public static String getAccessToken(){
        return System.getenv("accessToken");
    }
}
