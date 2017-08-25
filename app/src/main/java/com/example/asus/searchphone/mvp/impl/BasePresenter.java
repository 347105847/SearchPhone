package com.example.asus.searchphone.mvp.impl;

import android.content.Context;

/**
 * Created by ASUS on 2017/8/24.
 */

public class BasePresenter {
    Context mContext;
    public void attach(Context context){
        mContext=context;
    };
    public void onPause(){};
    public void onResume(){};
    public void onDestrory(){
        mContext=null;
    };

}
