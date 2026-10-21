package com.unit401.pranav.muricize;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MS = 3000L;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable openNameFinder =
            new Runnable() {
                @Override
                public void run() {
                    if (isFinishing()) {
                        return;
                    }
                    startActivity(new Intent(SplashScreenActivity.this, NameFinder.class));
                    finish();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler.postDelayed(openNameFinder, SPLASH_DELAY_MS);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(openNameFinder);
        super.onDestroy();
    }
}
