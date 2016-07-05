package ly.generalassemb.energize;

import java.util.ArrayList;

import ly.generalassemb.energize.data.Drink;

/**
 * Created by darrankelinske on 7/1/16.
 */
public class ShoppingCart {
    //Define the Zoo object
    private static ShoppingCart cart;

    //Definition of the list to hold the animals
    private static ArrayList<Drink> drinks;

    private ShoppingCart(){
        drinks = new ArrayList<>();
    }

    public static ShoppingCart getInstance(){
        if(cart == null)
            cart = new ShoppingCart();
        return cart;
    }

    public void addDrink(Drink drink){
        drinks.add(drink);
    }

    public ArrayList<Drink> getDrinks(){
        return drinks;
    }

    public Double getCartTotal(){
        Double total= 0.00;
//        if (drinks.size() > 0) {
            for (Drink drink : drinks) {
                total += drink.getPrice();
            }
//        }
        return total;
    }
}