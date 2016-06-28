package ly.generalassemb.energize;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ly.generalassemb.energize.DrinkAdapter.DrinkItemListener;
import ly.generalassemb.energize.data.Drink;

/**
 * Created by darrankelinske on 6/28/16.
 */
public class DrinkHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

    private Drink drink;

    private TextView nameTextView;

    private DrinkItemListener listener;

    public DrinkHolder(View itemView, DrinkItemListener listListener) {
        super(itemView);
        listener = listListener;
        itemView.setOnClickListener(this);

        nameTextView = (TextView) itemView.findViewById(R.id.drink_detail_name_text_view);

    }

    public void bindDrink(Drink drink) {
        this.drink = drink;
        nameTextView.setText(drink.getName());

    }

    @Override
    public void onClick(View v) {
        listener.onDrinkClick(drink.getId());
    }

}