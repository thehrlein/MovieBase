package tobiapplications.com.moviebase.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.ui.movie_overview.MovieOverviewActivity;

public class CoverPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_page);

        showFullscreen();

        waitAndShowMainActivity();
    }

    private void showFullscreen() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().hide();
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void waitAndShowMainActivity() {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(CoverPage.this, MovieOverviewActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
