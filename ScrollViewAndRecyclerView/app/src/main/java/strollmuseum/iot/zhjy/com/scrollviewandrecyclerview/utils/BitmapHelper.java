package strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.utils;


import android.os.Environment;

import com.lidroid.xutils.BitmapUtils;

import java.io.File;

import strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.R;

/**
 * bitmap的工具类
 * @author zhangyang
 *
 */
public class BitmapHelper {
	private BitmapHelper(){
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 * @param
	 *        application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils(){
		if(bitmapUtils == null){
			// 第二个参数 缓存图片的路径 // 加载图片 最多消耗多少比例的内存 0.05-0.8f
			bitmapUtils = new BitmapUtils(UiUtils.getContext(), Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"androidTest",0.5f);
			bitmapUtils.configDefaultLoadFailedImage(UiUtils.getContext().getResources().getDrawable(R.drawable.pic_default_horizontal));
			bitmapUtils.configDefaultLoadingImage(UiUtils.getContext().getResources().getDrawable(R.drawable.pic_default_horizontal));
		}
		return bitmapUtils;
	}
}
