package strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.utils;

import android.content.Context;
import android.content.res.Resources;

import strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.MyApplication;

/**
 * Created by zhangyang on 2016/12/23 16 16 V1.0.
 */
public class UiUtils {

    /**
     * 进行dip转成像素dp
     * @param dip
     * @return
     */
    public static int dip2dp(int dip) {
        return (int)(getResource().getDisplayMetrics().density*dip+0.5);
    }

    /**
     * 进行获取resource
     * @return
     */
    private static Resources getResource(){
       return  MyApplication.instance.getResources();
    }

    /**
     * 进行获取context
     * @return
     */
    public static Context getContext() {
        return MyApplication.instance.getApplicationContext();
    }

    public static int getDefaultHeight(){
        return getResource().getDisplayMetrics().heightPixels;
    }

    public static int getDefaultWidth(){
        return getResource().getDisplayMetrics().widthPixels;
    }
}

