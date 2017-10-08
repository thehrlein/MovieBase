package tobiapplications.com.moviebase.ui.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.databinding.ActivitySearchBinding;
import tobiapplications.com.moviebase.utils.Constants;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding bind;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_search);

        Intent intent = getIntent();
        if (intent != null) {
            String query = intent.getStringExtra(Constants.SEARCH_QUERY);
            title = query;
            setTitle(title);
            setUpSearchResultsFragment(query);
        }
    }

    private void setUpSearchResultsFragment(String query) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SEARCH_QUERY, query);
        SearchResultsFragment fragment = SearchResultsFragment.newInstance(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.search_content, fragment)
                .commit();
    }
}
