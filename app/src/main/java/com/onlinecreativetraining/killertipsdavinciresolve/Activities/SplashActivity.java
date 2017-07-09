package com.onlinecreativetraining.killertipsdavinciresolve.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.onlinecreativetraining.killertipsdavinciresolve.R;
import com.onlinecreativetraining.killertipsdavinciresolve.util.SessionController;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView image = (ImageView)findViewById(R.id.imgSplash);
        image.setImageResource(R.drawable.default_image);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startApp();
            }
        }, 2000);
    }

    void startApp()
    {
        if (SessionController.getInstance().isTutorialStatus()) {
            startActivity(new Intent(this, MainActivity.class));
        }else {
            startActivity(new Intent(this, TutorialActivity.class));
        }
        finish();
    }

}
