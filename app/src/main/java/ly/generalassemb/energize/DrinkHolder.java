package ly.generalassemb.energize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ly.generalassemb.energize.DrinkAdapter.DrinkItemListener;
import ly.generalassemb.energize.data.Drink;

/**
 * Created by darrankelinske on 6/28/16.
 */
public class DrinkHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

    private Drink drink;
    private DrinkItemListener listener;
    private View itemView;

    private CardView drinkCardView;
    private RoundedImageView drinkImageView;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private TextView sizeTextView;

    public DrinkHolder(View itemView, DrinkItemListener listListener) {
        super(itemView);
        this.itemView = itemView;
        listener = listListener;
        itemView.setOnClickListener(this);

        drinkCardView = (CardView) itemView.findViewById(R.id.drink_card_view);
        nameTextView = (TextView) itemView.findViewById(R.id.drink_name_text_view);
        descriptionTextView = (TextView)
                itemView.findViewById(R.id.drink_description_text_view);
        priceTextView = (TextView) itemView.findViewById(R.id.drink_price_text_view);
        sizeTextView = (TextView) itemView.findViewById(R.id.drink_description_size_view);
        drinkImageView = (RoundedImageView) itemView.findViewById(R.id.drink_detail_image_view);

    }

    public void bindDrink(Drink drink) {
        this.drink = drink;
        nameTextView.setText(drink.getName());
        descriptionTextView.setText(drink.getDescription());
        priceTextView.setText(String.valueOf(drink.getPrice()));
        sizeTextView.setText(String.valueOf(drink.getSize()));

        int id = drinkImageView.getContext().getResources()
                .getIdentifier(drink.getImageLocation(), "drawable",
                        drinkImageView.getContext().getPackageName());

        Picasso.with(drinkImageView.getContext())
                .load(id)
                .fit()
                .centerCrop()
                .into(drinkImageView);

        Bitmap icon = BitmapFactory.decodeResource(drinkImageView.getContext().getResources(),
                id);
        Palette p = Palette.from(icon).generate();
        drinkCardView.setBackgroundColor(p.getLightVibrantColor(
                drinkImageView.getContext().getResources().getColor(R.color.colorAccent)));
    }

    @Override
    public void onClick(View v) {
        if (null != listener) {
            listener.onDrinkClick(drink.getId());
        }
    }

}