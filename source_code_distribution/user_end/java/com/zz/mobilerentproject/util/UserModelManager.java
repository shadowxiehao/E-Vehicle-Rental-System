package com.zz.mobilerentproject.util;

import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;

public class UserModelManager {
    private static final String TAG = "UserModelManager";
    private static final String PER_DATA       = "per_profile_manager";
    private static final String PER_USER_MODEL = "per_user_model";


    private static UserModelManager sInstance;
    private        UserModel        mUserModel;

    public static UserModelManager getInstance(){
        if(sInstance == null){
            synchronized (UserModelManager.class){
                if(sInstance == null){
                    sInstance = new UserModelManager();
                }
            }
        }
        return sInstance;
    }

    public synchronized UserModel getUserModel(){
        if(mUserModel == null){
            loadUserModel();
        }
        return mUserModel == null? new UserModel(): mUserModel;
    }

    private void loadUserModel() {
        try {
            String json = SPUtils.getInstance(PER_DATA).getString(PER_USER_MODEL);
            mUserModel = GsonUtils.fromJson(json, UserModel.class);
        } catch (Exception e) {
            Log.d(TAG, "loadUserModel failed:" + e.getMessage());
        }
    }

    public synchronized void setUserModel(UserModel model) {
        mUserModel = model;
        try {
            SPUtils.getInstance(PER_DATA).put(PER_USER_MODEL, GsonUtils.toJson(mUserModel));
        } catch (Exception e) {
            Log.d(TAG, "");
        }
    }

    public synchronized void clearUserModel() {
        try {
            SPUtils.getInstance(PER_DATA).put(PER_USER_MODEL, "");
        } catch (Exception e) {
            Log.d(TAG, "clea user model error:" + e.getMessage());
        }
    }

}
