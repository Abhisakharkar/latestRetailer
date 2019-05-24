package com.example.retailer.SingletonClasses;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.content.ContentValues.TAG;

@Singleton
public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SimpleDateFormat sdf;

    @Inject
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        editor=this.sharedPreferences.edit();
        sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
    }

    public JSONObject getProfile(){
        String strJson = sharedPreferences.getString("jsondata",null);
        if (strJson != null) {
            try {
                JSONObject response = new JSONObject(strJson);
                return response;

            } catch (JSONException e) {
            return null;
            }
        }else return null;
    }

    public void saveProfile(JSONObject jsonObject){
        editor.putString("profile", jsonObject.toString());
        editor.commit();
    }

    public String getToken(){
        try {
            Date currentTime=new Date();
            Log.d(TAG, "getToken: currentTime "+currentTime);
            Date fetchTime = sdf.parse(sharedPreferences.getString("tokenFetchTime",null));
            Log.d(TAG, "getToken: fetchTime "+fetchTime);
            Date expiryTime=sdf.parse(sharedPreferences.getString("tokenExpiryTime",null));
            Log.d(TAG, "getToken: expiryTime"+expiryTime);
            if (currentTime.after(fetchTime) && currentTime.before(expiryTime)){
                String token =sharedPreferences.getString("Token",null);
                Log.d(TAG, "getToken: fetched token "+token);
                return token;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void setToken(String token){

        String fetchTime = sdf.format(new Date());
        Log.d(TAG, "setToken: fetch time"+fetchTime);
        Date date = null;
        try {
            date = sdf.parse(fetchTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        editor.putString("tokenFetchTime",fetchTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 58);
        String expiryTime=sdf.format(calendar.getTime());
        Log.d(TAG, "setToken: expiry time"+expiryTime);

        editor.putString("tokenExpiryTime",expiryTime);
        editor.putString("Token",token);
        Log.d(TAG, "setToken: token"+token);
        editor.commit();
    }

}
