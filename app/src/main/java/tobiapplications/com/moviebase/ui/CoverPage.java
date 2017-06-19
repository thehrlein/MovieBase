package tobiapplications.com.moviebase.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.network.DataManager;
import tobiapplications.com.moviebase.ui.overview.OverviewActivity;
import tobiapplications.com.moviebase.utils.GeneralUtils;
import tobiapplications.com.moviebase.utils.SettingsUtils;

public class CoverPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_page);

        showFullscreen();

        setLanguage();

        initializeApi();

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

    private void setLanguage() {
        SettingsUtils.updateApplicationLanguage(this);
    }

    private void initializeApi() {
        DataManager.getInstance().buildMovieApi();
    }

    private void waitAndShowMainActivity() {
        new Handler().postDelayed(() -> startOverviewActivity(), 1500);
    }

    private void startOverviewActivity() {
        Intent intent = new Intent(CoverPage.this, OverviewActivity.class);
        startActivity(intent);
        finish();
    }
}
