package com.example.jana.motivlearn;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class levelsAdapter extends PagerAdapter {

    private Context lcontext;
    private int[] levelsadapter = new int[] {R.drawable.levels1,R.drawable.levels2,R.drawable.levels3};

    levelsAdapter(Context context){
        lcontext=context;
    }
    @Override
    public int getCount() {
        return levelsadapter.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(lcontext);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(levelsadapter[position]);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
