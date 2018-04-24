package com.example.jana.motivlearn;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by noufalsedairy on 04/04/2018 AD.
 */

public class ImageAdapter extends PagerAdapter {
    private Context mcontext;
    private int[] mImageIds = new int[] {R.drawable.padgeslide1,R.drawable.padgesslide2,R.drawable.padgesslide3,R.drawable.padgesslide4};

    ImageAdapter(Context context){
        mcontext=context;
    }
    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mcontext);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(mImageIds[position]);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((ImageView) object);
    }
}
