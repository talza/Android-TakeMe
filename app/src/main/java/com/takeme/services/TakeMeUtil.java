package com.takeme.services;

import android.content.Context;

import com.takeme.takemeapp.R;

import java.util.ArrayList;

/**
 * Created by tzamir on 11/7/2015.
 */
public class TakeMeUtil {
    ArrayList<String> lsSize;
    ArrayList<String> lsGender;
    ArrayList<String> lsType;

    public synchronized void init(Context context) {

        this.lsSize = new ArrayList<>();
        this.lsGender = new ArrayList<>();
        this.lsType = new ArrayList<>();

        for (String strSize : context.getResources().getStringArray(R.array.pet_size)){
            this.lsSize.add(strSize);
        }

        for (String strSize : context.getResources().getStringArray(R.array.pet_gender)){
            this.lsGender.add(strSize);
        }

        for (String strSize : context.getResources().getStringArray(R.array.pet_type)){
            this.lsType.add(strSize);
        }

    }

    private TakeMeUtil() {

    }

    public String getSizeByIndex(int index){
        return this.lsSize.get(index);
    }

    public String getTypeByIndex(int index){
        return this.lsType.get(index);
    }

    public String getGenderByIndex(int index){
        return this.lsGender.get(index);
    }

    /**
     * SingletonHolder is loaded on the first execution of TakeMeUtil.getInstance()
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        private final static TakeMeUtil INSTANCE = new TakeMeUtil();
    }

    public static TakeMeUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
