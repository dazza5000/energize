package ly.generalassemb.energize;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ly.generalassemb.energize.data.Drink;

/**
 * Created by darrankelinske on 6/28/16.
 */
public class DrinkAdapter extends RecyclerView.Adapter<DrinkHolder> {

    private List<Drink> drinks;

    private DrinkItemListener listener;

    public DrinkAdapter(List<Drink> drinks, DrinkItemListener listener) {
        this.drinks = drinks;
        this.listener = listener;
    }

    @Override
    public DrinkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.drink_item, parent, false);
        return new DrinkHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(DrinkHolder holder, int position) {
        Drink drink = drinks.get(position);
        holder.bindDrink(drink);
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public void setDrinksList(List<Drink> drinks) {
        this.drinks = drinks;
        notifyDataSetChanged();
    }

    public interface DrinkItemListener {
        void onDrinkClick(int drinkId);
    }
}
