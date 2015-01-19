package com.korqie.features.login;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.korqie.R;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    int[] slides;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, int[] slides) {
        this.context = context;
        this.slides = slides;
    }

    @Override
    public int getCount() {
        return slides.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageViewSlide;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        imageViewSlide = (ImageView) itemView.findViewById(R.id.imageViewSlide);
        imageViewSlide.setImageResource(slides[position]);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}