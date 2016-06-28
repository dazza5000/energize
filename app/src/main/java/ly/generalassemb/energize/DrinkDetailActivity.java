package ly.generalassemb.energize;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ly.generalassemb.energize.data.DatabaseHelper;
import ly.generalassemb.energize.data.Drink;

public class DrinkDetailActivity extends AppCompatActivity {

    public static final String EXTRA_DRINK_ID = "DRINK_ID";

    private ImageView drinkImageView;
    private TextView drinkNameTextView;
    private TextView drinkDescriptionTextView;

    private int drinkId;
    private DatabaseHelper databaseHelper;
    private Drink drink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail);

        drinkImageView = (ImageView) findViewById(R.id.drink_detail_image_view);
        drinkNameTextView = (TextView) findViewById(R.id.drink_detail_name_text_view);
        drinkDescriptionTextView = (TextView) findViewById(R.id.drink_detail_description_text_view);

        // Fetch the drinkId from the Extra data
        if (getIntent().hasExtra(EXTRA_DRINK_ID)) {
            drinkId = getIntent().getExtras().getInt(EXTRA_DRINK_ID);
        }

        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        drink = databaseHelper.getDrink(drinkId);

        drinkNameTextView.setText(drink.getName());
        drinkDescriptionTextView.setText(drink.getDescription());
        int id = this.getResources().getIdentifier(drink.getImageLocation(), "drawable", this.getPackageName());
        Picasso.with(DrinkDetailActivity.this).load(id).into(drinkImageView);

    }
}
