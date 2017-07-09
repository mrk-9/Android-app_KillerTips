package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.hkm.slider.Indicators.PagerIndicator;
import com.hkm.slider.SliderLayout;
import com.hkm.slider.SliderTypes.BaseSliderView;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.util.SessionController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dev on 04/09/16.
 */
public class TutorialActivity extends Activity {

    SliderLayout slider;
    PagerIndicator pagerIndicator;
    LinearLayout lytSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        slider = (SliderLayout) findViewById(R.id.slider);
        pagerIndicator = (PagerIndicator) findViewById(R.id.page_indicator);
        lytSkip = (LinearLayout) findViewById(R.id.lyt_skip);
        lytSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionController.getInstance().setTutorialStatus(true);
                startActivity(new Intent(TutorialActivity.this, MainActivity.class));
                finish();
            }
        });

        initWidget();
    }

    public void initWidget(){

        ArrayList<SliderView> images = new ArrayList<>();
        images.add(new SliderView(this, R.drawable.mytutorial_1));
        images.add(new SliderView(this, R.drawable.mytutorial_2));
        images.add(new SliderView(this, R.drawable.mytutorial_3));
        images.add(new SliderView(this, R.drawable.mytutorial_4));
        slider.loadSliderList(images);
        slider.setCustomIndicator(pagerIndicator);
    }

    public class SliderView extends BaseSliderView {

        private Context context;
        private Integer image;

        public SliderView(Context context) {
            super(context);
            this.context = context;
        }

        public SliderView(Context context, Integer image) {
            super(context);
            this.context = context;
            this.image = image;
        }

        @Override
        public View getView() {
            View view = LayoutInflater.from(context).inflate(R.layout.slider_view, null, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Picasso.with(context).load(image).into(imageView);
            return view;
        }

    }

}
