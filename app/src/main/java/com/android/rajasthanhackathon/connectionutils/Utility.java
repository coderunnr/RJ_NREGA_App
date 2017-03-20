package com.android.rajasthanhackathon.connectionutils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 20/3/17.
 */
public class Utility {

    public static String URL1="http://192.168.43.219:8000/";

    public static void setDistrictId(int id, Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("nrega",0);
        sharedPreferences.edit().putInt("district",id).apply();
    }
    public static void setDistrictMainId(int id, Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("nrega",0);
        sharedPreferences.edit().putInt("districtmain",id).apply();
    }

    public static void setGramId(int id, Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("nrega",0);
        sharedPreferences.edit().putInt("gram",id).apply();
    }

    public static int getDistrictId(Context context)
    {
        return context.getSharedPreferences("nrega",0).getInt("district",0);
    }
    public static int getDistrictMainId(Context context)
    {
        return context.getSharedPreferences("nrega",0).getInt("districtmain",0);
    }
    public static int getGramId(Context context)
    {
        return context.getSharedPreferences("nrega",0).getInt("gram",0);
    }

}
