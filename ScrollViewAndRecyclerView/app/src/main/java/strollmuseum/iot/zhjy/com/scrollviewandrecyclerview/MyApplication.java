package strollmuseum.iot.zhjy.com.scrollviewandrecyclerview;

import android.app.Application;

/**
 * Created by zhangyang on 2016/12/23 16 14 V1.0.
 */
public class MyApplication extends Application{
    public static MyApplication  instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
