package ly.generalassemb.energize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import ly.generalassemb.energize.data.Drink;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView shoppingCartRecyclerView;
    private List<Drink> shoppingCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingCartRecyclerView = (RecyclerView) findViewById(R.id.shopping_cart_recycler_view);

        shoppingCartItems = ShoppingCart.getInstance().getDrinks();

        DrinkAdapter shoppingCartItemsAdapter = new DrinkAdapter(shoppingCartItems, null);

        shoppingCartRecyclerView.setAdapter(shoppingCartItemsAdapter);

        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
