package tobiapplications.com.moviebase.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.utils.Constants;

public class SearchActivity extends AppCompatActivity {

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            title = query;
            setTitle(title);
            setUpSearchResultsFragment(query);
        }
    }

    private void setUpSearchResultsFragment(String query) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SEARCH_QUERY, query);
        SearchFragment fragment = SearchFragment.newInstance(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.search_content, fragment)
                .commit();
    }
}
