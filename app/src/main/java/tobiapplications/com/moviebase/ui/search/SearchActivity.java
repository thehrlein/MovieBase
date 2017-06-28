package tobiapplications.com.moviebase.ui.search;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import tobiapplications.com.moviebase.R;
import tobiapplications.com.moviebase.listener.OnFilterSearchListener;
import tobiapplications.com.moviebase.utils.Constants;

public class SearchActivity extends AppCompatActivity implements OnFilterSearchListener {

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filter:
                openFilterFragment();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openFilterFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchFilterFragment fragment = SearchFilterFragment.newInstance();

        fragment.show(fragmentManager, Constants.FILTER_DIALOG);
    }

    @Override
    public void onCancelFiltering() {
        Toast.makeText(this, "CANCEL FILTER", Toast.LENGTH_SHORT).show();
        setTitle(title);


    }

    @Override
    public void onFilterSubmit() {

    }
}
