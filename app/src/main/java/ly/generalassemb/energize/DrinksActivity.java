package ly.generalassemb.energize;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import ly.generalassemb.energize.DrinkAdapter.DrinkItemListener;
import ly.generalassemb.energize.data.DatabaseHelper;
import ly.generalassemb.energize.data.Drink;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class DrinksActivity extends AppCompatActivity {

    private RecyclerView drinkRecyclerView;
    private List<Drink> drinkList;

    private DrinkItemListener drinkItemListener;
    private DrinkAdapter drinkAdapter;

    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = DatabaseHelper.getInstance(getApplicationContext());
        dbHelper.cleanDatabase();
        dbHelper.populateDatabase();

        drinkList = dbHelper.getAllDrinks();

        drinkRecyclerView = (RecyclerView) findViewById(R.id.drink_recycler_view);

        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            drinkRecyclerView.setLayoutManager(new GridLayoutManager(DrinksActivity.this, 2));
        } else{
            drinkRecyclerView.setLayoutManager(new LinearLayoutManager(DrinksActivity.this));
        }

        drinkItemListener = new DrinkItemListener() {
            @Override
            public void onDrinkClick(int drinkId) {
                Intent drinkIntent = new Intent(DrinksActivity.this, DrinkDetailActivity.class);
                drinkIntent.putExtra(DrinkDetailActivity.EXTRA_DRINK_ID, drinkId);
                startActivity(drinkIntent);
            }
        };

        drinkAdapter = new DrinkAdapter(drinkList, drinkItemListener);
        drinkRecyclerView.setAdapter(drinkAdapter);

        handleIntent(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drinks_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search),
                new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                drinkList = dbHelper.getAllDrinks();
                drinkAdapter.setDrinksList(drinkList);
                drinkRecyclerView.scrollToPosition(0);
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                drinkList = dbHelper.getAllDrinks();
                drinkAdapter.setDrinksList(drinkList);
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            drinkList = dbHelper.searchDrinks(query);
            drinkAdapter.setDrinksList(drinkList);
            drinkRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shopping_cart:
                Intent drinkIntent = new Intent(DrinksActivity.this, ShoppingCartActivity.class);
                startActivity(drinkIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
