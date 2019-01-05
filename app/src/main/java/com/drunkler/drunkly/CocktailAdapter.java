package com.drunkler.drunkly;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is adapter for the RecyclerView holding every information on the cocktailMenu
 *
 * @author Oscar
 */
public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.MyViewHolder> {

    private List<Cocktail> cocktailList; // The list of cocktails

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, ingredients, alcohol, recipe;


        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            name = view.findViewById(R.id.name);
            ingredients = view.findViewById(R.id.ingredients);
            alcohol = view.findViewById(R.id.alcohol);
            recipe = view.findViewById(R.id.recipe);
        }

        @Override
        public void onClick(View v) {
            if (recipe.getVisibility() == name.getVisibility()) {
                recipe.setVisibility(View.GONE);
            }
            else {
                recipe.setVisibility(View.VISIBLE);
            }
        }
    }

    CocktailAdapter(ArrayList<Cocktail> cocktailList) {
        this.cocktailList = cocktailList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cocktail_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cocktail cocktail = cocktailList.get(position);
        holder.name.setText(cocktail.getName());
        holder.ingredients.setText(cocktail.getIngredients());
        holder.alcohol.setText(cocktail.getAlcohol() + "/1000 mL");
        holder.recipe.setText(cocktail.getRecipe());
    }

    @Override
    public int getItemCount() {
        return cocktailList.size();
    }
}
