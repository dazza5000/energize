package ly.generalassemb.energize;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ly.generalassemb.energize.data.DatabaseHelper;
import ly.generalassemb.energize.data.Drink;
import ly.generalassemb.energize.data.Manufacturer;

public class DrinkDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DRINK_ID = "DRINK_ID";

    private ActionBar actionBar;
    private ImageView drinkImageView;
    private TextView drinkNameTextView;
    private TextView drinkDescriptionTextView;
    private TextView manufacturerDescriptionTextView;
    private TextView manufacturerLocationTextView;

    private int drinkId;
    private DatabaseHelper databaseHelper;
    private Drink drink;
    private Manufacturer manufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        drinkImageView = (ImageView) findViewById(R.id.drink_detail_image_view);
        drinkNameTextView = (TextView) findViewById(R.id.drink_detail_name_text_view);
        drinkDescriptionTextView = (TextView) findViewById(R.id.drink_detail_description_text_view);
        manufacturerDescriptionTextView =
                (TextView) findViewById(R.id.manufacturer_details_text_view);
        manufacturerLocationTextView =
                (TextView) findViewById(R.id.manufacturer_location_text_view);

        // Fetch the drinkId from the Extra data
        if (getIntent().hasExtra(EXTRA_DRINK_ID)) {
            drinkId = getIntent().getExtras().getInt(EXTRA_DRINK_ID);
        }

        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        drink = databaseHelper.getDrink(drinkId);
        manufacturer = databaseHelper.getManufacturerDetails(drinkId);

        actionBar = getSupportActionBar();
        actionBar.setTitle(drink.getName());

        drinkNameTextView.setText(drink.getName());
        drinkDescriptionTextView.setText(drink.getDescription());

        int id = this.getResources().getIdentifier(drink.getImageLocation(), "drawable",
                this.getPackageName());
        Picasso.with(DrinkDetailActivity.this).load(id).into(drinkImageView);

        Bitmap icon = BitmapFactory.decodeResource(drinkImageView.getContext().getResources(),
                id);
        Palette p = Palette.from(icon).generate();

        actionBar.setBackgroundDrawable(new ColorDrawable((p.getLightVibrantColor(
                drinkImageView.getContext().getResources().getColor(R.color.colorAccent)))));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(p.getDarkVibrantColor(
                    drinkImageView.getContext().getResources().getColor(R.color.colorAccent)));
        }

        manufacturerDescriptionTextView.setText("Description: "+manufacturer.getDescription());
        manufacturerLocationTextView.setText("Location: "+manufacturer.getLocation());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drink_detail_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_buy:
                ShoppingCart.getInstance().addDrink(drink);
                Toast.makeText(DrinkDetailActivity.this,
                        drink.getName() + " added to your cart!"
                        , Toast.LENGTH_SHORT).show();
                return true;
            case R.id.shopping_cart:
                Intent drinkIntent = new Intent(DrinkDetailActivity.this,
                        ShoppingCartActivity.class);
                startActivity(drinkIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
