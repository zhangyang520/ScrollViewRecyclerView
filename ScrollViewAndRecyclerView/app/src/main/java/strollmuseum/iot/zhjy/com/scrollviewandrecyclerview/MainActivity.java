package strollmuseum.iot.zhjy.com.scrollviewandrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.bitmap.PauseOnRecyclerviewScrollListener;

import java.io.File;
import java.util.ArrayList;

import strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.utils.BitmapHelper;
import strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.utils.UiUtils;
import strollmuseum.iot.zhjy.com.scrollviewandrecyclerview.view.ThemeHallScrollView;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    ThemeHallScrollView scrollView;
    RecyclerView recyclerView;
    ArrayList<String> filePathList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //进行获取View
        scrollView=(ThemeHallScrollView)findViewById(R.id.scrollView);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        String dir= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"androidTest";
        File file=new File(dir);
        String fileName[]=file.list();
        if(fileName!=null && fileName.length>0){
            addData(fileName,dir);
            addRecyclerViewLayout();
        }
    }

    /**
     * 进行增加数据
     * @param fileName
     */
    private void addData(String[] fileName,String parentDir) {
        for (String filePath : fileName) {
            filePathList.add(parentDir+File.separator+filePath);
        }
    }

    /**
     * 进行创建recyclerViewlayout
     */
    private void addRecyclerViewLayout() {
        //进行计算recyler的高度
        int listCount=filePathList.size()/2;
        int listLeft=filePathList.size()%2;
        if(listLeft!=0){
            listCount+=1;
        }
        int height=listCount* UiUtils.dip2dp(110);
        System.out.println("listCount:" + listCount);
        if(listCount*UiUtils.dip2dp(110)>UiUtils.getDefaultHeight()-UiUtils.dip2dp(50)){
            height=UiUtils.getDefaultHeight()-UiUtils.dip2dp(50);
        }
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

//                System.out.println("recyler getMeasuredHeight:" + recyler.getMeasuredHeight() + "...line_out getMeasureedHeight:" + line_out.getMeasuredHeight());
            }
        });
        recyclerView.addOnScrollListener(new PauseOnRecyclerviewScrollListener(BitmapHelper.getBitmapUtils(), true, true));
        GridLayoutManager gridLayoutMananger = new GridLayoutManager(this, 2);
        gridLayoutMananger.setOrientation(GridLayoutManager.VERTICAL);
        scrollView.setGridLayoutManager(gridLayoutMananger);
        recyclerView.setLayoutManager(gridLayoutMananger);
        recyclerView.setAdapter(new GridViewAdapter());
    }

    class GridViewAdapter extends RecyclerView.Adapter<GridViewHolder>{
        @Override
        public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GridViewHolder(View.inflate(MainActivity.this, R.layout.apart_dynamic_load_hallexhibt2, null));
        }

        @Override
        public void onBindViewHolder(final GridViewHolder holder, final int position) {
            BitmapHelper.getBitmapUtils().display(holder.img_left, filePathList.get(position));
            holder.tv_hallexhibit_name1.setText("张扬");

            holder.relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    holder.relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    System.out.println("holder.relativeLayout getMeasuredHeight:"+holder.relativeLayout.getMeasuredHeight());
                }
            });
        }

        @Override
        public int getItemCount() {
            return filePathList.size();
        }
    }
    /**
     * ViewHolder
     */
    class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView img_left;
        TextView tv_hallexhibit_name1;
        RelativeLayout relativeLayout;
        public GridViewHolder(View itemView) {
            super(itemView);
            relativeLayout=(RelativeLayout)itemView;
            img_left=(ImageView)itemView.findViewById(R.id.img_left);
            tv_hallexhibit_name1=(TextView)itemView.findViewById(R.id.tv_hallexhibit_name1);
        }
    }

}
