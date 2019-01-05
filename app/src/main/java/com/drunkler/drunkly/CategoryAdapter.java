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
 * The Category Adapter is the adapter for the RecyclerView responsible for the categories of drinks in the TipsDrinks
 *
 * @author Oscar
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<String> drinkList; // The list of every drink
    private String textName = "Gin"; // Initial value of the TextView holding the name of the categories
    private CategoryInterface callback; // Callback method

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name; // TextView holding the name of the drink

        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            name = view.findViewById(R.id.catName);
        }

        @Override
        public void onClick(View v) {
            //Code to update the contents of the categoryDetails RecyclerView
            textName = name.getText().toString();
            callback.onItemSelected(getAdapterPosition());
        }
    }

    CategoryAdapter(ArrayList<String> drinkList, CategoryInterface listener) {
        this.drinkList = drinkList;
        callback = listener;
    }
    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_text, parent, false);

            return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
        String drink = drinkList.get(position);
        holder.name.setText(drink);
        textName = holder.name.getText().toString();
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    String getCurrentCategory() {
        return textName;
    }

    public interface CategoryInterface {
        void onItemSelected(int position);
    }
}
