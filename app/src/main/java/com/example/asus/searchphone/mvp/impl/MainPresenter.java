package com.example.asus.searchphone.mvp.impl;

import com.example.asus.searchphone.Model.Phone;
import com.example.asus.searchphone.business.HttpUntil;
import com.example.asus.searchphone.mvp.MvpMainView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 2017/8/24.
 */

public class MainPresenter extends BasePresenter{
    String mUrl="https://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
    MvpMainView mvpMainView;
    Phone mPhone;

    public MainPresenter(MvpMainView mainView){
        mvpMainView=mainView;
    }

    public Phone getPhoneInfo(){
        return mPhone;
    }

    public void sarchPhoneInfo(String phone){
        if(phone.length()!=11){
            mvpMainView.showToast("请输入正确的手机号");
            return;
        }
        mvpMainView.showLoading();
        //写上http请求的处理逻辑
        sendHttp(phone);
    }
    private void sendHttp(String phone){
        Map<String, String> map = new HashMap<String, String>();
        map.put("tel", phone);
        HttpUntil httpUntil=new HttpUntil(new HttpUntil.HttpResponse() {
            @Override
            public void onSuccess(Object object) {
                String json = object.toString();
                int index=json.indexOf("{");
                json = json.substring(index, json.length());

                //使用JSONObject
//                mPhone = parseModelwithOrgJson(json);
                //使用Gson
//                mPhone = parseModelWithGson(json);
                //使用FastJson
                mPhone = parseModelWithFastJson(json);

                mvpMainView.hidenLoading();
                mvpMainView.updateView();

            }

            @Override
            public void onFail(String error) {
                mvpMainView.showToast(error);
                mvpMainView.hidenLoading();
            }
        });
        httpUntil.sendGetHttp(mUrl,map);
    }

    //OrgJson解析
//    private Phone parseModelwithOrgJson(String json){
//        Phone phone = new Phone();
//        try {
//            org.json.JSONObject jsonObject = new org.json.JSONObject(json);
//            String value = jsonObject.getString("telString");
//            phone.setTelString(value);
//
//            value = jsonObject.getString("province");
//            phone.setProvince(value);
//
//            value = jsonObject.getString("catName");
//            phone.setCatName(value);
//
//            value = jsonObject.getString("carrier");
//            phone.setCarrier(value);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return phone;
//    }

    //Gson解析
//    private Phone parseModelWithGson(String json){
//        Gson gson=new Gson();
//        Phone phone = gson.fromJson(json, Phone.class);
//        return phone;
//    }

    //fastJson解析
    private Phone parseModelWithFastJson(String json){
        Phone phone = com.alibaba.fastjson.JSONObject.parseObject(json, Phone.class);
        return phone;
    }
}
