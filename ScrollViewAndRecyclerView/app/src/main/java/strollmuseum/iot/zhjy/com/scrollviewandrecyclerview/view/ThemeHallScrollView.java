package strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.utils.UiUtils;


/**
 * Created by zhangyang on 2016/12/21 17 24 V1.0.
 * 主要的思路:
 *     (1)在onInterceptTouchEvent函数中:
 *         down：
 *            初始化interceptFlag=false，记录startX,startY值
 *
 *        move：
 *           获取瞬间dy值
 *           条件:
 *                 1.1:是否准备向上滑动:
 *                        (1):如果scrollView滑动的scrollY>=固定值 && dy<0 (向上运动)
 *                              如果(2) true：交给recylerView进行处理
 *                                      false:拦截焦点:交给scrollView处理
 *
 *                        (2)具体的判断是:gridView获取最后一个显示的position，
 *                            将position与list的个数比较？
 *                              如果满足条件:
 *                                  获取最后一个子节点lastView，将lastView的getBottom值>
 *                                  recyclerView的getBottom值:
 *                                     如果true:还没有显示完全，进行拦截交给scrollView进行拦截
 *
 *                1.2:是否准备向下滑动:
 *                      (1)如果scrollView滑动的scrollY>=固定值 && dy>0 (向下运动)
 *                             如果:recylerView的firstVisiableItem完全可见:
 *                                   交给scrollView处理
 *
 *                             否则：交给recylerView处理
 *
 *
 *               1.3:其他情况:
 *                   交给scrollView进行处理:
 *
 */
public class ThemeHallScrollView extends ScrollView{
    public ThemeHallScrollView(Context context) {
        this(context, null);
    }

    public ThemeHallScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ThemeHallScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 标识是否拦截
     */
    boolean interceptFlag =false;
    float startX,startY,dy;//移动的相关的值
    float scrollTopValue= UiUtils.dip2dp(160);//初始化滑动的topY值

    final float TOUCH_SLOP= ViewConfiguration.getTouchSlop();
    GridLayoutManager gridLayoutManager;//相关的gridLayoutManager


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interceptFlag =false;
                startX=event.getRawX();
                startY=event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                dy=event.getRawY()-startY;
                startY=event.getRawY();
                System.out.println("ThemeHallScrollView onInterceptTouchEvent dy:"+dy);
                if(Math.abs(dy)>TOUCH_SLOP){
                    //有效的滑动:
                    /**
                     * 是否准备向上滑动
                     */
                     if(hasReadyScrollUp(event)){
                         System.out.println("ThemeHallScrollView 111111111111.......");
                         //满足条件:不进行拦截
                         interceptFlag =false;
                     }else if(hasReadyScrollDown(event) && gridLayoutManager!=null){
                        //继而判断recycler的firstView是否完全可见
                         int position=gridLayoutManager.findFirstVisibleItemPosition();
                         System.out.println("ThemeHallScrollView 222222222222.......position:"+position+"...gridLayoutManager.getChildAt(0).getTop():"+//
                                                                                                                            gridLayoutManager.getChildAt(0).getTop());
                         if(position<=1 && gridLayoutManager.getChildAt(0).getTop()>=0){
                             //进行拦截
                             interceptFlag =true;
                         }else{
                             //不进行拦截
                             interceptFlag =false;
                         }
                     }else{
                         interceptFlag =true;
                     }
                }else{
                    interceptFlag =false;
                }

                break;


            case MotionEvent.ACTION_UP:
                interceptFlag =false;
                break;
        }

        if(interceptFlag){
            System.out.println("ThemeHallScrollView 333333333333333......interceptFlag:"+interceptFlag);
            return true;
        }
        System.out.println("ThemeHallScrollView 333333333333333......interceptFlag:"+interceptFlag);
        return interceptFlag;
    }

    /**
     * 是否准备向下移动
     * @param event
     * @return
     */
    private boolean hasReadyScrollDown(MotionEvent event) {
        if(getScrollY()>=scrollTopValue && dy>0){
            System.out.println("ThemeHallScrollView hasReadyScrollDown getScrollY():"+getScrollY()+"....scrollTopValue:"+scrollTopValue+"...dy:"+dy+"..true");
            return true;
        }
        System.out.println("ThemeHallScrollView hasReadyScrollDown getScrollY():"+getScrollY()+"....scrollTopValue:"+scrollTopValue+"...dy:"+dy+"...false");
        return false;
    }

    /**
     * 进行判断在onTouchEvent事件中能否向上滑动
     * @return
     */
    private boolean touchEventScrollUp() {
        if (getScrollY()<scrollTopValue && dy<0){
            return true;
        }
        return false;
    }
    /**
     * 是否准备向上滑动
     * @param event
     * @return
     */
    private boolean hasReadyScrollUp(MotionEvent event) {
        if(getScrollY()>=scrollTopValue && dy<0){
            if(getScrollY()>scrollTopValue){
                smoothScrollBy(0,-Math.round(getScrollY() - scrollTopValue));
            }
            System.out.println("ThemeHallScrollView hasReadyScrollUp getScrollY():"+getScrollY()+"....scrollTopValue:"+scrollTopValue+"...dy:"+dy+"..true");
            return true;
        }
        System.out.println("ThemeHallScrollView hasReadyScrollUp getScrollY():"+getScrollY()+"....scrollTopValue:"+scrollTopValue+"...dy:"+dy+"..false");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //获取滑动的偏移量
                if((ev.getRawY()-startY)!=0){
                    dy=ev.getRawY()-startY;
                }
                System.out.println("ThemeHallScrollView onTouchEvent..ACTION_MOVE dy:"+dy);

                //如果向上滑动
                if(dy<0 && touchEventScrollUp()){
                    scrollBy(0, -Math.round(dy));
                }else if(dy>0){
                    //如果向下滑动
                    scrollBy(0, -Math.round(dy));
                }
                startY=ev.getRawY();
                break;
        }
        return true;
    }

    /**
     * 相关的get和set方法
     * @return
     */
    public float getScrollTopValue() {
        return scrollTopValue;
    }

    public void setScrollTopValue(float scrollTopValue) {
        this.scrollTopValue = scrollTopValue;
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

    public void setGridLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }
}
