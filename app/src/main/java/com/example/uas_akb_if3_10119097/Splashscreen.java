package com.example.uas_akb_if3_10119097;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 10119097
 * Ikhsan Nurul Rizki
 * IF-3 */
public class Splashscreen extends AppCompatActivity {
    LuncherManager luncherManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        luncherManager = new LuncherManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(luncherManager.isFirstTime()){
                    luncherManager.setFirstLunch(false);
                    startActivity(new Intent(getApplicationContext(),Slider.class));
                }else {
                    startActivity(new Intent(getApplicationContext(),Login.class));

                }

            }
        },3000);
    }
}
